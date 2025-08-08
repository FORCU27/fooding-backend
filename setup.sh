#!/bin/bash

set -e
echo ""
echo "📦 Docker Compose로 Elasticsearch, Kibana, MySQL, Redis, MongoDB 환경을 구성합니다."

echo ""
echo "Step 1: MySQL, Redis, MongoDB, Elasticsearch 컨테이너 시작"
docker-compose up -d mysql redis mongodb elasticsearch

echo ""
echo "STEP 2: Elasticsearch가 준비될 때까지 대기..."
until curl -s http://localhost:9200 >/dev/null; do
  sleep 1
done

echo ""
echo "Elasticsearch 준비 완료"

echo ""
echo "STEP 3: ElasticSearch 서비스 토큰 생성 중..."
ES_SERVICE_TOKEN=$(docker exec fooding-elasticsearch bin/elasticsearch-service-tokens create elastic/kibana es-service-token | awk -F' = ' '{print $2}')

if [[ -z "$ES_SERVICE_TOKEN" ]]; then
  echo "토큰 생성 실패"
  exit 1
fi

# .env에 저장
if [ ! -f .env ]; then
  echo "ES_SERVICE_TOKEN=$ES_SERVICE_TOKEN" > .env
  echo ".env 파일이 생성되었습니다."
else
  echo "기존 토큰을 업데이트합니다."
  sed -i "s|^ES_SERVICE_TOKEN=.*|ES_SERVICE_TOKEN=$ES_SERVICE_TOKEN|" .env
fi

echo ""
echo "STEP 6: Kibana 컨테이너 시작"
docker-compose up -d kibana

echo ""
echo "완료"
