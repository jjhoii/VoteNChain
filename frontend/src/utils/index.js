import Web3 from "web3";
import {
    contractAddress,
    abi,
    gethHost
} from "../config"

if (!localStorage.myData) {
    localStorage.myData = JSON.stringify(web3.eth.accounts.create());
    console.log("new account created: ", JSON.parse(localStorage.myData).address)
}

export class Utils {
    static web3 = new Web3(gethHost);
    static contract = new this.web3.eth.Contract(abi, contractAddress);
    static account = JSON.parse(localStorage.myData).address;

    constructor() {}

    static async getBalance() {
        return await this.web3.eth.getBalance(JSON.parse(localStorage.myData).address)
    }

    static async signAndSend(method, args) {
        const web3 = this.web3;
        const contract = this.contract;
        const privateKey = JSON.parse(localStorage.myData).privateKey
        const account = JSON.parse(localStorage.myData).address;

        // 트랜잭션 데이터
        // const txBuilder = contract.methods.addItem();
        const txBuilder = method(...args);
        const encodedTx = txBuilder.encodeABI();

        // 기타 데이터
        const nonce = await web3.eth.getTransactionCount(account);
        const block = await web3.eth.getBlock("latest")
        const gasLimit = block.gasLimit;
        const gasPrice = await web3.eth.getGasPrice() * 1.5;

        const txObject = {
            nonce: web3.utils.toHex(nonce),
            gasLimit,
            gasPrice,
            data: encodedTx,
            from: JSON.parse(localStorage.myData).address,
            to: contractAddress
        }

        // sign
        web3.eth.accounts.signTransaction(txObject, privateKey, function (error, signedTx) {
            if (error) {
                console.log("error!", error)
            } else {
                // send
                web3.eth.sendSignedTransaction(signedTx.rawTransaction).then((res) =>
                    console.log("result", res)).catch(err => console.log(err))
            }
        })
    }
}