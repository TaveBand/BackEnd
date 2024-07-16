# 대일밴드 : 대학 밴드 동아리 커뮤니티 서비스
![ReadMe_MainImage](https://github.com/user-attachments/assets/0e843219-f6e8-43c1-af84-bf633a0dc232)

## 📖 목차
- [프로젝트 소개](#-프로젝트-소개)

- [주제 선정 배경](#주제-선정-배경)
- [프로젝트 기획 의도](#프로젝트-기획-의도)
- [기술 스택](#-기술-스택)
  - [기술 선정 이유](#기술-선정-이유)
- [폴더 구조](#폴더-구조)
- [ERD](#ERD)
- [와이어프레임](#와이어프레임)
- [주요 기능](#주요-기능)
- [팀원 소개 & 후기](팀원-소개-&-후기)


## 🙋‍♀️ 프로젝트 소개
대일밴드 : 밴드 동아리들을 위한 밴드모집 및 공연예약 서비스와 음성분석을 통해 음역대에 맞는 편곡 서비스

## ✨ 주제 선정 배경
- 밴드 음악에 대한 늘어나는 관심과 학교, 학과별 밴드 동아리 수가 많아짐에 따라 밴드 동아리를 위한 커뮤니티의 필요성을 느낌
- 사용자의 음역대를 분석해 밴드 보컬리스트에게 적합한 노래를 자동으로 추천해주기 위함

## ❓ 프로젝트 기획 의도
- 타학교 **밴드동아리와의 교류**를 돕고, **밴드원 모집을 더 쉽게** 합니다!
- 같은 세션을 가진 사람들의 **고민과 노하우를 공유**할 수 있어요!
- 공연 상세 정보를 확인가능한 포스팅과 **해당 페이지에서 예약까지** 가능합니다!
- 노래 부르는 사람의 **음역대를 분석**해주고 **맞춤형 노래를 추천**해드립니다!

## 🛠 기술 스택
**프론트엔드**

<img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black">&nbsp;<img src="https://img.shields.io/badge/axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white">&nbsp;

**백엔드**

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">


## 🐱‍💻 기술 선정 이유
>React와 SpringBoot를 왜 선택하였는가? 

Component를 사용하여 **재사용과 유지보수가 용이**하다는 점과 Virtual DOM으로 인해 *리렌더링* 될 때 **컨텐츠를 좀 더 빠르고 효율적으로 변경**할 수 있다고 생각했습니다.

이지 렌더링이 많이 일어나는 서비스 특성상 **렌더링 서비스에 관리에 적합**한  Spring을 사용했고, React 또한 렌더링 최적화에 좋아서 사용하고자 합니다.



## 📂 폴더 구조
 ```
📂src
│
├── App.js
├── index.js
├── axios.js
│
├── 📂authentication
│   ├── Login.js
│   ├── Register.js
│   ├── Verify.js
│   ├── Complete.js
│   ├── AuthContext.js
│   └── 📂styles
│       └── Login.css
│       ├── Register.css
│       ├── Verify.css
│       ├── Complete.css
│       └── AuthContext.css
│
├── 📂mainpage
│   ├── Home.js
│   └── 📂styles
│       └── Home.css
│
├── 📂mypage
│   ├── Profile.js
│   ├── Scrap.js
│   ├── MyPosts.js
│   ├── MyPerformances.js
│   ├── MyReservations.js
│   └── 📂styles
│       └── Profile.css
│       ├── Scrap.css
│       ├── MyPosts.css
│       ├── MyPerformances.css
│       └── MyReservations.css
│
├── 📂post
│   ├── Clubs.js
│   ├── ClubsDetail.js
│   ├── PR.js
│   ├── PRDetail.js
│   ├── Matching.js
│   ├── MatchingDetail.js
│   └── 📂styles
│       └── Clubs.css
│       ├── ClubsDetail.css
│       ├── PR.css
│       ├── PRDetail.css
│       ├── Matching.css
│       └── MatchingDetail.css
│
├── 📂performance
│   ├── UnionPerformance.js
│   ├── UnionPerformanceDetail.js
│   ├── Reservation.js
│   ├── ReservationCompleted.js
│   └── 📂styles
│       └── UnionPerformance.css
│       └── UnionPerformanceDetail.css
│       └── Reservation.css
│       └── ReservationCompleted.css
│
├── 📂session
│   ├── Bass.js
│   ├── BassDetail.js
│   ├── Drum.js
│   ├── DrumDetail.js
│   ├── Guitar.js
│   ├── GuitarDetail.js
│   ├── Keyboard.js
│   ├── KeyboardDetail.js
│   ├── Vocal.js
│   ├── VocalDetail.js
│   └── 📂styles
│       └── Drum.css
│       └── DrumDetail.css
│
├── 📂voiceanalysis
│   ├── VoiceAnalysis.js
│   ├── Record.js
│   ├── RecordWaiting.js
│   ├── RecordResult.js
│   ├── Recommendation.js
│   └── 📂styles
│       └── VoiceAnalysis.css
│       └── Record.css
│       └── RecordWaiting.css
│       └── RecordResult.css
│       └── Recommendation.css
│
├── 📂shared
│   ├── BoardBtns.js
│   ├── Comment.js
│   ├── Header.js
│   ├── Pagenumber.js
│   ├── SessionBtns.js
│   ├── Sidebar.js
│   ├── Toggle.js
│   └── 📂styles
│       └── BoardBtns.css
│       ├── Comment.css
│       ├── Header.css
│       ├── Pagenumber.css
│       ├── SessionBtns.css
│       ├── Sidebar.css
│       └── Toggle.css
```



## 📋 ERD
![영선이네 밴드부 ](https://github.com/user-attachments/assets/7f407ab5-646b-4a38-bfdb-38eafe6c3d5d)
## 🖥 와이어프레임
![그림1](https://github.com/user-attachments/assets/4fa0cc32-2d77-4387-b137-8032228dd139)
![그림2](https://github.com/user-attachments/assets/cf767319-71e4-4dc5-9f38-b495edfec109)

## 💡 주요 기능
### 📌주요 기능 1

## 👩‍💻 팀원 소개 & 후기
| 고운            | 윤영선   | 김민중   | 김시은  | 정재현  | 
| ----------------- | -------- | -------- | ------- | ------- |
|  <img src="https://github.com/user-attachments/assets/e816f12d-7087-422a-9cfb-fb96fdf58835" width="100">  |  <img src="https://github.com/user-attachments/assets/f02f8ebe-d2d8-46f8-8191-2cf077601720" width="100">  |  <img src="https://github.com/user-attachments/assets/62acad61-fedf-4b9b-90b8-565a5e295f5d" width="100">  |  <img src="https://github.com/user-attachments/assets/f757a680-6133-44bb-bd84-0757391f821d" width="100">  |  <img src="https://github.com/ssafy-is-free/free-project/assets/76441040/0c16e0c4-651d-4fd7-90b7-28317af9fbb5" width="100">  |
| Leader & Backend | Frontend | Backend | Frontend | Backend |

### 📑 프로젝트 후기

- **고운** : 프로젝트 시작할 때 깃 사용법이 어려웠는데 브랜치 사용하는데 익숙해 졌고, 지라와 도커 그리고 my sql을 이번에 사용하는 방법을 팀원들이 알려줘서 배울 수 있었습니다. ACE 팀에 참여해서 프로젝트를 진행하는데 걱정이 없었고 좋은 팀원들을 만나서 제대로 된 첫 프로젝트를 할 수 있었습니다. 이번에 정말 많이 배울 수 있는 시간을 가져서 좋았습니다! 😘😘

- **윤영선** : webRTC 라는 분야가 너무 생소해서 과연 이 프로젝트를 잘 해낼 수 있을까 걱정이 앞섰었습니다. 하지만 좋은 팀원들 덕분에 무사히 유종의 미를 거둘 수 있었습니다. 처음 다뤄보는 깃과 지라는 낯설었지만 점점 적응해 지난 프로젝트보다 잘 활용하게 되었습니다. 혼자가 아니라 다같이, 또 한 사람도 빠지는 사람없이  새벽까지 모여서 코드를 쳤던 것이 기억에 남습니다. 이렇게 다같이 해서 우리 프로젝트 무사히 끝냈습니다!!🥰

- **김민중** : 이번 프로젝트를 통해 Git, Jira와 같은 협업툴을 이용하여 함께 하나의 산출물을 만들어내는 과정에 익숙해질 수 있는 시간이였습니다. JPA, Vue.js, Docker 등 처음 사용하거나 익숙하지 않은 기술들을 학습하고 직접 프로젝트에 적용해봄으로써 기술들을 익혀나갈 수 있었습니다. ✍ 나의 작업만이 아닌 팀원 모두의 코드를 리뷰하고 오류나 모르는 부분을 함께 해결해나가면서 놓친 부분을 찾고 서로를 통해 배울 수 있는 좋은 기회였습니다. 👍 더 퀄리티 높은 시스템을 제작하기 위해 끝까지 지치지않고 힘써준 팀원들에게 감사하고😊 다음 프로젝트는 함께하지 못했지만 기회가 된다면 마지막 자율 프로젝트에서 만나서 우리의 코스모스 시스템을 보완하고 추가기능들을 모두 구현했으면 좋겠습니다. 🙏

- **김시은** : 다들 6인 프로젝트... 하지만 저희 팀은 5명이서 모두 맡은 역할에 대해 책임감을 가지며 잘 해 줬기에 프로젝트를 성공적으로 마칠 수 있었습니다. 이번 계기로 팀워크와 협업의 중요성에 대해 많은 걸 느꼈고, 각자의 장점이 모여 서로의 부족한 점을 보완할 수 있으며 팀원들과 함께 라면 어떠한 것도 해낼 수 있을 것 같다는 자신감이 있었습니다. 이번 공통 프로젝트를 통해 처음 하는 Docker을 사용한 서버 배포 방법, JPA를 사용한 객체 중심 프로젝트 개발, WebRTC 기술 등 많이 배우며 성장했고, 깃 Flow, Jira, Notion를 사용하여 비 대면 상황 이더라도 효율적으로 소통하고 협업하는 법을 익힐 수 있었습니다. 체력적(잠을 못 잠)으로 정신적으로(왜 안되지?) 많이 힘들었지만, 팀원들과 소통하며 문제를 잘 해결해 나아갔고, 저 스스로 정말 열정적으로 프로젝트에 임했다고 자부할 수 있습니다. 마지막으로 우리 팀원들 모두 수고했고 코스모스팀 화이팅!!😀


- **정재현** : 이번 프로젝트로 잘 다루지 못했던 깃, 지라 등 협업툴에 대해 익숙해질 수 있었고, 프로젝트의 기획, 설계 단계부터 꼼꼼히 배울 수 있었습니다. 좋은 팀원들과 함께여서 더 잘 배우고 더 많이 배울 수 있었던 것 같고, 개발 역량이 늘어난 것뿐만아니라 좋은 사람들을 남길 수 있었던 프로젝트여서 더 행복했습니다. 앞으로 모든 팀원들 다 잘 돼서 다시 만났으면 좋겠습니다. 성호없는성호팀 화이팅! 🌸🌸
