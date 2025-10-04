package im.fooding.core.event.report;

import im.fooding.core.model.report.ReportStatus;

import java.time.LocalDateTime;

public record ReportUpdateStatusEvent(
        ReportStatus status,
        LocalDateTime reportedAt,
        String reporterName,
        String phoneNumber,
        String sender
) {
}
