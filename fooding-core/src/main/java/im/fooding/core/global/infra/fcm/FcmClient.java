package im.fooding.core.global.infra.fcm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Value;

@Service
@Slf4j
public class FcmClient {
    private final RestTemplate restTemplate;

    @Value("${notification.api.url}")
    private String notificationApiUrl;

    @Value("${notification.service.app.id}")
    private String appId;

    @Value("${notification.service.app.token}")
    private String appToken;

    @Value("${notification.service.ceo.id}")
    private String ceoId;

    @Value("${notification.service.ceo.token}")
    private String ceoToken;

    @Value("${notification.service.user.id}")
    private String userId;

    @Value("${notification.service.user.token}")
    private String userToken;

    @Value("${notification.service.pos.id}")
    private String posId;

    @Value("${notification.service.pos.token}")
    private String posToken;


    public FcmClient() {
        this.restTemplate = new RestTemplate();
    }

    public void sendMessage(String service, SendFcmDto messageDto) {
        String channelId = this.getChannel(service);
        String channelToken = this.getChannelToken(service);

        String url = notificationApiUrl + "/v2/notification/channels/" + channelId + "/messages";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-channel-token", channelToken);

        HttpEntity<SendFcmDto> request = new HttpEntity<>(messageDto, headers);

        restTemplate.postForEntity(url, request, String.class);
    }

    private String getChannel(String service) {
        switch (service) {
            case "app":
                return appId;
            case "ceo":
                return ceoId;
            case "user":
                return userId;
            case "pos":
                return posId;
            default:
                throw new IllegalArgumentException("Invalid service: " + service);
        }
    }

    private String getChannelToken(String service) {
        switch (service) {
            case "app":
                return appToken;
            case "ceo":
                return ceoToken;
            case "user":
                return userToken;
            case "pos":
                return posToken;
            default:
                throw new IllegalArgumentException("Invalid service: " + service);
        }
    }
}