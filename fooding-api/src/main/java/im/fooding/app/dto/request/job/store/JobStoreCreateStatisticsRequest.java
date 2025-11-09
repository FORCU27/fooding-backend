package im.fooding.app.dto.request.job.store;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JobStoreCreateStatisticsRequest {

    @Schema(description = "생성할 날짜", example = "2025-10-23")
    private LocalDate date;
}
