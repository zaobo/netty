FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD protal-0.0.1-SNAPSHOT.jar protal-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev", "-Duser.timezone=GMT+08","/protal-0.0.1-SNAPSHOT.jar"]