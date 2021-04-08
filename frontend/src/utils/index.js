import Web3 from "web3";
import {
    contractAddress,
    abi,
    gethHost
} from "../config"

export class Utils {
    static web3 = new Web3(gethHost);
    static contract = new this.web3.eth.Contract(abi, contractAddress);

    constructor() {}

    // 회원가입 시 실행
    static async createAccount() {
        const web3 = this.web3;

        console.log("account creating start")
        const account = await web3.eth.personal.newAccount("ethereum") // temp password: ethereum
        console.log("account: ", account);
        console.log("created!")

        localStorage.myData = JSON.stringify({
            address: account
        })
        await this.unlockAccount();
        return account;
    }

    static async unlockAccount() {
        const web3 = this.web3;
        await web3.eth.personal.unlockAccount(JSON.parse(localStorage.myData).address, "ethereum", 0) // unlock inf time
    }

    static async getBalance() {
        const web3 = this.web3;
        return await web3.eth.getBalance(JSON.parse(localStorage.myData).address)
    }

    static async receiveBalance() {
        const web3 = this.web3;
        const ether = web3.utils.toWei("1");
        const currentBalance = await this.web3.eth.getBalance(JSON.parse(localStorage.myData).address)

        if (parseInt(currentBalance) < parseInt(ether)) {
            const rs = await web3.eth.sendTransaction({
                from: "0x85f87a4c6aa4b40f2c7fbb5cad924c749c65ba15", // main miner. unlocked.
                to: JSON.parse(localStorage.myData).address,
                value: ether
            })
            return true;
        }
        return false;
    }

    static async call(method, args) {
        const rs = await method(...args).call({
            from: JSON.parse(localStorage.myData).address
        });
        return rs;
    }

    // 노드에서 계정을 관리할 경우
    static async send(method, args) {
        // ether가 모자랄 경우 수급
        console.log("get balance: ", await this.receiveBalance())

        const account = JSON.parse(localStorage.myData).address;

        try {
            const rs = await method(...args).send({
                from: account
            });
            return rs;
        } catch (err) {
            console.log("send error: ", err);
        }
    }

    // 로컬에서 계정을 관리할 경우 서명 후 전송
    static async signAndSend(method, args, callback) {
        const web3 = this.web3;
        const privateKey = JSON.parse(localStorage.myData).privateKey
        const account = JSON.parse(localStorage.myData).address;

        // 트랜잭션 데이터
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
        const rs = await web3.eth.accounts.signTransaction(txObject, privateKey, async (error, signedTx) => {
            if (error) {
                console.log("error!", error)
            } else {
                // send
                try {
                    const _rs = await web3.eth.sendSignedTransaction(signedTx.rawTransaction)
                    callback(_rs)
                } catch (err) {
                    console.log("run error!", err)
                }
            }
        })
        console.log("rs!!", rs);
    }
}