package im.fooding.core.global.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class EventStore implements ApplicationContextAware {

    @Autowired
    private ObjectMapper objectMapper;

    private Map<Class<?>, List<HandlerMethod>> eventHandlers = new HashMap<>();
    private Map<String, Class<?>> eventTypeMapping = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        scanForEventHandlers(applicationContext);
        buildEventTypeMapping();
    }

    private void scanForEventHandlers(ApplicationContext applicationContext) {
        Map<String, Object> beans = applicationContext.getBeansOfType(Object.class);

        for (Object bean : beans.values()) {
            Method[] methods = bean.getClass().getMethods();

            for (Method method : methods) {
                KafkaEventHandler annotation = method.getAnnotation(KafkaEventHandler.class);
                if (annotation != null) {
                    Class<?> eventType = annotation.value();
                    HandlerMethod handlerMethod = new HandlerMethod(bean, method);

                    eventHandlers.computeIfAbsent(eventType, k -> new ArrayList<>())
                            .add(handlerMethod);

                    log.info("Registered event handler: {} for event type: {}",
                            method.getName(), eventType.getSimpleName());
                }
            }
        }
    }

    private void buildEventTypeMapping() {
        for (Class<?> eventClass : eventHandlers.keySet()) {
            String eventTypeName = eventClass.getSimpleName();
            eventTypeMapping.put(eventTypeName, eventClass);
            log.info("Mapped event type: {} -> {}", eventTypeName, eventClass.getName());
        }
    }

    @KafkaListener(topics = "fooding.event.internal", groupId = "fooding-event-internal-${spring.profiles.active}")
    public void handleEvents(ConsumerRecord<String, String> record) {
        try {
            JsonNode cdcMessage = objectMapper.readTree(record.value());

            log.info("Received cdcMessage: {}", cdcMessage);

            // CDC 메시지에서 after payload 추출
            JsonNode afterPayload = cdcMessage.path("payload").path("after");

            // event_type 컬럼에서 이벤트 타입 추출
            String eventType = afterPayload.path("event_type").asText();

            // data 컬럼에서 실제 데이터 추출
            String dataJson = afterPayload.path("data").asText();

            log.info("Received dataJson: {}", dataJson);

            if (eventType.isEmpty() || dataJson.isEmpty()) {
                log.warn("Empty event_type or data field in CDC message");
                return;
            }

            log.info("Received event type: {}", eventType);

            // 이벤트 라우팅
            Class<?> eventClass = eventTypeMapping.get(eventType);
            if (eventClass != null) {
                // data JSON을 이벤트 객체로 파싱
                JsonNode eventData = objectMapper.readTree(dataJson);
                Object event = objectMapper.treeToValue(eventData, eventClass);
                routeToHandlers(event);
            } else {
                log.warn("No handler found for event type: {}", eventType);
            }

        } catch (Exception e) {
            log.error("Error processing event: {}", record.value(), e);
        }
    }

    private void routeToHandlers(Object event) {
        List<HandlerMethod> handlers = eventHandlers.get(event.getClass());

        if (handlers != null && !handlers.isEmpty()) {
            log.debug("Routing event {} to {} handlers",
                    event.getClass().getSimpleName(), handlers.size());

            for (HandlerMethod handler : handlers) {
                try {
                    handler.getMethod().invoke(handler.getBean(), event);
                    log.debug("Successfully invoked handler: {}",
                            handler.getMethod().getName());
                } catch (Exception e) {
                    log.error("Error invoking handler method: {}",
                            handler.getMethod().getName(), e);
                }
            }
        } else {
            log.warn("No handlers found for event type: {}", event.getClass().getSimpleName());
        }
    }

    @Data
    private static class HandlerMethod {
        private final Object bean;
        private final Method method;
    }
}
