## 1. 빌드 스테이지 시작
# [T_NOTE] gradle 이미지 가져옴(빌드 스테이지를 build라는 이름으로 설정)
FROM gradle:7.6.1-jdk17-alpine AS build

# [T_NOTE] 컨테이너 내부(apine 리눅스 환경)의 app폴더로 설정
WORKDIR /app

# [T_NOTE] 현재 디렉토리 내의 모든 파일과 폴더를 컨테이너의 /app 디렉토리로 복사
COPY . .

# 기존에 있으면 지우고 재 build
# --no-daemon : 백그라운드에서 자바프로젝트를 하나의 컨테이너에서 하나의 프로젝트만 할거라 no daemon 사용
# [T_NOTE] gradle을 사용하여 프로젝트 빌드(daemon 프로세스 사용 안함(필수 X))
RUN gradle clean build -x test --no-daemon

## 2. 실행 스테이지 시작
# [T_NOTE] openjdk 17 버전의 이미리를 가져와 JVM 환경 구축
FROM openjdk:17-alpine

## 2-1. 빌드를 미리 수동으로 프로젝트에서 하고 이미지를 구축할 시
# COPY build/libs/*.jar app.jar

## 2-2. 빌드를 따로 수동으로 하지 않고 이미지를 구축할 시(docker build)
COPY --from=build /app/build/libs/*.jar ./

# [T_NOTE] *.jar 파일을 나열하고 grep을 사용해 'plain'이라는 단어가 포함되지 않은 줄을 선택해 app.jar로 변경
# 위에서 빌드하고 복사한 것들중에 plain이 달려있는 부분 빼고 app.jar로 이동(복사)
RUN mv $(ls *.jar | grep -v plain) app.jar

# [T_NOTE] app.jar를 리눅스 환경에서 실행(스프링 부트 서버 실행(7777포트))
ENTRYPOINT ["java", "-jar", "app.jar"]
