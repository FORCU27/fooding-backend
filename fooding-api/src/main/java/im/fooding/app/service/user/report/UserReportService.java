package im.fooding.app.service.user.report;

import im.fooding.app.dto.request.user.report.CreateReportRequest;
import im.fooding.core.event.auth.AuthPhoneAuthenticateEvent;
import im.fooding.core.event.report.ReportCreateEvent;
import im.fooding.core.model.notification.NotificationChannel;
import im.fooding.core.model.report.ReportTargetType;
import im.fooding.core.model.review.Review;
import im.fooding.core.model.user.User;
import im.fooding.core.service.report.ReportService;
import im.fooding.core.service.review.ReviewService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserReportService {
    private final ReportService reportService;
    private final UserService userService;
    private final ReviewService reviewService;

    @Value("${message.sender}")
    private String SENDER;
    private final ApplicationEventPublisher publisher;

    public void createReport( long id, CreateReportRequest request ){
        log.info( "Input User Name: {}", request.getReporterId() );
        User reporter = userService.findById( request.getReporterId() );
        reportService.create(
                request.getTargetType(),
                id,
                reporter.getDescription(),
                "",
                reporter
        );
        if( request.getTargetType() == ReportTargetType.REVIEW ){
            Review review = reviewService.findById(request.getReferenceId() );
            review.setBlind( true );
        }
        sendNotification( request.getTargetType(), reporter.getName(), request.getDescription(), reporter.getPhoneNumber() );
    }

    private void sendNotification( ReportTargetType targetType, String reporterName, String description, String phoneNumber ){
        publisher.publishEvent(
                new ReportCreateEvent( targetType, reporterName, description, phoneNumber, SENDER )
        );
    }
}
