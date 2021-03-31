import Web3 from "web3";
import {
    contractAddress,
    abi,
    gethHost
} from "../config"

const web3 = new Web3(gethHost);

if (!localStorage.myData) {
    createAccount(web3);
}

// 회원가입 시 실행
async function createAccount(web3) {
    console.log("creating start")
    const account = await web3.eth.personal.newAccount("ethereum") // temp password: ethereum
    console.log("account: ", account);
    console.log("created!")
    localStorage.myData = JSON.stringify({
        address: account
    })
    await unlockAccount(web3);
    return account;
}

async function unlockAccount(web3) {
    await web3.eth.personal.unlockAccount(JSON.parse(localStorage.myData).address, "ethereum", 0) // unlock inf time
    console.log("unlocked");
}

export class Utils {
    static web3 = new Web3(gethHost);
    static contract = new this.web3.eth.Contract(abi, contractAddress);
    static account = JSON.parse(localStorage.myData).address;

    constructor() {}

    static async getBalance() {
        return await this.web3.eth.getBalance(JSON.parse(localStorage.myData).address)
    }

    static async receiveBalance() {
        const ether = web3.utils.toWei("1");
        const currentBalance = await this.web3.eth.getBalance(JSON.parse(localStorage.myData).address)

        if (currentBalance < ether) {
            const rs = await web3.eth.sendTransaction({
                from: "0xb5d166cc0d82bac74364f17d8977536abac711c6", // main miner. unlocked.
                to: JSON.parse(localStorage.myData).address,
                value: ether
            })
            console.log("balance sended: ", rs);
            return true;
        }
        return false;
    }

    // 노드에서 계정을 관리할 경우
    static async send(method, args) {
        // ether가 모자랄 경우 수급
        await this.receiveBalance()

        console.log("send start")
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
                    const rs = await web3.eth.sendSignedTransaction(signedTx.rawTransaction)
                    callback(rs)
                } catch (err) {
                    console.log("run error!", err)
                }
            }
        })
        console.log("rs!!", rs);
    }
}