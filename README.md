<!-- # 프로젝트 WIWY -->

# WIWY - 코로나19 정보 제공 및 커뮤니티 서비스 😷

![제목을-입력해주세요_-001 (3)](https://user-images.githubusercontent.com/57143818/188647397-7d865d80-2923-4081-a141-23bc84374119.jpg)




---

## 💡 서비스 소개

**코로나19 정보 제공 및 커뮤니티 서비스**

> 실시간으로 업데이트되는 코로나19 관련 정보를 제공하며 커뮤니티 기능을 활용해 사용자들끼리 소통할 수 있는 서비스입니다.
>

## 🛠️ 기술 스택
| FrontEnd | BackEnd | DevOps |
| --- | --- | --- |
|<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black">|<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> ![JPA](https://img.shields.io/badge/jpa-6DA55F.svg?style=for-the-badge&logo=springdatajpa&logoColor=white) ![MySQL](https://img.shields.io/badge/mysql-%230769AD.svg?style=for-the-badge&logo=mysql&logoColor=white) | ![Docker](https://img.shields.io/badge/docker-%23007ACC.svg?style=for-the-badge&logo=docker&logoColor=white) ![AWS EC2](https://img.shields.io/badge/awsec2-%23E34F26.svg?style=for-the-badge&logo=awsec2&logoColor=white)

## 🗃️ 프로젝트 구조

### 🏗️  아키텍처

![아키텍처](https://user-images.githubusercontent.com/57143818/188647700-94c8bafa-4312-4087-85d4-c874e4bc2cab.PNG)

## 🔗 ERD
![ERD](https://user-images.githubusercontent.com/57143818/188648168-9d91ffb3-9173-4f21-b06f-19ee051a0a8a.png)

## 💻 주요 기능 및 데모 사진

### 당일 집계 현황

- 당일 코로나 19 현황을 받아볼 수 있습니다.
- 총 확진자 수와 백신 2차 접종 완료 수, 거리두기 단계에 대한 정보를 얻을 수 있습니다.
  ![당일집계현황](https://user-images.githubusercontent.com/57143818/188648591-8db52970-6936-46e5-98b3-483b004145b1.png)


### 확진자 현황, 백신 접종 현황

- 일주일 확진자 현황을 시각화된 정보로 제공합니다.
- 누적된 확진환자 수, 격리해제 수, 격리중 수, 사망 수 등을 알 수 있습니다.
- 일주일 백신 접종 현황을 시각화된 정보로 제공합니다.
- 백신 1차, 2차 접종 누적 수를 알 수 있습니다.
  ![확진자현황](https://user-images.githubusercontent.com/57143818/188648618-0c35b207-219a-435b-819f-55fb308e3d06.png)


### 시도별 확진 현황

- 시도별로 확진 현황을 알 수 있습니다.
- 좌측 지도에서 특정 지역을 선택하면 우측에서 그 지역에 대한 확진자 정보를 제공합니다.
  ![시도별 확진 현황](https://user-images.githubusercontent.com/57143818/188648636-02d981b5-00b0-4fe1-b754-5ef5464243ce.png)


### 거리두기 안내, 백신 종류

- 1~4단계 거리두기별 정보를 제공합니다.
- 백신 종류별 정보를 제공합니다.
  ![거리두기 안내](https://user-images.githubusercontent.com/57143818/188648674-7935dfb5-df01-4982-b0d7-df31bc947092.png)


### 국외 발생동향

- 전세계 코로나19 발생 동향을 알 수 있습니다.
- 더보기 버튼을 이용해 한번에 10개씩 새로운 나라에 대한 정보를 볼 수 있습니다.

![국외발생동향](https://user-images.githubusercontent.com/57143818/188648712-b9cad128-f311-419c-86e5-295b63a6d519.png)


### 커뮤니티

- 사용자들끼리 소통할 수 있는 커뮤니티 기능을 제공합니다.
- 회원가입 시 작성한 지역을 바탕으로 지역별로 소통할 수 있는 지역게시판을 제공합니다.
- 내 주변에 있는 선별진료소를 확인할 수 있습니다.


![커뮤니티](https://user-images.githubusercontent.com/57143818/188648748-ac9a88ca-2e7c-4f19-95b9-a8d60eb2c9e8.png)
![자유게시판](https://user-images.githubusercontent.com/57143818/188648773-bc15b7d5-0dca-49ef-8513-f549af298461.png)
![글쓰기](https://user-images.githubusercontent.com/57143818/188648782-45b2e037-c25b-459a-a26a-2c9e33feaf11.png)

---

# Build
## Prerequisites
- Docker > 19.x

## Installation
1. 소스코드 다운로드 - WIWY 클론 후 해당 디렉토리에서 서브 모듈 server, client 클론 진행
    ```shell
    $ git clone https://github.com/W-I-W-Y/WIWY.git
    $ cd WIWY
    WIWY$ git clone https://github.com/W-I-W-Y/client.git
    WIWY$ git clone https://github.com/W-I-W-Y/server.git
    ```
   or
    ```shell
    $ git clone https://github.com/W-I-W-Y/WIWY.git
    $ cd WIWY
    $ git submodule init
    $ git submodule update
    ```

2. 리액트 npm 패키지 설치
    ```shell
    $ cd client
    client$ npm install
    ```
3. 스프링부트 gradle 빌드
    ```shell
    $ cd server
    server$ ./gradlew build
    ```
## Usage
- 도커 이미지 빌드 및 컨테이너 실행
    ```shell
    $ docker-compose up --build
    ```
- 컨테이너 실행
    ```shell
    $ docker-compose up
    ```
- 컨테이너 다운
    ```shell
    $ docker-compose down
    ```
