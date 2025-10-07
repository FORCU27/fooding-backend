package im.fooding.core.event.report;

import im.fooding.core.model.report.ReportTargetType;

public record ReportCreateEvent(
        ReportTargetType targetType,
        String reporter,
        String description,
        String phoneNumber,
        String sender
) {
}
