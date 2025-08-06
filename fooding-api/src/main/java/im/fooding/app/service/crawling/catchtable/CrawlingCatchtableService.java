package im.fooding.app.service.crawling.catchtable;

import im.fooding.app.dto.response.crawling.GetStoreDocumentResponse;
import im.fooding.core.model.store.document.StoreDocument;
import im.fooding.core.model.user.User;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.store.document.StoreDocumentService;
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
    private final StoreDocumentService documentService;
    private final StoreService storeService;
    private final UserService userService;

    // CSV 파일의 Store 정보를 MongoDB Document로 만드는 함수
    private List<StoreDocument> csvToDocument() {
        // csv 파일을 읽어옴
        String fileName = "";
        String line = "";
        String SPLIT_BY = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            List<StoreDocument> documentList = new ArrayList<StoreDocument>();
            while ((line = br.readLine()) != null) {
                String[] store = line.split(SPLIT_BY);
                // 이름, 설명, 주소, 가격대, 시간대, 지역, 분류, 위치
                // Document에 정의되지 않은 값들은 무시
                StoreDocument storeDoc = StoreDocument.builder()
                        .name( store[0] )
                        .category( store[6] )
                        .address( store[2] )
                        .reviewCount( 0 )
                        .averageRating( 0 )
                        .visitCount( 0 )
                        .createdAt( LocalDateTime.now() )
                        .build();
                documentList.add( storeDoc );
            }
            return documentList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveDocuments(){
        List<StoreDocument > documentList = this.csvToDocument();
        for( StoreDocument document : documentList )documentService.save( document );
    }

    // 만들어진 모든 MongoDB의 Document를 조회하는 함수
    public Page<GetStoreDocumentResponse> getDocuments(Pageable pageable){
        // fullTextSearch 함수가 null 값들을 받아낼 수 있는지 확인 필요
        return documentService.fullTextSearch( null, null, null, pageable ).map(GetStoreDocumentResponse::of);
    }

    // Document ID를 통해 특정 Document의 정보를 확인하는 함수
    public GetStoreDocumentResponse findDocumentById(long documentId){
        // Document ID를 통해 특정 Document의 정보를 불러오는 함수 필요
        return GetStoreDocumentResponse.of( documentService.findById( documentId ) );
    }

    // MongoDB의 Document의 ID를 통해 해당 ID의 Document를 MySQL Store Entity로 저장하는 함수
    public void create( long documentId ){
        StoreDocument document = documentService.findById( documentId );
        // owner, name, region, city, address, category, description, priceCategory, eventDescription, contactNumber, direction, information,
        //      isParkingAvailable, isNewOpen, isTakeOut, latitude, longitude
        storeService.create(
                null, document.getName(), null, null, document.getAddress(), document.getCategory(), null, null, "",
                "", "", "", false, false, false, null, null
        );
    }
}
