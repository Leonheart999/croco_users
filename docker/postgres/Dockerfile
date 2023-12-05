FROM postgres:14.2

ADD init.sql /docker-entrypoint-initdb.d/

RUN apt-get update
RUN apt-get install -y mc
RUN apt-get install -y vim

ENV POSTGRES_PASSWORD=password123123
ENV TZ="Asia/Tbilisi"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
