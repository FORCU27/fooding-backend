package im.fooding.app.service.user.report;

import im.fooding.app.dto.request.user.report.CreateReportRequest;
import im.fooding.core.model.report.ReportTargetType;
import im.fooding.core.model.review.Review;
import im.fooding.core.model.user.User;
import im.fooding.core.service.report.ReportService;
import im.fooding.core.service.review.ReviewService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserReportService {
    private final ReportService reportService;
    private final UserService userService;
    private final ReviewService reviewService;

    public void createReport( long id, CreateReportRequest request ){
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
    }
}
