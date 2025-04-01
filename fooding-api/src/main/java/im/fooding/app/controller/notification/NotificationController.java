package im.fooding.app.controller.notification;


import im.fooding.app.service.notification.NotificationApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationApplicationService service;

    @PostMapping("/waiting")
    public void sendWaitingMessage() {
        service.sendWaitingMessage("강고기 홍대점", "알림 후 입장이 늦어질 경우 취소될 수 있어요.", 3, 1, 1, 2);
    }

    @PostMapping("/enter-store")
    public void sendEnterStoreMessage() {
        service.sendEnterStoreMessage("강고기 홍대점", "입장 부탁드려요!", 1, 3);
    }
}
