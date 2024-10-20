# WeShareYou

# 목차

1. [팀 소개](#팀-소개)
2. [기술 스택](#기술-스택)
   - [Backend](#1-backend)
   - [Security](#2-security)
   - [Frontend](#3-frontend)
   - [Tool](#4-tool)
3. [프로젝트 개요](#프로젝트-개요)
   - [배경](#1-배경)
   - [서비스 목표](#2-서비스-목표)
   - [차별점](#3-차별점)
   - [핵심 기능](#4-핵심-기능)
   - [기대 효과](#5-기대-효과)
4. [설계 문서](#설계-문서)
   - [System Architecture](#system-architecture)
   - [주요 기능 FLOW CHART](#주요-기능-flow-chart)
   - [OAuth 2.0 Sequence Diagram](#OAuth2SequenceDiagram)
   - [DDD](#ddd)
     - [Event Storming](#1-event-storming)
     - [Bounded Context](#2-bounded-context)
   - [DB 모델링](#db-모델링)
     - [개념 모델링](#1-개념-모델링)
     - [논리 모델링](#2-논리-모델링)
     - [물리 모델링](#3-물리-모델링)
5. [산출물](#산출물)
   - [와이어 프레임](#와이어프레임)
   - [스토리 보드](#스토리보드)
   - [기능명세서](#기능명세서)
---

## Team _STANL-2_<a id="팀-소개"></a>
|  |  | || |
| ------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------ |
| 기우석 | 김민석 | 방동호 | 송의혁 | 유혜진 |

---

# 기술 스택 <a id="기술-스택"></a>

### 1. Backend <a id="1-backend"></a>

| Java 17                                                                       | Spring Boot                                                                                              | Spring Data JPA                                                                                   | MyBatis                                                                                                 | Hibernate                                                                       | Gradle                                                                 | JUnit5                                                                                             | Spring Batch                                                                                                  | OpenAI                                                                                       |
| ----------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------- | ---------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------- |
| ![Java](https://img.shields.io/badge/Java-17-007396.svg?&logo=java&color=red) | ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F.svg?&logo=spring-boot&color=lightgreen) | ![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F.svg?&logo=spring-data-JPA) | ![MyBatis](https://img.shields.io/badge/MyBatis-FE6602.svg?&logo=mybatis5&logoColor=white&color=FE6602) | ![Hibernate](https://img.shields.io/badge/Hibernate-59666C.svg?&logo=hibernate) | ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?&logo=gradle) | ![JUnit5](https://img.shields.io/badge/JUnit5-25A162.svg?&logo=junit5&logoColor=white&color=green) | ![Spring Batch](https://img.shields.io/badge/Spring_Batch-6CB33E?style=flat&logo=springbatch&logoColor=white) | ![OpenAI](https://img.shields.io/badge/OpenAI-412991?style=flat&logo=openai&logoColor=white) |

### 2. Security <a id="2-security"></a>

| Spring Security                                                                                                   | JWT                                                                                      | 카카오 로그인                                                                                    | 네이버 로그인                                                                                     |
| ----------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------- |
| ![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F.svg?&logo=spring-security&logoColor=white) | ![JWT](https://img.shields.io/badge/JWT-F60055.svg?&logo=json-web-token&logoColor=white) | ![Kakao Login](https://img.shields.io/badge/Kakao_Login-FFCD00.svg?&logo=kakao&logoColor=black)   | ![Naver Login](https://img.shields.io/badge/Naver_Login-03C75A.svg?&logo=naver&logoColor=white)   |

### 3. Frontend <a id="3-frontend"></a>

| Vue 3                                                                                     | JavaScript                                                                                      | HTML                                                                                            | CSS                                                                                            | Chart.js                                                                                     | VCalendar                                                                                     |
| ------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------ |
| ![Vue 3](https://img.shields.io/badge/Vue_3-4FC08D.svg?&logo=vue.js&logoColor=white)       | ![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E.svg?&logo=javascript&logoColor=black) | ![HTML](https://img.shields.io/badge/HTML-E34F26?logo=html5&logoColor=white)                     | ![CSS](https://img.shields.io/badge/CSS-1572B6?logo=css3&logoColor=white)                     | ![Chart.js](https://img.shields.io/badge/Chart.js-FF4500.svg?&logo=javascript&logoColor=white) | ![VCalendar](https://img.shields.io/badge/VCalendar-8A2BE2.svg?&logo=vcalendar&logoColor=white) |

### 4. Tool <a id="4-tool"></a>

| GitHub                                                                                          | DA#                                                                                              | IntelliJ IDEA                                                                                          | Visual Studio Code                                                                                  |
| ---------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white"> &nbsp; | <img src="https://img.shields.io/badge/DA%23-0B6121.svg?style=flat&logo=draw.io&logoColor=white"> &nbsp; | ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=flat&logo=intellij-idea&logoColor=white) | ![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=flat&logo=visual-studio-code&logoColor=white) |


# 프로젝트 개요 <a id="프로젝트-개요"></a>

---

## 1. 배경 <a id="1-배경"></a>

1. 팀 스터디의 효율성을 향상시킬 수 있는 도구가 필요함
2. 일정 관리, 학습 진도 파악 등 스터디의 정보를 한 곳에서 모아볼 수 있는 서비스가 없음

## 2. 서비스 목표 <a id="2-서비스-목표"></a>

> 팀 스터디의 모든 과정을 쉽게 관리 및 각 멤버가 자신의 학습 목표를 달성할 수 있도록 도움

## 3. 차별점 <a id="3-차별점"></a>

|         | 팀원모집 | 일정관리 | 문제 출제 | 일정관리 |
| ------- | -------- | -------- | --------- | -------- |
| Studyin | ㅇ       | ㅇ       |           | ㅇ       |
| 맞추다  |          |          | ㅇ        |          |
| 공작소  | ㅇ       | ㅇ       |           | ㅇ       |
| SGMA    | ㅇ       | ㅇ       | ㅇ        | ㅇ       |

## 4. 핵심 기능 <a id="4-핵심-기능"></a>

1. 스터디 그룹 생성 및 모집
2. 학습 내용 확인 및 공유
3. 학습 성취도 시각화

## 5. 기대 효과 <a id="5-기대-효과"></a>

> 단순한 학습의 장을 넘어, 체계적이고 효과적인 학습 플랫폼으로 발전할 수 있을 것으로 기대

# 설계 문서 <a id="설계-문서"></a>

---

### System Architecture <a id="system-architecture"></a>

![Network Diagrams](https://github.com/user-attachments/assets/83706f89-f51f-4036-8cd7-f4244cb34801)


## 주요 기능 FLOW CHART <a id="주요-기능-flow-chart"></a>

![문제관련 drawio (1)](https://github.com/user-attachments/assets/59625632-7ce8-4300-b085-945d176620f8)

## OAuth 2.0 Sequence Diagram <a id="OAuth2SequenceDiagram"></a>
![image](https://github.com/user-attachments/assets/b150924e-92a5-4290-9602-38efa24e9804)

## DDD <a id="ddd"></a>

### 1. Event Storming <a id="1-event-storming"></a>

![image](https://github.com/user-attachments/assets/b11ddb78-7874-4dfd-a8c0-ebfdbd41e238)

### 2. Bounded Context <a id="2-bounded-context"></a>

![image](https://github.com/user-attachments/assets/3efdd6c0-09bb-4ac0-ae83-d0e399f39ccd)

## DB 모델링 <a id="db-모델링"></a>

### 1. 개념 모델링 <a id="1-개념-모델링"></a>

![image](https://github.com/user-attachments/assets/e6dea257-6429-4be1-91f4-fa653914148e)

### 2. 논리 모델링 <a id="2-논리-모델링"></a>

![image](https://github.com/user-attachments/assets/b8903887-7000-4205-bf5d-f41de5996ab9)

### 3. 물리 모델링 <a id="3-물리-모델링"></a>

![image](https://github.com/user-attachments/assets/2bf256be-07f2-4f74-91d6-dab3994d7e0b)





## 산출물<a id="산출물"></a>

### 1. 기능 정의서 <a id="기능명세서"></a>

[기능 정의서 바로가기](https://docs.google.com/spreadsheets/d/1XVX6lAse2VZzDybUvryL8GyeM3-PO_EZMFG10hMVJSk/edit?gid=0#gid=0)

![image](https://github.com/user-attachments/assets/e3a3e217-34e9-4954-8301-1150095d71bf)

## 2. 와이어 프레임<a id="와이어프레임"></a>

### 2.1 전체 와이어 프레임
![image](https://github.com/user-attachments/assets/89defadf-fa92-4d09-8de3-767962dab4d2)


### 2.2 회원

<details>
  <summary>2.2.1. 마이페이지</summary>
   <img src="https://github.com/user-attachments/assets/e2019098-836a-48c4-9d03-d9e93060f4e0" alt="마이페이지" />
</details>

<details>
  <summary>2.2.2. 일반 & 카카오 로그인</summary>
  <img src="https://github.com/user-attachments/assets/022e3e9e-7ab3-4f97-9a99-9ef7abb99c55" alt="일반 & 카카오 로그인" />
</details>

<details>
  <summary>2.2.3. 회원가입</summary>
  <img src="https://github.com/user-attachments/assets/01b21f1d-97c7-4562-8581-fd53e3043258" alt="회원가입" />
</details>

<details>
  <summary>2.2.4. 아이디 찾기</summary>
  <img src="https://github.com/user-attachments/assets/43d1d170-9b8c-4801-a8e5-ddb0e55fcf95" alt="아이디 찾기" />
</details>

<details>
  <summary>2.2.5. 비밀번호 찾기</summary>
  <img src="https://github.com/user-attachments/assets/38194563-9c02-46a3-a369-63e330aa0aed" alt="비밀번호 찾기" />
</details>

### 2.3 스터디 그룹 및 모집관련

<details>
  <summary>2.3.1 스터디 그룹 모집글</summary>

  <img src="https://github.com/user-attachments/assets/3687f3ec-1479-4374-89d6-1b21ff639ea4" alt="스터디 그룹 모집글" />
</details>

<details>
  <summary>2.3.2 스터디 그룹 자유게시판 및 공지사항</summary>

  <img src="https://github.com/user-attachments/assets/1e988cc6-a63b-4613-ab6d-4e564efb5895" alt="스터디 그룹 자유게시판 및 공지사항" />
</details>

<details>
  <summary>2.3.3 스터디 그룹원</summary>
  <img src="https://github.com/user-attachments/assets/ae4ff43b-6f9a-49f7-8f71-87ed8c310c6b" alt="스터디 그룹원" />
</details>


### 2.4 스터디 그룹 일정 및 문제
<details>
  <summary>스터디 그룹 일정 및 문제</summary>
  <img src="https://github.com/user-attachments/assets/c1e73822-d022-4b2f-a335-f054fbdd4cf8" alt="스터디 그룹 일정 및 문제" />
</details>


## 3. 스토리 보드<a id="스토리보드"></a>

### 3.1 전체 스토리 보드
![image](https://github.com/user-attachments/assets/574e8f9d-2cfc-4063-993b-c4980ea9873d)

### 3.2 회원

<details>
  <summary>3.2.1. 마이페이지</summary>
  <img src="https://github.com/user-attachments/assets/81084eef-6700-45b2-af93-1aaf8d969781" alt="마이페이지" />
</details>

<details>
  <summary>3.2.2. 일반 & 카카오 로그인</summary>
  <img src="https://github.com/user-attachments/assets/593ac0fb-fe8e-4f1c-a975-daa4b67d7fdd" alt="일반 & 카카오 로그인" />
</details>

<details>
  <summary>3.2.3. 회원가입</summary>
  <img src="https://github.com/user-attachments/assets/fc174228-f14a-4de6-8bf1-2845cb9a2dc5" alt="회원가입" />
</details>

<details>
  <summary>3.2.4. 아이디 찾기</summary>
  <img src="https://github.com/user-attachments/assets/a4f3eb47-401e-4c4c-b95e-1270d71e77a6" alt="아이디 찾기" />
</details>

<details>
  <summary>3.2.5. 비밀번호 찾기</summary>
  <img src="https://github.com/user-attachments/assets/2a95d0d6-6bc2-42d8-adda-8b4663504a34" alt="비밀번호 찾기" />
</details>

### 3.3 스터디 그룹 및 모집관련

<details>
  <summary>3.3.1 스터디 그룹 모집글</summary>
  <img src="https://github.com/user-attachments/assets/ddaa6706-9809-43d8-9319-417ad19eeeb0" alt="스터디 그룹 모집글" />
</details>

<details>
  <summary>3.3.2 스터디 그룹 자유게시판 및 공지사항</summary>
  <img src="https://github.com/user-attachments/assets/1bdcc2c5-6cd2-43f1-bdaf-07592b24e916" alt="스터디 그룹 자유게시판 및 공지사항" />
</details>

<details>
  <summary>3.3.3 스터디 그룹원</summary>
  <img src="https://github.com/user-attachments/assets/5fcfb1ad-5f62-4a3d-9eeb-fe13104b564e" alt="스터디 그룹원" />
</details>

### 3.4 스터디 그룹 일정 및 문제

<details>
  <summary>스터디 그룹 일정 및 문제</summary>
  <img src="https://github.com/user-attachments/assets/71cfac86-9e2e-4c24-b56c-55d0cc5835b5" alt="스터디 그룹 일정 및 문제" />
</details>


