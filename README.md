# fooding-backend

## 🔧 처음 실행 가이드
 
- **도커 컨테이너 셋업 및 실행**
```bash
   chmod +x setup.sh
   ./setup.sh
   
   .env에 ES_SERVICE_TOKEN=발급된 토큰
    잘 되었는지 확인 후 안된다면 수동 생성해서 넣어주고 docker compose up -d kibana
```

- **접속 확인**
```
키바나 dev tools에서 api key 발급

POST /_security/api_key
{
  "name": "test-key",
  "expiration": "1d" // 값 없으면 기간 제한 없음.
}
```

- **application 실행**
```
실행하기 전에 환경변수 세팅
-DELASTIC_SEARCH_ID=발급받은 ID
-DELASTIC_SEARCH_KEY=발급받은 KEY
```
