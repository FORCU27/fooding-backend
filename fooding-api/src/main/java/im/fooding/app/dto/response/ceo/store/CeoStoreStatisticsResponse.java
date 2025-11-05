package im.fooding.app.dto.response.ceo.store;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CeoStoreStatisticsResponse {

    @Schema(description = "총 매출 (원)", example = "1250000", requiredMode = RequiredMode.REQUIRED)
    int totalSales;

    @Schema(description = "전날 대비 총 매출 변화율 (%)", example = "560", requiredMode = RequiredMode.REQUIRED)
    int totalSalesChangeRate;

    @Schema(description = "총 방문자 수 (팀)", example = "45", requiredMode = RequiredMode.REQUIRED)
    int totalVisitors;

    @Schema(description = "전날 대비 방문자 수 변화율 (%)", example = "-230", requiredMode = RequiredMode.REQUIRED)
    int visitorChangeRate;

    @Schema(description = "목표 매출 달성률 (%)", example = "7350", requiredMode = RequiredMode.REQUIRED)
    int annualTargetSalesRate;

    @Schema(description = "현재 웨이팅 (팀)", example = "4", requiredMode = RequiredMode.REQUIRED)
    int currentWaitingCount;

    @Schema(description = "예상 대기 시간 (분)", example = "25", requiredMode = RequiredMode.REQUIRED)
    int expectedWaitingTime;

    @Schema(description = "최근 입장 (분 전)", example = "3", requiredMode = RequiredMode.REQUIRED)
    int lastEntranceMinutesAgo;
}
