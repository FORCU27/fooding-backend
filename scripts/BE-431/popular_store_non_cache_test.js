import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    vus: 10,           // 동시 사용자 수
    duration: '30s',   // 테스트 지속 시간
};

export default function () {
    const url = 'http://localhost:8080/user/stores?sortType=REVIEW&sortDirection=DESCENDING&pageNum=1&pageSize=10';
    const token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsImV4cCI6MTc2NTAwMjA4MywidXNlciI6Mn0.J6G7oecR-PCk7J2cwJV6IbgQRb8yVabrQz17gmDs3yhGg2X0ZWNtNaiuDUKf0yhT91Q1Wb-XuVMjkoXfdawLSA';

    const res = http.get(url, {
        headers: {
            Authorization: `Bearer ${token}`,
            Accept: 'application/json',
        },
    });

    // 상태 코드 체크
    check(res, {
        'status is 200': (r) => r.status === 200,
    });

    sleep(1); // 1초 대기
}
