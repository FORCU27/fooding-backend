package im.fooding.realtime.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class RedisConfig {

    /**
     * Redis Pub/Sub 메시지를 수신하는 리스너들을 관리하는 컨테이너를 Bean으로 등록
     * Redis 채널로부터 메시지를 비동기적으로 수신하고,
     * 등록된 MessageListener에게 메시지를 전달하는 역할
     *
     * @param connectionFactory Spring Boot가 자동으로 설정해주는 Redis 연결 팩토리
     * @return RedisMessageListenerContainer 인스턴스
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            ThreadPoolTaskExecutor redisTaskExecutor
    ) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();

        // 컨테이너에 Redis 연결 팩토리 설정
        container.setConnectionFactory(connectionFactory);

        // 리스너가 메시지를 처리할 때 사용할 스레드 풀 설정 (성능 향상)
        container.setTaskExecutor(redisTaskExecutor);

        return container;
    }

    /**
     * Redis 리스너 전용 스레드 풀을 Bean으로 등록
     * 리스너 작업이 다른 웹 요청 스레드 풀과 분리하기 위함
     */
    @Bean
    public ThreadPoolTaskExecutor redisTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10); // 기본 스레드 수
        taskExecutor.setMaxPoolSize(20);  // 최대 스레드 수
        taskExecutor.setQueueCapacity(100); // 대기 큐 크기
        taskExecutor.setThreadNamePrefix("redis-listener-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
