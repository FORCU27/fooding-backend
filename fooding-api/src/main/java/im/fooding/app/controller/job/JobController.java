package im.fooding.app.controller.job;

import im.fooding.app.dto.request.job.store.JobStoreCreateStatisticsRequest;
import im.fooding.app.service.job.JobService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/jobs")
@RequiredArgsConstructor
@Tag(name = "JobController", description = "Job Controller")
public class JobController {

    private final JobService jobService;

    @PostMapping("/create-statistics")
    @Operation(summary = "통계 생성")
    public ApiResult<Void> createStatistics(
            @RequestBody JobStoreCreateStatisticsRequest request
    ) {
        jobService.createStoreStatistics(request);
        return ApiResult.ok();
    }
}
