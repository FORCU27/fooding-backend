package im.fooding.app.controller.job.store;

import im.fooding.app.dto.request.job.store.JobStoreCreateStatisticsRequest;
import im.fooding.app.service.job.store.JobStoreService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/jobs/stores")
@RequiredArgsConstructor
@Tag(name = "JobStoreController", description = "Job Store Controller")
public class JobStoreController {

    private final JobStoreService jobStoreService;

    @PostMapping("/statistics")
    @Operation(summary = "통계 생성")
    public ApiResult<Void> createStatistics(
            @RequestBody JobStoreCreateStatisticsRequest request
    ) {
        jobStoreService.createStatistics(request);
        return ApiResult.ok();
    }
}
