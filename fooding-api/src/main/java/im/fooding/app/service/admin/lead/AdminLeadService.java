package im.fooding.app.service.admin.lead;

import im.fooding.app.dto.request.admin.lead.AdminLeadPageRequest;
import im.fooding.app.dto.response.admin.lead.AdminLeadResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminLeadService {

    public PageResponse<AdminLeadResponse> list(AdminLeadPageRequest request) {
        // TODO: Replace with actual repository query when Lead domain is available
        List<AdminLeadResponse> list = List.of();

        PageInfo pageInfo = PageInfo.builder()
                .pageNum(request.getPageNum())
                .pageSize(request.getPageSize())
                .totalCount(0)
                .totalPages(0)
                .build();

        return PageResponse.of(list, pageInfo);
    }
}

