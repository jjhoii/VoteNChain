<h1 align="center">A306 - Vote&Chain</h1>

<div align=center>
    <img src="https://img.shields.io/badge/version-1.0.0-blue">
    <img src="https://img.shields.io/badge/stable-master-brightgreen">
</div>
<img src="images/main.png" align=center>

<h3><a href="https://votenchain.tk">DEMO 바로가기</a></h3>
<h3><a href="https://youtu.be/">UCC 바로가기</a></h3>

<div>
  	Vote&Chain에서는 블록체인의 특징과 스마트 컨트랙트를 활용하여 안전하게 투표 데이터가 관리됩니다.<br>
    데모 페이지에서 가장 안전한 투표 솔루션을 경험해보세요.
</div>

## 목차

### [프로젝트 소개](#프로젝트-소개)

### [산출물](#산출물)
- 기능 정의서
- 와이어 프레임
- 유저 플로우
- 시스템 구성도
- ERD

### [작업 룰](#작업-룰)
- Commit Rule
- 브랜치 관리

### [프로젝트 구성](#프로젝트-구성)
- 사용 패키지
- 실행 방법

### [팀원 소개](#팀원-소개)

## 프로젝트 소개

> **Vote&Chain**
>
> 세상에서 가장 안전한 투표 솔루션

설명 설명

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

#### Frontend 구성 요소

|    Vue 라이브러리   | Version | Comment                                              |
| :-----------------: | :-----: | :--------------------------------------------------- |
|       axios         | 0.21.1  | 서버로 요청을 보내기 위해 사용                       |
|        bootstrap-vue      | 2.21.2   | UI 구성을 위해 사용                           |
|        sockjs-client      | 1.5.0   | Backend 서버와 소켓 통신을 위해 사용           |
|        sockjs-client      | 1.5.0   | Backend 서버와 소켓 통신을 위해 사용           |
|        vuex         | 3.4.0   | 상태 관리를 위해 사용                                |
|        web3         | 1.3.4   | 스마트 컨트랙트와 상호작용을 하기 위해 사용           |

#### Backend 구성 요소

|   기술 스택    |   Version   | Comment                                    |
| :------------: | :---------: | :----------------------------------------- |
|    OpenJDK     |      11     | Java                                       |
|   SpringBoot   |    2.4.2    | Rest API 웹 애플리케이션 개발              |
|     Gradle      |      -     | 프로젝트를 빌드하고 라이브러리를 관리 도구 |
|     Mysql      |      -      | DB                                        |
|      JPA       |    2.1.2    | 서버와 DB의 연동을 위해 사용               |
|     Ubuntu     | 20.04.2 LTS | 서비스 제공을 위해 리눅스 서버 구축        |
|     Docker     |   20.10.2   | 컨테이너화 된 애플리케이션 관리            |
|     Nginx      |   1.19.6    | Vue 클라이언트 웹 서버 사용                |

|  Spring 라이브러리  |  Version   | Comment                      |
| :-----------------: | :--------: | :--------------------------- |
|     Spring Web      |     -      |      Rest API 서버 구축      |
|   Spring Data JPA   |     -      |      DB와의 연결 관리        |
|       Lombok        |     -      | Getter, Setter 등 빠른 구현  |
|   Spring Data JDBC  |     -      | JDBC                         |
|     Mysql Driver    |     -      | Mysql Driver                 |

#### Blockchain 구성 요소
|       구성요소      |  Version   | Comment                      |
| :-----------------: | :--------: | :--------------------------- |
|      go-ethereum    |    1.9.25  |      블록체인 네트워크 구성   |
|        solidity     |    0.8.3   |     스마트 컨트랙트 작성     |


### 실행 방법

#### Blockchain

임시
- geth 설치
- genesis 파일 설정
- 네트워크 구성
- coinbase 생성

#### Backend
- DB 구성 (table-query)
- application.properties 설정
- 빌드
- 실행

#### Frontend
- .env.local
- config 파일 수정
- npm run serve

## 팀원 소개

### 팀 개표자들

- **강용욱** - *PM, Frontend*
- **김세진** - *Backend*
- **이석호** - *Backend*
- **이지환** - *Backend*
- **정지환** - *Blockchain, Server*

---



