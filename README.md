# VoteNChain (투표 플랫폼)
## 회의록
[회의록](./docs/meeting)
## 기능 정의서
[기능 정의서](https://docs.google.com/spreadsheets/d/1uNGqb8jLbEXDDbZiSiF3P0Q2t1UjB-v-Cu3_RzDfo2M/edit?usp=sharing)
## 와이어 프레임
[와이어 프레임](https://miro.com/app/board/o9J_lP-hMKk=/)
## 시스템 구성도
[시스템 구성도](https://docs.google.com/presentation/d/11bQkDWvyQLBqAm3rW7P5UiRKPAqQkbl_VmNB_Q31h2U/edit#slide=id.gc44cc550f2_4_0)
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
- fetaure : 기능별 branch