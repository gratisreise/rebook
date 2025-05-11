# Spring Config 설정 파일


## application.yaml
- openfein 설정
- sentry

## application-dev.yaml
도커 컴포즈 환경에서 개발
- jpa(update)
- redis
- rabbitmq
- eureka
- actuator
- logging
- postgresql: 같은 db


## application-prod.yaml
- jpa(validate)
- rabbitmq
- eureka
- actuator
- logging

## user-service
### dev
- port 

### prod
- redis