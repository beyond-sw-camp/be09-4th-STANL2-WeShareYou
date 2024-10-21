# 목차

1. [팀 소개](#팀-소개)
2. [기술 스택](#기술-스택)
   - [Backend](#1-backend)
   - [Frontend](#2-frontend)
   - [DataBase](#3-database)
   - [Infra](#4-infra)
   - [ETC](#5-etc)
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
<img src="https://skillicons.dev/icons?i=java,spring,hibernate,gradle,postman,idea& perline="/> 

### 2. Frontend <a id="2-frontend"></a>
<img src="https://skillicons.dev/icons?i=html,css,js,vue& perline="/> 

### 3. DataBase <a id="3-database"></a>
<img src="https://skillicons.dev/icons?i=mysql,redis,mongodb& perline="/> 

### 4. Infra <a id="4-infra"></a>
<img src="https://skillicons.dev/icons?i=jenkins,docker,nginx,kubernetes& perline="/> 

### 5. ETC <a id="5-etc"></a>
<img src="https://skillicons.dev/icons?i=aws,npm,notion,discord,github,ai,i& perline="/> 

# 프로젝트 개요 <a id="프로젝트-개요"></a>
- 한국에 여행오는 외국인들과 국내 여행객들을 위한 물품 공유 서비스

---

## 1. 배경 <a id="1-배경"></a>


1. 기내 반입 금지 물품들로 인해 버려지는 물품들이 공항에 쌓여가고 있음
2. 여행 중 일회성 소비를 막고, 지속 가능한 사회를 만들어가기 위함

## 2. 서비스 목표 <a id="2-서비스-목표"></a>

> 💡 여행자들의 불필요한 지출을 방지 및 버려지는 물품 감소
> 💡 외국인, 국내인 통한된 여행자 커뮤니티 

## 3. 차별점 <a id="3-차별점"></a>

|                      | <img width="100" height="70" alt="당근" src="https://github.com/user-attachments/assets/bfaf0c38-8d4d-449d-a9ae-5249fe616f9c"> | <img width="100" height="70" alt="오쉐어" src="https://github.com/user-attachments/assets/53452c89-230a-4b30-9a16-d5f9b7dfce17"> | <img width="100" height="70" alt="WSU" src="https://github.com/user-attachments/assets/bef07352-9ed0-4fd6-861c-0f7df9d1bc76"> |
|----------------------|:------------------------------------:|:------------------------------------:|:------------------------------------:|
| ***다양한 언어 지원***          |               X                                |                X                               |               ㅇ                   |
| ***기부 & 대여 서비스***      |    ㅇ                               |  ㅇ                           | △                                |
| ***여행자 커뮤니티***             | X                                | ㅇ                                |    X                            |


## 4. 핵심 기능 <a id="4-핵심-기능"></a>

1. 공유 물품 대여 기능
2. 여행자 SNS 커뮤니티
3. 사용자 채팅 기능
4. 다양한 언어 번역 기능

## 5. 기대 효과 <a id="5-기대-효과"></a>

> 국내/외 여행자 커뮤니티를 활성화하여, 사용자들끼리의 상호작용을 통해 여행의 질을 올려줄 수 있음
> 불필요한 지출을 줄이고, 버려지는 물품들의 수를 감소시킴으로 여행자들의 부담을 덜어줄 수 있음

# 설계 문서 <a id="설계-문서"></a>

---

## System Architecture <a id="system-architecture"></a>

![image](https://github.com/user-attachments/assets/844d3f87-8494-40d4-96cf-00ea06a8e8b7)

## DDD <a id="ddd"></a>

### 1. Event Storming <a id="1-event-storming"></a>

![image](https://github.com/user-attachments/assets/2aa841bc-711a-4be8-8723-3e02ffe67bdc)

### 2. External System <a id="2-external-system"></a>

![image](https://github.com/user-attachments/assets/b0f246d8-50f1-4644-9b00-de8ef8b5a00c)

### 3. Command & Actor <a id="3-command-and-actor-system"></a>

![image](https://github.com/user-attachments/assets/edb25e69-3553-405b-a460-dafb7d50d60e)

### 4. Aggregate <a id="4-aggregate"></a>

![image](https://github.com/user-attachments/assets/14a5be54-4f55-4dd7-b4f1-0b7ead806fef)

### 5. Bounded Context <a id="5-bounded-context"></a>

![image](https://github.com/user-attachments/assets/437d1dc4-8091-4f30-9f84-d2b089be34de)

### 6. Context Mapping <a id="6-context_mapping"></a>

![image](https://github.com/user-attachments/assets/d7872d52-41f8-424c-ad04-abd5f5b73695)

## DB 모델링 <a id="db-모델링"></a>

### 1. 논리 모델링 <a id="2-논리-모델링"></a>

![image](https://github.com/user-attachments/assets/fc56ae44-6b45-43f6-a66a-cb93f36c7e3f)

### 2. 물리 모델링 <a id="3-물리-모델링"></a>

![image](https://github.com/user-attachments/assets/08499ff0-00ae-4e62-83cc-5376c4ae5ef1)

## 산출물<a id="산출물"></a>

### 1. 요구사항 명세서 <a id="요구사항명세서"></a>

[요구사항 명세서 바로가기](https://docs.google.com/spreadsheets/d/1FtUepEhB9tNA0qP20CNM_EzY0c0Jh0suZ3eh00r_5K0/edit?gid=0#gid=0)

![image](https://github.com/user-attachments/assets/54ab239f-be8b-487a-ad08-4a1a1021c21d)
![image](https://github.com/user-attachments/assets/56ffc490-62e5-4960-9877-60827520e468)

### 2. 기능 정의서 <a id="기능명세서"></a>

[[기능 정의서 바로가기](https://docs.google.com/spreadsheets/d/1FtUepEhB9tNA0qP20CNM_EzY0c0Jh0suZ3eh00r_5K0/edit?gid=1007720080#gid=1007720080)]

![image](https://github.com/user-attachments/assets/fd7dd066-3180-4e56-afac-8d63645fcd91)
![image](https://github.com/user-attachments/assets/28fa0db9-9315-4acc-b767-baed89d83189)

### 3. 와이어 프레임<a id="와이어프레임"></a>

![image](https://github.com/user-attachments/assets/3be68d1b-2558-4dc6-b4f4-b583b4b03636)
![image](https://github.com/user-attachments/assets/6ececb3f-39d6-4f71-a8bf-31c59db8e692)

### 4. WBS<a id="WBS"></a>

![image](https://github.com/user-attachments/assets/82cb9fd2-2de6-4757-ad97-a1de9b63a7be)

### 6. 실행 결과
<details>
<summary>회원 기능</summary>

<details>
<summary> 회원가입 & 이메일 인증  </summary>

   
https://github.com/user-attachments/assets/f56d6c30-1e88-4b80-a00a-c45b48a919de


</details>
<details>
<summary> 로그인 </summary>

   
https://github.com/user-attachments/assets/b4848f50-fb87-4b7b-820a-872113fe6b4e


</details>
<details>
<summary> 프로필 수정 </summary>

   
https://github.com/user-attachments/assets/f0776a3b-9a12-46fc-8384-d4624c903f1c


</details>
</details>

---

<details>
<summary>게시글</summary>
 
<details>
<summary> 1. 게시글 등록 </summary>

</details>


https://github.com/user-attachments/assets/02a34d15-a19d-4a14-a95c-1f236afd9f6a


<details>
<summary> 2. 게시글 수정 </summary>

</details>

<details>
<summary> 3. 게시글 삭제 </summary>

</details>

<details>
<summary> 4. 게시글 조회 </summary>

</details>

https://github.com/user-attachments/assets/e2b3b4fc-ef9d-46ab-90ff-fb64e82ad131



<details>
<summary> 5. 게시글 댓글 알림 </summary>

</details>

<details>
<summary> 6. 식재료 게시글 댓글 등록 </summary>
<img src = "https://github.com/3-Minutes-Query/1st_project/assets/162286096/bf849242-8cac-4d20-b14b-51dd7d804d67"  width="600">
</details>

<details>
<summary> 7. 식재료 게시글 댓글 수정 </summary>
<img src = "https://github.com/3-Minutes-Query/1st_project/assets/162286096/6dc817c6-fc87-4acb-a17c-5e4e54378703"  width="600">
</details>

<details>
<summary> 8. 식재료 게시글 댓글 삭제 </summary>
<img src = "https://github.com/3-Minutes-Query/1st_project/assets/162286096/6457439d-dcbc-4f93-8041-560f6ff305c4"  width="600">
</details>

<details>
<summary> 9. 식재료 게시글 댓글 조회 </summary>
<img src = "https://github.com/3-Minutes-Query/1st_project/assets/162286096/cc32614e-e42d-4926-a760-c338d9e45429"  width="600">
</details>

</details>

---

<details>
<summary>🧑‍🍳 레시피 게시글 관리</summary>
 
<details>
<summary> 1. 레시피 게시글 등록 </summary>
<img src = "https://github.com/3-Minutes-Query/1st_project/assets/105826061/c39d0453-232b-494d-92bb-7f23ce330a61"  width="600">
</details>

<details>
<summary> 2. 레시피 게시글 수정 </summary>
<img src = "https://github.com/3-Minutes-Query/1st_project/assets/105826061/0c033569-60a2-47ed-9c27-9f2393f27548"  width="600">
</details>

<details>
<summary> 3. 레시피 게시글 삭제 </summary>
<img src = "https://github.com/3-Minutes-Query/1st_project/assets/105826061/4e34de5a-b3fa-45c4-ad34-87e2cfcd2a7b"  width="600">
</details>

<details>
<summary> 4. 레시피 게시글 조회 </summary>
<img src = "https://github.com/3-Minutes-Query/1st_project/assets/105826061/068cec05-90ec-4741-934b-c28a8758a6bf"  width="600">
</details>

<details>
<summary> 5. 레시피 게시글 키워드 검색 </summary>
<img src = "https://github.com/3-Minutes-Query/1st_project/assets/105826061/3e48bb44-2e93-4702-b6a7-7a803f6260f8"  width="600">
</details>

<details>
<summary> 6. 레시피 게시글 조건별 검색 </summary>
<img src = "https://github.com/3-Minutes-Query/1st_project/assets/105826061/13cb16ef-9693-4de5-8581-a6d43f8b7fe4"  width="600">
</details>

### 6. AS IS TO BE<a id="as-is-to-be"></a>

![image](https://github.com/user-attachments/assets/b63c2582-a926-4080-aeeb-83b61b949e75)



