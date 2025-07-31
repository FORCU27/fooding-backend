#!/bin/bash

set -e
echo ""
echo "📦 Docker Compose로 Elasticsearch, Kibana, MySQL, Redis, MongoDB 환경을 구성합니다."

echo ""
echo "Step 1: mysql 컨테이너 시작"
docker-compose up -d mysql

echo ""
echo "Step 2: redis 컨테이너 시작"
docker-compose up -d redis

echo ""
echo "Step 3: mongodb 컨테이너 시작"
docker-compose up -d mongodb

echo ""
echo "Step 4: Elasticsearch 컨테이너 시작"
docker-compose up -d elasticsearch

echo ""
echo "Elasticsearch가 준비될 때까지 대기..."
until curl -s http://localhost:9200 >/dev/null; do
  sleep 1
done

echo ""
echo "Elasticsearch 준비 완료"

echo ""
echo "STEP 5: Kibana 서비스 토큰 생성 중..."
TOKEN=$(docker exec fooding-elasticsearch bin/elasticsearch-service-tokens create elastic/kibana kibana-token | awk -F' = ' '{print $2}')

if [[ -z "$TOKEN" ]]; then
  echo "토큰 생성 실패. 수동으로 생성하세요."
  exit 1
fi

# .env에 저장
if [ ! -f .env ]; then
  echo "ELASTICSEARCH_SERVICE_ACCOUNT_TOKEN=$TOKEN" > .env
  echo ".env 파일이 생성되었습니다."
else
  echo "기존 토큰을 업데이트합니다."
  sed -i.bak "s|^ELASTICSEARCH_SERVICE_ACCOUNT_TOKEN=.*|ELASTICSEARCH_SERVICE_ACCOUNT_TOKEN=$TOKEN|" .env
fi

echo ""
echo "STEP 6: Kibana 컨테이너 시작"
docker-compose up -d kibana

echo ""
echo "완료"
