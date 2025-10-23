package im.fooding.app.dto.request.ceo.store;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import lombok.Value;

@Value
public class CeoStoreStatisticsRequest {

    @Schema(description = "조회할 날짜", example = "2025-10-23")
    @PastOrPresent
    LocalDate date;
}
