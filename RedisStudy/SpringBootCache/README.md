⚠️ SpringBoot 3.X.X랑 Jedis 4.X.X 사용 시 JMX 충돌문제

(javax.management.InstanceAlreadyExistsException)
<br>
<br>

📌 원인
- Jedis 4.X부터 JMX 기능이 기본적으로 활성화됨
- setJmxEnabled(true)가 기본 값 → Spring JMX와 충돌
<br>

📌 해결방법

권장설정인 SpringBoot 3.X와 Lettuce Redis Client 조합을 사용할 수 있는 환경이 아니라면
<br>

1️⃣ SpringBoot JMX 비활성화 (application.yaml 파일)
```
spring: jmx: enabled: false
```
2️⃣ Jedis JMX 비활성화 (RedisConfig class)
![image](https://github.com/user-attachments/assets/f6a6cee2-c059-4b50-904b-71bf7012669b)
