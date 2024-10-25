# 목차

1. [팀 소개](#팀-소개)
2. [기술 스택](#기술-스택)
   - [Backend](#1-backend)
   - [Frontend](#2-frontend)
   - [DataBase](#3-database)
   - [Infra](#4-infra)
   - [ETC](#5-etc)
3. [프로젝트 개요](#프로젝트-개요)
   - [주제](#1-주제)
   - [배경](#2-배경)
   - [서비스 목표](#3-서비스-목표)
   - [차별점](#4-차별점)
   - [핵심 기능](#5-핵심-기능)
   - [기대 효과](#6-기대-효과)
4. [설계 문서](#설계-문서)
   - [System Architecture](#system-architecture)
   - [DDD](#ddd)
     - [Event Storming](#1-event-storming)
     - [External System](#2-external-system)
     - [Command & Actor](#3-command-and-actor-system)
     - [Aggregate](#4-aggregate)
     - [Bounded Context](#5-bounded-context)
     - [Context Mapping](#6-context_mapping)
5.  [DB 모델링](#db-모델링)
     - [논리 모델링](#2-논리-모델링)
     - [물리 모델링](#3-물리-모델링)
6. [산출물](#산출물)
   - [요구사항 명세서](#요구사항명세서)
   - [기능명세서](#기능명세서)
   - [WBS](#WBS)
   - [화면설계서](#화면설계서)
   - [AS-IS TO-BE](#as-is-to-be)
   - [살행결과](#실행결과)
   - [CI/CD](#CI/CD)
7. [발표 자료](#etc)
8. [동료 평가](#Evaluation)

---

## Team _STANL-2_<a id="팀-소개"></a>
![image](https://github.com/user-attachments/assets/e09b9f7a-2cc3-41fa-8164-348a494882c9)
| <img width="130" height="100" alt="스크린샷 2024-09-05 오전 9 50 04" src="https://github.com/user-attachments/assets/5ce7dd8a-205b-49f9-9274-aeef0ebe11fb">| <img width="130" height="100" alt="스크린샷 2024-09-05 오전 9 47 10" src="https://github.com/3-Minutes-Query/choleeTest/assets/102345450/1046b24a-5d40-4dc1-a747-cb65f20dc764"> | <img width="130" height="100" alt="스크린샷 2024-09-05 오전 9 47 28" src="https://github.com/user-attachments/assets/6a0e2b77-22d6-4e45-be58-2336f7e80afe"> | <img width="130" height="100" alt="스크린샷 2024-09-05 오전 9 47 28" src="https://github.com/user-attachments/assets/67e48c8e-6e01-476e-b255-c902a1eba08e"> | <img width="130" height="100" alt="스크린샷 2024-09-05 오전 9 47 28"  src="https://github.com/user-attachments/assets/c485422d-7685-40cb-aed3-a6893c75044e"> |
| :------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------------: |
| 기우석 | 김민석 | 방동호 | 송의혁 | 유혜진 |
| [<img src="https://img.shields.io/badge/Github-Link-181717?logo=Github">](https://github.com/woosuk1) <br>| [<img src="https://img.shields.io/badge/Github-Link-181717?logo=Github">](https://github.com/minseokkim6823) <br>|[<img src="https://img.shields.io/badge/Github-Link-181717?logo=Github">](https://github.com/Bang1999) <br>| [<img src="https://img.shields.io/badge/Github-Link-181717?logo=Github">](https://github.com/euihyeok-song) <br>| [<img src="https://img.shields.io/badge/Github-Link-181717?logo=Github">](https://github.com/yuhyejin) <br>|

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
## 1. 주제 <a id="1-주제"></a>
<b>한국에 여행오는 외국인들과 국내 여행객들을 위한 물품 공유 서비스</b>

## 2. 배경 <a id="2-배경"></a>
1. 기내 반입 금지 물품들로 인해 버려지는 물품들이 공항에 쌓여가고 있음
2. 여행 중 일회성 소비를 막고, 지속 가능한 사회를 만들어가기 위함

## 3. 서비스 목표 <a id="3-서비스-목표"></a>

>
 💡 여행자들의 불필요한 지출 방지 및 버려지는 물품 감소 <br>
 💡 외국인, 국내인 통한된 여행자 커뮤니티 <br>
 💡 여행 물품을 기부를 함으로써 이후 여행오는 여행자가 사용할 수 있게 함 <br>
>

## 4. 차별점 <a id="4-차별점"></a>

|                      | <img width="100" height="70" alt="당근" src="https://github.com/user-attachments/assets/bfaf0c38-8d4d-449d-a9ae-5249fe616f9c"> | <img width="100" height="70" alt="WSU" src="https://github.com/user-attachments/assets/bef07352-9ed0-4fd6-861c-0f7df9d1bc76"> | <img width="100" height="70" alt="오쉐어" src="https://github.com/user-attachments/assets/53452c89-230a-4b30-9a16-d5f9b7dfce17"> |
|----------------------|:------------------------------------:|:------------------------------------:|:------------------------------------:|
| ***다양한 언어 지원***          |               X                                |               ㅇ                                |               X                   |
| ***기부 & 대여 서비스***      |    ㅇ                               |  ㅇ                           | ㅇ                                |
| ***여행자 커뮤니티***             | X                                | ㅇ                                |    △                            |


## 5. 핵심 기능 <a id="5-핵심-기능"></a>

1. 공유 물품 대여 기능
2. 여행자 SNS 커뮤니티
3. 사용자 간 채팅 기능
4. 다양한 언어 번역 기능

## 6. 기대 효과 <a id="6-기대-효과"></a>

> 국내/외 여행자 커뮤니티를 활성화하여, 사용자들끼리의 상호작용을 이끌어내고, 여행 물품 대여 및 공유를 통해서 사용층을 늘림

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

![image](https://github.com/user-attachments/assets/2b8d6d61-4d78-4380-b7c0-52db65938084)


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

### 3. WBS<a id="WBS"></a>

![image](https://github.com/user-attachments/assets/82cb9fd2-2de6-4757-ad97-a1de9b63a7be)

### 4. 화면 설계서<a id="화면설계서"></a>
![image](https://github.com/user-attachments/assets/b5be8cc1-59d8-43c2-b26d-0af5660c27e4)
![image](https://github.com/user-attachments/assets/60be1083-44a5-4aab-847e-d46527bad78e)
![image](https://github.com/user-attachments/assets/3ee516de-101e-4cb4-a662-b81c6437cf18)

### 5. AS IS TO BE<a id="as-is-to-be"></a>

![image](https://github.com/user-attachments/assets/b63c2582-a926-4080-aeeb-83b61b949e75)



### 6. 실행 결과<a id="실행결과"></a>

<details>
<summary>👨‍👩‍👧‍👦회원</summary>
<details>
<summary>회원가입 & 이메일 인증  </summary>

   
https://github.com/user-attachments/assets/f56d6c30-1e88-4b80-a00a-c45b48a919de


</details>
<details>
<summary>로그인 </summary>

   
https://github.com/user-attachments/assets/b4848f50-fb87-4b7b-820a-872113fe6b4e


</details>
<details>
<summary>프로필 수정 </summary>

   
https://github.com/user-attachments/assets/f0776a3b-9a12-46fc-8384-d4624c903f1c


</details>
</details>

---

<details>
<summary>📋게시글</summary>

<details>
<summary>게시글 조회</summary>


https://github.com/user-attachments/assets/a1db067f-e8c8-4742-ba81-d6af500b384a

   
</details>
<details>
<summary>게시글 작성</summary>


https://github.com/user-attachments/assets/c72a75fc-41ee-4380-a708-eb0b1fbaf579

   
</details>
<details>
<summary>게시글 수정</summary>


https://github.com/user-attachments/assets/ad01e96c-e453-4c51-922b-c3f090bd4bef

   
</details>
<details>
<summary>게시글 삭제</summary>


https://github.com/user-attachments/assets/38522821-f81c-42ba-92bb-ac025ae1a526

   
</details>
<details>
<summary>게시글 댓글</summary>


https://github.com/user-attachments/assets/67ea3a26-0d3b-4a63-8fc3-8bb76824c0a4

   
</details>
<details>
<summary>게시글 댓글 알람</summary>


https://github.com/user-attachments/assets/37668fff-ce2e-44a7-8293-50d6081e6586

   
</details>
</details>

---

<details>
<summary>🧳공유물품</summary>
<details>
<summary>공유물품 조회 & 대여</summary>


https://github.com/user-attachments/assets/78f0ce16-44fc-4da5-9931-9eeda17828e0

   
</details>
</details>

---

<details>
<summary>📢공지사항</summary>
<details>
<summary>공지사항 조회</summary>


https://github.com/user-attachments/assets/142f02dc-59b5-4c4b-b475-1d80cf719826

   
</details>
</details>
   
---

<details>
<summary>🙋🏻FAQ & 번역</summary>
<details>
<summary>FAQ 조회 & 번역 </summary>


https://github.com/user-attachments/assets/beaf8fbe-b669-48e0-bff2-129666cdcda4

   
</details>
</details>

---

<details>
<summary>💌채팅</summary>
<details>
<summary>채팅방 생성 & 채팅</summary>


https://github.com/user-attachments/assets/4a0f8642-282c-407e-88e5-aa801c854f7b

   
</details>
<details>
<summary>게시글에서 채팅보내기</summary>


https://github.com/user-attachments/assets/fd73bce2-e912-45cd-9209-336dc1dea3b9

   
</details>
</details>

---

### 7. CI/CD
#### Jenkins CI/CD
![jenkins자동배포](https://github.com/user-attachments/assets/da1bb444-9239-4de8-9ba1-4125554da92f)


#### Jenkins Code
```
pipeline {
    agent any

    tools {
        gradle 'gradle'
        jdk 'openJDK17'
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('DOCKERHUB_PASSWORD')
        DOCKERHUB_USERNAME = 'hjeu'
        GITHUB_URL = 'https://github.com/STANL-2/STANL_Backend.git'
    }

    stages {
        stage('Preparation') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'docker --version'
                    } else {
                        bat 'docker --version'
                    }
                }
            }
        }
        stage('Source Build') {
            steps {
                script {
                    if (isUnix()) {
                        sh "chmod +x ./gradlew"
                        sh "./gradlew clean build -x test"
                    } else {
                        bat "gradlew.bat clean build -x test"
                    }
                }
            }
        }
        stage('Container Build and Push') {
            steps {
                script {
                    dir('C:/STANL_Backend') {  
                        withCredentials([usernamePassword(credentialsId: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                            if (isUnix()) {
                                sh "docker build -t ${DOCKERHUB_USERNAME}/weshareu-backend:latest ."
                                sh "docker login -u ${DOCKER_USER} -p ${DOCKER_PASS}"
                                sh "docker push ${DOCKERHUB_USERNAME}/weshareu-backend:latest"
                            } else {
                                bat "docker build -t ${DOCKERHUB_USERNAME}/weshareu-backend:latest ."
                                bat "docker login -u %DOCKER_USER% -p %DOCKER_PASS%"
                                bat "docker push ${DOCKERHUB_USERNAME}/weshareu-backend:latest"
                            }
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                if (isUnix()) {
                    sh 'docker logout'
                } else {
                    bat 'docker logout'
                }
            }
        }
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
```


<br>

## 7. 발표자료<a id="etc"></a>

![image](https://github.com/user-attachments/assets/7d4115b3-ab40-4c87-bcb1-6391a1e8dadd)

![image](https://github.com/user-attachments/assets/3043a1ec-9cad-41c6-8263-a5996e631249)


## 8. 동료 평가<a id="Evaluation"></a>

<details>
<summary>동료 평가 확인하기</summary>
<div markdown="1">

#### ABOUT [방동호](https://github.com/Bang1999)

| FROM | COMMENT |
| :---: | :--- |
|기우석|시큐리티에 대한 이해를 바탕으로 이번 프로젝트의 보안을 책임져 주었습니다. 또한 프로젝트의 문서화 설계를 미리 준비함으로 팀의 프로젝트 일정 관리가 수월해졌습니다.|
|송의혁|다른 팀원들의 의견을 적극적으로 수용하시고 소통을 통해서 해결해 나가시는 점이 좋았습니다. 시큐리티라는 어려운 부분을 맡아서 끝까지 책임지고 마무리 하신점이 인상깊었습니다.|
|김민석|쉽지 않은 부분인 로그인 관련기능을 개발 기간이 짧음에도 불구하고 개발하는 모습을 보고 대단하다고 느꼈습니다.|
|유혜진|동호님은 시큐리티에 대해 공부를 하고 짧은 기간동안 완벽하게 구현하는 것을 보고 대단하다는 생각을 했습니다. 그리고 개발을 정말 빠르게, 정확하게 하십니다. 정말 멋있고 고생했다고 말하고 싶습니다!|


#### ABOUT [송의혁](https://github.com/euihyeok-song)

| FROM | COMMENT |
| :---: | :--- |
|기우석|아이디어가 항상 좋으셔서 지난 팀부터 주제는 의혁님 것으로 결정되었고, 몰입감을 가지고 할 수 있었던 것 같습니다. 구상해왔던 것들을 다 구현하셔서 aha 모먼트를 만들어주셨습니다.|
|방동호|의혁님은 팀의 오른쪽 기둥(어머니)이십니다. 팀원들이 다들 힘든 시간을 보내 예민했던 시기도 있었지만 의혁님이 어머니처럼 뭐든 잘 풀어가게 만들어 주셨습니다.|
|김민석|팀의 핵심 기능 중 하나인 게시글에 사진을 첨부하는 기능을 담당하셨습니다. 구현 과정에서 어려움이 있었지만, 이를 극복하고 성공적으로 완성하는 모습을 보며 책임감 있는 분이라고 느꼈습니다..|
|유혜진|의혁님은 개발하면서 정말 열정이 많다는 것을 느꼈습니다. 서비스에 대한 생각을 많이 하고 어떻게하면 더 좋을지에 대해 생각을 많이 하시면서 책임감이 굉장하다고 느꼈습니다.|


#### ABOUT [김민석](https://github.com/minseokkim6823)

| FROM | COMMENT |
| :---: | :--- |
|기우석|프로젝트 경험이 풍부하신 덕분에 프로젝트 중에 덧붙이고 거를 것들에 대해서 명확해 질 수 있었고, 생각지 못한 부분을 경험하지 않아도 미리 예방할 수 있게 해주셨습니다.|
|송의혁|프로젝트를 진행하면서 모르는 부분을 많이 질문했던 거 같습니다. 오류가 발생하는 부분에 log를 찍어가며 금방 해결하시는 점들을 보고, 많이 배웠던거 같습니다.|
|방동호|민석님은 팀에서 가장 프로젝트 관련 경험이 많으셔서 팀원들 모두가 각자 힘든걸 하는데 어려움이 있을때 툭툭 키워드를 던져주면서 엄청난 삽질을 안하게 도와주셨습니다.|
|유혜진|민석님은 확실히 프로젝트 경험이 많아서 그런지 많이 의지가 되었습니다. 프로젝트를 하면서 힘든게 있으면 와서 같이 도와주시기도 하고 도움이 정말 많이 됐습니다. 감사합니다!|


#### ABOUT [유헤진](https://github.com/yuhyejin)

| FROM | COMMENT |
| :---: | :--- |
|기우석|혜진님은 제가 맞닥뜨린 난관들을 물어보면 다른 관점으로 바라보고 해결책을 제시해주셨습니다. 할 일이 바쁜 와중에도 긍정 에너지로 친절하게 대해주셔서 감사합니다.|
|송의혁|책임감이 아주 강하신 분이시고, 어려운 환경 속에서도 끝까지 완성하시는 모습이 인상깊었습니다. 그리고 SSE를 통한 알림서비스를 맡아서 잘 수행해주셨습니다.|
|김민석|때와 장소를 가리지 않고 본인이 맡은 일을 충실히 수행하는 모습이 인상적이었습니다.|
|방동호|제 짝꿍 혜진님은 우리팀의 홍일점입니다. 항상 팀원의 기분을 매우 좋게해주시면서 자기가 맡은건 확실하게 하시는 책임감이 엄청 높으십니다.|

#### ABOUT [기우석](https://github.com/woosuk1)

| FROM | COMMENT |
| :---: | :--- |
|방동호|우석님은 팀의 든든한 왼쪽 기둥(아버지)이라고 저는 생각합니다. 항상 든든하게 팀이 힘들면 한번씩 찾아갈 수 있도록 편하게 해주셨습니다.|
|송의혁|가장 까다로웠던 채팅을 맡으셔서 잘 완성하셨고, 짝꿍으로써 함께 고민하고 생각해본 시간이 좋았던 거 같고, 제가 캐치하지 못한 점들을 잘 골라내주셔서 도움이 됐습니다.|
|김민석|채팅 기능의 세세한 부분까지 신경 써서 트러블슈팅하는 모습을 보며, 정말 끈기 있는 분이라고 느꼈습니다.|
|유혜진|우석님은 개발에 문제가 생겨도 포기하지 않고 열심히 하는 모습을 보여줍니다. 그리고 해내는 모습이 정말 멋있다고 생각합니다. 채팅 하느라 힘드셨을텐데 정말 고생 많으셨습니다!|

</details>
