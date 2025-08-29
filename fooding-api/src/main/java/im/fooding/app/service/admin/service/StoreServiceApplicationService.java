package im.fooding.app.service.admin.service;

import im.fooding.app.dto.request.admin.service.CreateStoreServiceRequest;
import im.fooding.app.dto.request.admin.service.RetrieveStoreServiceRequest;
import im.fooding.app.dto.response.admin.service.StoreServiceResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.store.StoreServiceType;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.store.StoreServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceApplicationService {
    private final StoreServiceService service;
    private final StoreService storeService;

    /**
     * 서비스 생성
     *
     * @param request
     */
    public void create( CreateStoreServiceRequest request ){
        service.create(
                storeService.findById( request.getStoreId() ),
                request.getType()
        );
    }

    /**
     * 모든 Store Service 조회
     *
     * @param request
     * @return PageResponse<StoreServiceResponse>
     */
    public PageResponse<StoreServiceResponse> list(RetrieveStoreServiceRequest request){
        Page<im.fooding.core.model.store.StoreService> result = service.list("", request.getStoreId(), request.getServiceType(), request.getPageable());
        
        PageInfo pageinfo = PageInfo.of( result );
        return PageResponse.of(
                result.getContent().stream().map(StoreServiceResponse::of).collect(Collectors.toList()),
                pageinfo
        );
    }

    /**
     * 특정 가게의 Store Service 조회
     *
     * @param storeId
     * @return List<StoreServiceType>
     */
    public List<StoreServiceResponse> findSignedStoreService( Long storeId ){
        return service.findByStoreId( storeId ).stream().map(StoreServiceResponse::of).collect(Collectors.toList());
    }


    /**
     * 특정 Store Service 조회
     *
     * @param id
     * @return StoreServiceResponse
     */
    public StoreServiceResponse findById( Long id ){
        return StoreServiceResponse.of( service.findById(id) );
    }

    /**
     * 서비스 활성화
     *
     * @param id
     */
    public void active( Long id ){ service.active( id ); }

    /**
     * 서비스 비활성화
     *
     * @param id
     */
    public void inactive( Long id ){ service.inactive( id  ); }

    /**
     * 서비스 삭제
     *
     * @param id
     */
    public void delete( Long id, Long deletedBy ){ service.delete( id, deletedBy );}
}
