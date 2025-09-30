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

    }
}
