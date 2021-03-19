# VoteNChain (투표 플랫폼)
## 개요
최근 한 방송에서 투표 조작으로 시청자들에게 피해를 입혔다. 이를 방지하기 위해 블록체인 기술을 이용하여 투표 플랫폼을 구축하고자 한다.

## 회의록
[회의록](./docs/meeting)

## 기능 정의서
[기능 정의서](https://docs.google.com/spreadsheets/d/1uNGqb8jLbEXDDbZiSiF3P0Q2t1UjB-v-Cu3_RzDfo2M/edit?usp=sharing)

## 와이어 프레임
[와이어 프레임](https://miro.com/app/board/o9J_lP-hMKk=/)

## 유저 플로우
[유저 플로우](https://viewer.diagrams.net/?highlight=0000ff&layers=1&nav=1&title=Untitled%20Diagram.drawio#Uhttps%3A%2F%2Fdrive.google.com%2Fuc%3Fid%3D1vMJoX8PhLb4dJrZv2U42-J3dO1j-8ft9%26export%3Ddownload)

## 시스템 구성도
[시스템 구성도](https://docs.google.com/presentation/d/11bQkDWvyQLBqAm3rW7P5UiRKPAqQkbl_VmNB_Q31h2U/edit#slide=id.gc44cc550f2_4_0)

## ERD
[ERD](https://www.erdcloud.com/d/9XCkpxa6CwCowEdJ5)

## 기술 스택
### Frontend 구성 요소

|    Vue 라이브러리   | Version | Comment                                              |
| :-----------------: | :-----: | :--------------------------------------------------- |
|       axios         | 0.21.1  | 서버로 요청을 보내기 위해 사용                       |
|        vuex         | 3.6.2   | 상태 관리를 위해 사용                                |
|        vuetify      | 2.4.3   | UI 구성을 위해 사용                                  |

### Backend 구성 요소

|   기술 스택    |   Version   | Comment                                    |
| :------------: | :---------: | :----------------------------------------- |
|    OpenJDK     |      11     | Java                                       |
|   SpringBoot   |    2.4.2    | Rest API 웹 애플리케이션 개발              |
|     Maven      |    4.0.0    | 프로젝트를 빌드하고 라이브러리를 관리 도구 |
|     Mysql      |      -      | DB                                        |
|      JPA       |    2.1.2    | 서버와 DB의 연동을 위해 사용               |
|     Ubuntu     | 20.04.2 LTS | 서비스 제공을 위해 리눅스 서버 구축        |
|     Docker     |   20.10.2   | 컨테이너화 된 애플리케이션 관리            |
|     Nginx      |   1.19.6    | Vue 클라이언트 웹 서버 사용                |

|  Spring 라이브러리  |  Version   | Comment                      |
| :-----------------: | :--------: | :--------------------------- |
|     Spring Web      |     -      |      Rest API 서버 구축      |
|     WebSocket       |     -      |       소켓 서버 구축         |
| Spring Data MongoDB |     -      |        MongoDB 연결          |
|   Spring Data JPA   |     -      |      DB와의 연결 관리        |
|       Lombok        |     -      | Getter, Setter 등 빠른 구현  |
|   Spring Data JDBC  |     -      | JDBC                         |
|     Mysql Driver    |     -      | Mysql Driver                 |

## 작업 룰
### Commit Rule
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

### 브랜치 관리
```bash
(master) -> (develop) -> (feature/frontend(backend)/feature명)
```
- master : 배포 가능한 상태로 유지
- develop : 개발용 최상위 branch
- feature : 기능별 branch