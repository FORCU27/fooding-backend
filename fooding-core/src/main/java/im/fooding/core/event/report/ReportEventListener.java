package im.fooding.core.event.report;

import im.fooding.core.global.infra.slack.SlackClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportEventListener {
    private final SlackClient slackClient;

    @EventListener
    public void handleReportCreateEventListener( ReportCreateEvent event ) {
        // 신고자에게 메세지 전달
        String reporterMessage = String.format(
                "[Fooding 신고 접수 안내]\n " +
                        "\"%s\"님의 신고 접수가 완료되었습니다. \n" +
                        "[ %s ]\n\n" +
                        "---\n\n" +
                        "정보\n " +
                        "- 신고 대상 타입: %s\n " +
                        "- 신고자 전화번호: %s\n" +
                        "발송 번호: %s",
                event.reporter(),
                event.description(),
                event.targetType().toString(),
                event.phoneNumber(),
                event.sender()
        );
        slackClient.sendNotificationMessage(reporterMessage);
        
        // 관리자에게 메세지 전달
        String adminMessage = String.format(
                "[Fooding 신고 접수]\n " +
                        "새로운 신고가 접수되었습니다. \n" +
                        "---\n\n" +
                        "- 신고자: %s \n" +
                        "- 신고 내용: %s\n " +
                        "- 신고 대상 타입: %s\n " +
                        "- 신고자 전화번호: %s",
                event.reporter(),
                event.description(),
                event.targetType().toString(),
                event.phoneNumber()
        );
        slackClient.sendNotificationMessage(adminMessage);
    }

    @EventListener
    public void handleReportStatusUpdateEvent( ReportUpdateStatusEvent event ){
        // 신고자에게 메세지 전달
        String processingMsg = "신고 접수가 완료되었습니다.";
        String successMsg = "신고 처리가 완료되었습니다.";
        String denyMsg = "신고 처리가 반려되었습니다.";
        String message = "";
        switch( event.status() ){
            case PROCESS:
                message = processingMsg;
                break;
            case DENY:
                message = denyMsg;
                break;
            case SUCCESS:
                message = successMsg;
                break;
            default:
                break;
        }
        String reporterMessage = String.format(
                "[Fooding 신고 처리 안내]\n " +
                        "\"%s\"님의 " + message + " \n" +
                        "[ %s ]\n\n" +
                        "---\n\n" +
                        "정보\n " +
                        "- 신고자 전화번호: %s\n" +
                        "- 신고일시: %s\n" +
                        "발송 번호: %s",
                event.reporterName(),
                event.status(),
                event.phoneNumber(),
                event.reportedAt(),
                event.sender()
        );
        slackClient.sendNotificationMessage(reporterMessage);

        // 관리자에게 메세지 전달
        String adminMessage = String.format(
                "[Fooding 신고 처리 안내]\n " +
                        "아래 신고에 대한 작업 단계가 변경되었습니다. \n" +
                        "---\n\n" +
                        "- 신고자: %s \n" +
                        "- 신고 일시: %s\n " +
                        "- 신고자 전화번호: %s",
                event.reporterName(),
                event.reportedAt(),
                event.phoneNumber()
        );
        slackClient.sendNotificationMessage(adminMessage);
    }
}
