# Toy_Trello

## 프로젝트 설명

팀원들과 일정을 같이 세우고 조율하는 Trello , 작업의 흐름을 시각적으로 파악하게 합니다.

## 디렉토리 구조
```bash
├── src                         # 소스 코드가 위치하는 디렉토리
│   ├── main                    # 메인 소스 코드가 위치하는 디렉토리
│   │   ├── java                # 자바 소스 코드가 위치하는 디렉토리
│   │   │   ├── comm.example.toy_trello  # 기본 패키지 이름
│   │   │   │   ├── domain # 각 도메인의 패키지들이 위치하는 디렉토리
│   │   │   │   │    ├── board # 보드 관련 클래스가 위치하는 디렉토리
│   │   │   │   │    ├── card # 카드 관련 클래스가 위치하는 디렉토리
│   │   │   │   │    ├── comment # 댓글 관련 클래스가 위치하는 디렉토리
│   │   │   │   │    ├── member # 멤버 관련 클래스가 위치하는 디렉토리
│   │   │   │   │    ├── team # 팀 관련 클래스가 위치하는 디렉토리
│   │   │   │   │    ├── user # 유저 관련 클래스가 위치하는 디렉토리
│   │   │   │   │    ├── util # 유틸 관련 클래스가 위치하는 디렉토리
│   │   │   │   ├── global # 공통 클래스의 패키지들이 위치하는 디렉토리
│   │   │   │   │    ├── config # config 관련 클래스가 위치하는 디렉토리
│   │   │   │   │    ├── dto # 공통 dto 클래스가 위치하는 디렉토리
│   │   │   │   │    ├── exception # 예외처리 관련 클래스가 위치하는 디렉토리
│   │   │   │   │    ├── jwt # jwt 관련 클래스가 위치하는 디렉토리
│   │   │   │   │    ├── security # security 관련 클래스가 위치하는 디렉토리
│   │   │   │   │    ├── util # 유틸 관련 클래스가 위치하는 디렉토리
│   │   │   │   └── ToyTrelloApplication.java  # 메인 클래스
│   │   └── resources           # 리소스 파일들이 위치하는 디렉토리
│   └── test                    # 테스트 소스 코드가 위치하는 디렉토리
├── build.gradle               # Gradle 빌드 스크립트 파일
├── settings.gradle            # Gradle 설정 파일
└── README.md                  # 프로젝트에 대한 설명이 담긴 마크다운 파일

```

## ERD 

![trello](https://github.com/Leetaeho33/Toy_Trello/assets/144213900/306ca938-9ce4-4c25-a001-4893566b9ecf)


## API 명세

![image]()
![image]()
![image]()
![image]()
