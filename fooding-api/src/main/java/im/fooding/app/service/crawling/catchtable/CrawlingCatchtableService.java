package im.fooding.app.service.crawling.catchtable;

import im.fooding.core.model.store.document.StoreDocument;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.store.document.StoreDocumentService;
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

    // CSV 파일의 Store 정보를 MongoDB Document로 만드는 함수
    public List<StoreDocument> csvToDocument() {
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

    public void saveDocuments( List<StoreDocument> documentList ){
        for( StoreDocument document : documentList )documentService.save( document );
    }

    // 만들어진 모든 MongoDB의 Document를 조회하는 함수
    public Page<StoreDocument> getDocuments(Pageable pageable){
        // fullTextSearch 함수가 null 값들을 받아낼 수 있는지 확인 필요
        return documentService.fullTextSearch( null, null, null, pageable );
    }

    // Document ID를 통해 특정 Document의 정보를 확인하는 함수
    public StoreDocument findDocumentById(long documentId){
        // Document ID를 통해 특정 Document의 정보를 불러오는 함수 필요

    }

    // MongoDB의 Document의 ID를 통해 해당 ID의 Document를 MySQL Store Entity로 저장하는 함수
    public void create( long documentId ){
        
    }
}
