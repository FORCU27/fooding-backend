package im.fooding.app.service.user.report;

import im.fooding.app.dto.request.user.report.CreateReportRequest;
import im.fooding.core.model.user.User;
import im.fooding.core.service.report.ReportService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserReportService {
    private final ReportService reportService;
    private final UserService userService;

    public void createReport( CreateReportRequest request ){
        User reporter = userService.findById( request.getReporterId() );
        reportService.create(
                request.getTargetType(),
                request.getReferenceId(),
                request.getDescription(),
                "",
                reporter
        );
    }
}
