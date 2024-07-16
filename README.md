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
├── main
│   ├── java
│   │   └── ys_band
│   │       └── develop
│   │           ├── DailBandApplication.java
│   │           ├── config
│   │           │   ├── CorsConfig.java
│   │           │   └── SecurityConfig.java
│   │           ├── controller
│   │           │   ├── CommentController.java
│   │           │   ├── HomeController.java
│   │           │   ├── MyPrPostController.java
│   │           │   ├── PerformanceController.java
│   │           │   ├── PostController.java
│   │           │   ├── ReservationController.java
│   │           │   ├── SessionController.java
│   │           │   ├── SongController.java
│   │           │   ├── UnionPerformancePostController.java
│   │           │   ├── UserController.java
│   │           │   ├── YoutubeLinkController.java
│   │           │   └── auth
│   │           │       ├── LoginController.java
│   │           │       └── RegisterController.java
│   │           ├── domain
│   │           │   ├── Authority.java
│   │           │   ├── BaseTime.java
│   │           │   ├── Board.java
│   │           │   ├── Comment.java
│   │           │   ├── File.java
│   │           │   ├── Performance.java
│   │           │   ├── Post.java
│   │           │   ├── Reservation.java
│   │           │   ├── Scrap.java
│   │           │   ├── Session.java
│   │           │   ├── Song.java
│   │           │   ├── User.java
│   │           │   └── Youtube.java
│   │           ├── dto
│   │           │   ├── JwtTokenDTO.java
│   │           │   ├── LoginDTO.java
│   │           │   ├── PostDTO.java
│   │           │   ├── UserDTO.java
│   │           │   ├── comment
│   │           │   │   ├── CommentGetDTO.java
│   │           │   │   └── CommentPostDTO.java
│   │           │   ├── mypr
│   │           │   │   ├── MyPrGetDTO.java
│   │           │   │   ├── MyPrPostDTO.java
│   │           │   │   └── MyPrPostDTOWithoutComments.java
│   │           │   ├── performance
│   │           │   │   ├── PerformanceDtoConverter.java
│   │           │   │   ├── PerformanceGetDto.java
│   │           │   │   └── PerformancePostDto.java
│   │           │   ├── post
│   │           │   │   ├── PostDTO.java
│   │           │   │   ├── PostDtoConverter.java
│   │           │   │   └── PostGetDto.java
│   │           │   ├── reservation
│   │           │   │   ├── ReservationDto.java
│   │           │   │   └── ReservationDtoConverter.java
│   │           │   ├── session
│   │           │   │   ├── SessionGetDTO.java
│   │           │   │   ├── SessionPostDTO.java
│   │           │   │   └── SessionPostDTOWithoutComments.java
│   │           │   ├── unionperformance
│   │           │   │   ├── UnionPerformanceGetDTO.java
│   │           │   │   ├── UnionPerformancePostDTO.java
│   │           │   │   └── UnionPerformancePostDTOWithoutComments.java
│   │           │   ├── user
│   │           │   │   ├── UserDtoConverter.java
│   │           │   │   ├── UserGetDto.java
│   │           │   │   └── UserPostDto.java
│   │           │   └── youtube
│   │           │       ├── YoutubeGetDTO.java
│   │           │       └── YoutubePostDTO.java
│   │           ├── exception
│   │           │   ├── GlobalExceptionHandler.java
│   │           │   └── UserException.java
│   │           ├── repository
│   │           │   ├── BoardRepository.java
│   │           │   ├── CommentRepository.java
│   │           │   ├── FileRepository.java
│   │           │   ├── PerformanceRepository.java
│   │           │   ├── PostRepository.java
│   │           │   ├── ReservationRepository.java
│   │           │   ├── SessionRepository.java
│   │           │   ├── SongRepository.java
│   │           │   ├── UserRepository.java
│   │           │   └── YoutubeRepository.java
│   │           ├── security
│   │           │   ├── JwtAccessDeniedHandler.java
│   │           │   ├── JwtAuthenticationEntryPoint.java
│   │           │   ├── JwtAuthenticationFilter.java
│   │           │   ├── JwtSecurityConfig.java
│   │           │   └── JwtTokenProvider.java
│   │           └── service
│   │               ├── AudioConversionService.java
│   │               ├── CustomUserDetailService.java
│   │               ├── EmailService.java
│   │               ├── PitchService.java
│   │               ├── Post
│   │               │   ├── CommentService.java
│   │               │   ├── MyPrService.java
│   │               │   ├── PerformanceService.java
│   │               │   ├── PostService.java
│   │               │   ├── ReservationService.java
│   │               │   ├── UnionPerformanceService.java
│   │               │   └── YoutubeLinkService.java
│   │               ├── RecommendationService.java
│   │               ├── RegisterService.java
│   │               ├── SessionService.java
│   │               ├── SpotifyService.java
│   │               └── UserService.java
```



## 📋 ERD
![영선이네 밴드부 ](https://github.com/user-attachments/assets/7f407ab5-646b-4a38-bfdb-38eafe6c3d5d)
## 🖥 와이어프레임
![그림1](https://github.com/user-attachments/assets/4fa0cc32-2d77-4387-b137-8032228dd139)
![그림2](https://github.com/user-attachments/assets/cf767319-71e4-4dc5-9f38-b495edfec109)


### 📌주요 기능 
##1. 로그인 및 회원가입 서비스
  - Jwt 토큰 방식
##2. 이메일 인증 서비스
##3. 마이페이지 제공 서비스
##4. 모집 게시판 서비스
##5. My PR 게시판 서비스
##6. 세션 별 게시판 서비스
##7. 녹음파일 기반 음성분석 노래 추천 서비스
  - TarsosDSP를 활용한 음역대 추출
  - Spotify API를 활용한 노래추천 서비스
##8. 연합 공연 홍보 서비스
##9. 공연 예약 서비스
## 👩‍💻 팀원 소개 & 후기
| 고운            | 윤영선   | 김민중   | 김시은  | 정재현  | 
| ----------------- | -------- | -------- | ------- | ------- |
|  <img src="https://github.com/user-attachments/assets/e816f12d-7087-422a-9cfb-fb96fdf58835" width="100">  |  <img src="https://github.com/user-attachments/assets/f02f8ebe-d2d8-46f8-8191-2cf077601720" width="100">  |  <img src="https://github.com/user-attachments/assets/62acad61-fedf-4b9b-90b8-565a5e295f5d" width="100">  |  <img src="https://github.com/user-attachments/assets/f757a680-6133-44bb-bd84-0757391f821d" width="100">  |  <img src="https://github.com/ssafy-is-free/free-project/assets/76441040/0c16e0c4-651d-4fd7-90b7-28317af9fbb5" width="100">  |
| Leader & Backend | Frontend | Backend | Frontend | Backend |

### 📑 프로젝트 후기

- **고운** : 프로젝트를 시작할 때에는 구상한 기능도 많고 대부분 첫 프로젝트 경험이어서 앞으로 어떻게 해야할지 조금 걱정이 됐습니다. 또 깃에 대해서도 익숙하지 않아 중간단계까지는 제대로 활용하지 못한 것 같습니다. 그래도 좋은 팀원분들과 프로젝트 경험을 쌓을 수 있어서 좋았고 모르는 부분을 같이 고민하고 의논하면서 해결하는 과정을 겪었기에 훨씬 더 성장할 수 있었습니다. 구현 과정에서는 먼저 초기 단계에 깃 관리와 설계를 제대로 하지 못해 아쉬웠습니다. 체계적인 프로젝트 설계를 초반에 했으면 정말 좋았겠지만, 이를 나중에 깨닫고 적용하려고 노력한 것 자체가 정말 많은 도움이 되었습니다. 구현 과정에서도 생각하지 못한 것들이 생기고, 그로 인해 기능을 추가하는 과정 또 오류를 수정하는 과정들이 힘들기도 하였지만 좋은 팀원분들과 함께해 이렇게 프로젝트를 원활하게 진행할 수 있었다고 생각합니다. 팀장으로서 프로젝트에 참여했지만 팀장을 맡을 만큼 개발 지식과 프로젝트 설계 및 관리 지식이 많지 않았습니다. 부족해도 팀원분들이 함께 노력하며 더 나은 프로젝트를 위해 다같이 힘써준 부분이 정말 고맙습니다. 끝까지 포기하지않고 기능 구현에 힘쓴 모두 고생 많았고 앞으로 더 성장하고 기회가 되면 또 같이 프로젝트할 수 있었으면 좋겠습니다! 다들 앞날은 창창하길 바래🙃

- **윤영선** :프로젝트 경험이 많지 않고 제대로 배포를 해본적이 없었기에 두려움이 많았습니다. 깃 관리에 익숙지 않아 어려움을 느꼈고 체계적으로 설계를 진행해본 경험자가 없었기에 이슈관리와 폴더구조 정리, git flow를 통한 깃관리 방식에 맞게 프로젝트를 진행하는데 어려움이 있었습니다. 시행착오가 있었지만 좋은 팀장님과 팀원 분들을 만나서 부족한 부분에 대해 배우고 같이 고민하면서 많은 것을 배우고 느꼈던 것 같습니다. 협업을 진행하면서 부족한 부분을 절실히 느끼고보완해야 할 점도 알게 되면서 오류를 해결하기 위해 밤샘 작업을 하는 날도 많았지만 그만큼 배운 것이 많았다고 생각합니다. Git을 다루는 법을 배우면서 jira같은 협업 툴을 사용해서 컨벤션도 설정하고 PR을 통한 코드리뷰와 이슈관리를 하며 프로젝트를 진행했으면 어땠을까 아쉬움이 많이 남는 것 같습니다. 하지만 좋은 팀원들과 함께 협업하면서 프로젝트를 진행할 수 있어서 영광이었고 프로젝트를 위해 매주 밤잠을 지새우며 코드를 수정하고 총괄을 담당해준 팀장님께 감사하단 말씀 드리고 싶습니다. 팀원분들 모두 맡은 역할을 열심히 수행해서 이보다 더 좋은 팀원들을 만날 수 있을까 생각이 들 정도로 짧은 시간이었지만 많은 것을 얻어갔던 것 같습니다. 부족한 저를 이끌어주신 팀원, 팀장님께 다시한번 감사하다는 말씀 드리고 싶습니다. 기회가 된다면 다시 프로젝트 진행을 저희 팀원들과 함께할 수 있으면 좋을 것 같습니다! 좋은 팀원들과 함께여서 더 잘 배우고 더 많이 배울 수 있었던 것 같고, 개발 역량이 늘어난 것뿐만아니라 좋은 사람들을 남길 수 있었던 프로젝트여서 더 행복했습니다. 앞으로 모든 팀원 다 잘 돼서 다시 만났으면 좋겠습니다. 화이팅!🌸🌸

- **김민중** : 이번 프로젝트를 통해 Git과 같은 협업툴을 이용하여 컨벤션을 정하고 git flow방식같이 깃관리 방법을 선택해 체계적으로 프로젝트를 진행해야 할 필요성을 느꼈습니다. 나의 작업만이 아닌 팀원 모두의 코드를 합치면서 merge conflict를 해결하고 PR을 통해 다른사람의 코드를 보고 이해하는 과정에서 혼자서 작성할때마다 효율적인 코드 작성에 도움이 되었습니다. 짧은 기간내에 열심히 참여해준 팀원들에게 정말 감사하고, 많이 배웠습니다. 좋은 경험을 가지게 되어 기쁩니다. 이번 프로젝트를 계기로 깃에 대해 깊게 공부해보고 싶다고 느꼈습니다!😊😊  
- **김시은** : 후기를 써주세요! 화이팅!!😀😀

- **정재현** : 후기써주세요! 🌸🌸

