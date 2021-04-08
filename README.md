<h1 align="center">A306 - Vote&Chain</h1>

<div align=center>
    <img src="https://img.shields.io/badge/version-1.0.0-blue">
    <img src="https://img.shields.io/badge/stable-master-brightgreen">
</div>
<img src="images/main.png" align=center>

<h3><a href="https://votenchain.tk">DEMO 바로가기</a></h3>
<h3><a href="https://youtu.be/bYXbropDL0E">UCC 바로가기</a></h3>

<div>
  	Vote&Chain에서는 블록체인의 특징과 스마트 컨트랙트를 활용하여 안전하게 투표 데이터가 관리됩니다.<br>
    데모 페이지에서 가장 안전한 투표 솔루션을 경험해보세요.
</div>

## 목차

### 1. [프로젝트 소개](#프로젝트-소개)

### 2. [산출물](#산출물)
- 기능 정의서
- 와이어 프레임
- 유저 플로우
- 시스템 구성도
- ERD

### 3. [작업 룰](#작업-룰)
- Commit Rule
- 브랜치 관리

### 4. [프로젝트 구성](#프로젝트-구성)
- 사용 패키지
- 실행 방법

### 5. [팀원 소개](#팀원-소개)

## 프로젝트 소개

> **Vote&Chain**
>
> 세상에서 가장 안전한 블록체인 기반 투표 솔루션

블록체인 기반의 투표 솔루션 개발 프로젝트입니다.  
Ethereum을 사용했으며, Solidity 언어로 Smart Contract를 작성하여 투표 시스템을 배포했습니다.  
배포된 컨트랙트는 수정할 수 없고, 수정이 필요할 경우 재배포 해야 하기 때문에 로직이 바뀔 염려 없이 안전하고 투명한 투표 진행이 가능합니다.

## 산출물
### 기능 정의서
[기능 정의서](https://docs.google.com/spreadsheets/d/1uNGqb8jLbEXDDbZiSiF3P0Q2t1UjB-v-Cu3_RzDfo2M/edit?usp=sharing)

### 와이어 프레임
[와이어 프레임](https://miro.com/app/board/o9J_lP-hMKk=/)

### 유저 플로우
[유저 플로우](https://viewer.diagrams.net/?highlight=0000ff&layers=1&nav=1&title=Untitled%20Diagram.drawio#Uhttps%3A%2F%2Fdrive.google.com%2Fuc%3Fid%3D1vMJoX8PhLb4dJrZv2U42-J3dO1j-8ft9%26export%3Ddownload)

### 시스템 구성도
[시스템 구성도](https://docs.google.com/presentation/d/11bQkDWvyQLBqAm3rW7P5UiRKPAqQkbl_VmNB_Q31h2U/edit#slide=id.gc44cc550f2_4_0)

### ERD
[ERD](https://www.erdcloud.com/d/9XCkpxa6CwCowEdJ5)


## 작업 룰

Commit Rule
```bash
type(타입) : title(제목)

body(본문, 생략 가능)

See also : #issue, ...(참고 이슈, 생략 가능)
##################################################
    types = {
      feat : 새로운 기능에 대한 커밋
      fix : 버그 수정에 대한 커밋
      build : 빌드 관련 파일 수정에 대한 커밋
      chore : 그 외 자잘한 수정에 대한 커밋
      ci : CI관련 설정 수정에 대한 커밋
      docs : 문서 수정에 대한 커밋
      style : 코드 스타일 혹은 포맷 등에 관한 커밋
      refactor :  코드 리팩토링에 대한 커밋
      test : 테스트 코드 수정에 대한 커밋
    }

ex)
feat: 로그인 기능 구현 / fix: 회원가입 수정
```

브랜치 관리
```bash
(master) -> (develop) -> (feature/frontend(backend)/feature명)
```
- master : 배포 가능한 상태로 유지
- develop : 개발용 최상위 branch
- feature : 기능별 branch

## 프로젝트 구성

### Package

Frontend 구성 요소

|   Vue 라이브러리   | Version | Comment                                              |
| :---------------: | :-----: | :--------------------------------------------------- |
|      aws-sdk      | 2.877.0 | 이미지파일을 S3저장소에 올리기 위해 사용               |
|       axios       |  0.21.1 | 서버로 요청을 보내기 위해 사용                         |
|   bootstrap-vue   |  2.21.2 | UI 구성을 위해 사용                                   |
|   sockjs-client   |   1.5.0 | Backend 서버와 소켓 통신을 위해 사용                  |
|        Vue        |  2.6.12 | 웹 프론트 개발                                        |
| Vue-google-charts |   0.3.3 | 결과페이지에 차트를 보여주기 위해 사용                 |
|     Vue-router    |   3.2.0 | 모듈화된, 컴포넌트 기반의 라우터 설정                  |
|       vuex        |   3.4.0 | 상태 관리를 위해 사용                                 |
|       web3        |   1.3.4 | 스마트 컨트랙트와 상호작용을 하기 위해 사용            |


Backend 구성 요소

|   기술 스택    |   Version   | Comment                                    |
| :------------: | :---------: | :----------------------------------------- |
|    OpenJDK     |      11     | OpenJDK                                    |
|   SpringBoot   |    2.4.2    | Rest API 웹 애플리케이션 개발              |
|     Gradle     |    6.8.3    | 프로젝트를 빌드하고 라이브러리를 관리 도구 |
|      Mysql     |   8.0.23    | DB                                        |
|     Ubuntu     | 20.04.2 LTS | 서비스 제공을 위해 리눅스 서버 구축        |
|     Docker     |   20.10.2   | 컨테이너화 된 애플리케이션 관리            |
|     Nginx      |   1.19.6    | Vue 클라이언트 웹 서버 사용                |

|  Spring 라이브러리  |  Version   | Comment                      |
| :-----------------: | :--------: | :--------------------------- |
|     Spring Web      |     -      | Rest API 서버 구축           |
|   Spring Data JPA   |   2.4.3    | DB와의 연결 관리             |
|       Lombok        |  1.18.18   | Getter, Setter 등 빠른 구현  |
|        JWT          |   0.9.1    | access token 생성            |
|   Mysql Connector   |   8.0.23   | Mysql Driver                 |
|       websocket     |    2.4.3   | 실시간 통신을 위한 웹 소켓 라이브러리 |

Blockchain 구성 요소

|       구성요소      |  Version   | Comment                      |
| :-----------------: | :--------: | :--------------------------- |
|      go-ethereum    |    1.9.25  |      블록체인 네트워크 구성   |
|        solidity     |    0.8.3   |     스마트 컨트랙트 작성     |


### 실행 방법

#### Blockchain

geth 설치
- https://geth.ethereum.org/docs/install-and-build/installing-geth 참고

account 생성
```bash
$ geth account new

Your new key was generated

Public address of the key:   <account>
Path of the secret key file: <key_file_path>
```

genesis 파일 설정
```bash
# genesis.json
{
    "config": {
        "chainId": 921,
        "homesteadBlock": 0,
        "eip150Block": 0,
        "eip155Block": 0,
        "eip158Block": 0,
        "byzantiumBlock": 0,
        "constantinopleBlock": 0,
        "constantinopleFixBlock": 0,
        "clique": {
            "period": 2
        }
    },
    "alloc"      : {
        "<account>": {
            "balance": "1000000000000000000000"
        }
    },
    "coinbase"   : "0x0000000000000000000000000000000000000000",
    "difficulty" : "0x10",
    "extraData"  : "0x0000000000000000000000000000000000000000000000000000000000000000<account>0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000", # <account>: account 생성 단계에서 생성되는 account address
    "gasLimit"   : "0x98967f",
    "nonce"      : "0xdeadbeefdeadbeef",
    "mixhash"    : "0x0000000000000000000000000000000000000000000000000000000000000000",
    "parentHash" : "0x0000000000000000000000000000000000000000000000000000000000000000",
    "timestamp"  : "0x00"
}
```

node 생성
```bash
$ mkdir -p datadir/keystore
# <key_file_path>: account 생성 단계에서 생성되는 key 파일
$ cp <key_file_path> datadir/keystore/
$ geth --datadir datadir init genesis.json
$ geth --datadir datadir --networkid 921 --nat extip:127.0.0.1 --ipcdisable --port 30301 --http --http.port 8545 --http.api "admin,eth,net,web3,personal,miner" --http.corsdomain "*" --allow-insecure-unlock --mine --miner.threads 1 --miner.gasprice 0 --http.addr 0.0.0.0 --http.vhosts "*"
```

Truffle 설치
```bash
npm install truffle -g
```

Solidity 배포
```bash
$ cd solidity
$ truffle migrate
# 출력되는 VoteManager contract 주소 저장
```

#### Backend

데이터 베이스 구성
```sql
CREATE TABLE `Participant` (
  `userIdx` int NOT NULL,
  `voteIdx` int NOT NULL,
  KEY `useridParticipant` (`userIdx`),
  KEY `voteidParticipant` (`voteIdx`),
  CONSTRAINT `useridParticipant` FOREIGN KEY (`userIdx`) REFERENCES `User` (`userIdx`),
  CONSTRAINT `voteidParticipant` FOREIGN KEY (`voteIdx`) REFERENCES `Vote` (`voteIdx`)
)

CREATE TABLE `User` (
  `userIdx` int NOT NULL AUTO_INCREMENT,
  `userEmail` varchar(255) NOT NULL,
  `userName` varchar(255) NOT NULL,
  `account` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`userIdx`)
)

CREATE TABLE `Vote` (
  `voteIdx` int NOT NULL AUTO_INCREMENT,
  `userIdx` int NOT NULL,
  `contractAddress` varchar(512) NOT NULL,
  `hashKey` varchar(256) NOT NULL,
  PRIMARY KEY (`voteIdx`),
  KEY `useridVote` (`userIdx`),
  CONSTRAINT `useridVote` FOREIGN KEY (`userIdx`) REFERENCES `User` (`userIdx`)
)

```
application.properties 설정
```bash
## MYSQL
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://<host>:3306/<database>?serverTimezone=UTC& characterEncoding=UTF-8 # host, database 설정
spring.datasource.username=<user> # user 설정
spring.datasource.password=<password # password 설정
spring.jpa.hibernate.naming.physical-strategy = org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```

빌드 및 실행
```bash
$ cd backend
$ ./gradlew build -x test
$ java -jar ./build/libs/*.jar
```

#### Frontend
.env.local 설정
```bash
VUE_APP_CLIENT_ID="728976855685-5opfb7817n7lhqblm7cnadbtdaokt1qq.apps.googleusercontent.com"
VUE_APP_SERVER_URL=<host> # host 설정
```
config 파일 수정
```javascript
// frontend/src/config.index.js
export const contractAddress = "<contract_address>"; // 이전에 배포했던 컨트랙트 주소
...
export const gethHost = "http://localhost:8545";
```

빌드 및 실행
```bash
$ cd frontend
$ npm i
$ npm run serve
```

## 팀원 소개

### 팀 개표자들

- **강용욱** - *PM, Frontend*
- **김세진** - *Backend*
- **이석호** - *Backend*
- **이지환** - *Backend*
- **정지환** - *Blockchain, Server*