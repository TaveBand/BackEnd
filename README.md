🌸 COSMOS(코스모스-코딩 스터디, 모두 함께 스터디)
비대면 화상 IT 스터디 플랫폼

👪 팀원 소개
image-20220218015429409

🕹Git 컨벤션 개요
Conflict를 방지하고, 효과적이고 명확한 협업을 진행하고자

1️⃣ Git-Flow 브랜치 전략을 도입하여 계층별 브랜치를 관리했습니다

2️⃣ AngularJS Commit Conventions를 참고하여 Commit 컨벤션을 정의했습니다

3️⃣ 작업의 시작 전 JIRA 티켓을 생성하고, 모든 커밋과 티켓을 연결했습니다

위와 같은 세 가지 협업 규칙을 세우고 프로젝트를 진행했습니다.

🏞 Git Flow
master   
└ develop  
  ├ front - feature/front/기능...  
  └ back - feature/back/기능...
  
master : 운영 서버로 배포하기 위한 브랜치
develop : 다음 출시 기능을 개발하는 브랜치
front : 프론트엔드 개발하는 브랜치
back : 백엔드를 개발하는 브랜치
feature : 세부 기능을 개발하는 브랜치
😎 Commit Convention
[type] commit message

Type

Fix : 잘못된 동작을 고칠 때

fix function/error/typo in style.css

option

funtion : 고친 함수 명 (e.g. fix login function in index.html)
error : 수정한 에러 (e.g. fix [구체적 에러명] error in login.js)
typo : 오타 (e.g. fix typo in style.css)
add : 새로운 것을 추가할 때

