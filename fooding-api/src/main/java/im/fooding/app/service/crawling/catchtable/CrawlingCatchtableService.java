package im.fooding.app.service.crawling.catchtable;

import im.fooding.app.dto.request.crawling.catchtable.CatchTableMenuRequest;
import im.fooding.app.dto.request.crawling.catchtable.CatchTableStoreRequest;
import im.fooding.app.dto.response.crawling.GetStoreDocumentResponse;
import im.fooding.app.dto.response.crawling.GetStoreMenuDocumentResponse;
import im.fooding.core.model.store.document.CatchTableStoreDocument;
import im.fooding.core.model.store.document.StoreDocument;
import im.fooding.core.model.store.document.StoreMenuDocument;
import im.fooding.core.model.user.User;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.store.document.CatchTableStoreDocumentService;
import im.fooding.core.service.store.document.StoreDocumentService;
import im.fooding.core.service.store.document.StoreMenuDocumentService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlingCatchtableService {
    private final CatchTableStoreDocumentService documentService;
    private final StoreMenuDocumentService menuDocumentService;
    private final StoreService storeService;

    public String saveDocuments(CatchTableStoreRequest request){
        // 1개의 Request = 1개의 Store
        System.out.println( request.getStoreName() );
        CatchTableStoreDocument storeDocument = CatchTableStoreDocument.builder()
                .name(request.getStoreName())
                .city(request.getRegion())
                .category(request.getCategory())
                .description(request.getDescription())
                .priceCategory(request.getPriceCategory())
                .direction(request.getDirection())
                .information(request.getInformation())
                .address(request.getAddress())
                .build();
        String storeDocumentId = documentService.save( storeDocument );
        return storeDocumentId;
    }

    public void saveMenuDocument(CatchTableMenuRequest request){
        StoreMenuDocument menuDocument = StoreMenuDocument.builder()
                .storeId( request.getStoreId() )
                .menuName( request.getMenuName() )
                .price( request.getPrice() )
                .build();
        menuDocumentService.save( menuDocument );
    }

    // 만들어진 모든 MongoDB의 Document를 조회하는 함수
    public Page<GetStoreDocumentResponse> getDocuments(Pageable pageable){
        // fullTextSearch 함수가 null 값들을 받아낼 수 있는지 확인 필요
        return null;
    }

    // Document ID를 통해 특정 Document의 정보를 확인하는 함수
    public GetStoreDocumentResponse findDocumentById(String documentId){
        // Document ID를 통해 특정 Document의 정보를 불러오는 함수 필요
        GetStoreDocumentResponse response = GetStoreDocumentResponse.of( documentService.findById( documentId ) );
        List<StoreMenuDocument> menuList = menuDocumentService.findByStoreId(documentId).stream().toList();
        response.setMenuList( menuList.stream().map(GetStoreMenuDocumentResponse::of).toList() );
        return response;
    }

    // MongoDB의 Document의 ID를 통해 해당 ID의 Document를 MySQL Store Entity로 저장하는 함수
    public void create( String documentId ){
        CatchTableStoreDocument document = documentService.findById( documentId );
        // owner, name, region, city, address, category, description, priceCategory, eventDescription, contactNumber, direction, information,
        //      isParkingAvailable, isNewOpen, isTakeOut, latitude, longitude
        storeService.create(
                null, document.getName(), null, document.getCity(), document.getAddress(), document.getCategory(), document.getDescription(), document.getPriceCategory(),
                "", "", document.getDirection(), document.getInformation(), false, false, false, 0.0, 0.0
        );
    }
}
