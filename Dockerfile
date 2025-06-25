FROM openjdk:17-alpine

MAINTAINER boris

RUN apk add bash

RUN mkdir /exam_copyria
WORKDIR /exam_copyria
