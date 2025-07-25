package im.fooding.core.service.store.subway;

import im.fooding.core.model.store.subway.Document;
import im.fooding.core.model.store.subway.KakaoMapResponse;
import im.fooding.core.model.store.subway.SubwayStation;
import im.fooding.core.repository.store.subway.SubwayStationRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@RequiredArgsConstructor
@Slf4j
public class SubwayStationService {
    private final SubwayStationRepository repository;

    // 추후 환경 변수로 등록
    @Value("${address.kakaomap.url}")
    private String KAKAO_MAP_API_BASE_URL;

    @Value("${address.kakaomap.api-key}")
    private String KAKAO_API_KEY;

    private final RestTemplate restTemplate = new RestTemplate();
    private final int FIND_COUNTS = 3;
    private final int FIND_RANGE = 1000;    // m 단위

    private SubwayStation create( String name, String line, String address ){
        SubwayStation station = SubwayStation.builder()
                .name( name )
                .line( line )
                .address( address )
                .build();
        return repository.save( station );
    }

    public SubwayStation findStation( String name, String line ){
        return repository.findByNameAndLine( name, line );
    }

    public List<SubwayStation> getNearStations(double latitude, double longitude ){
        String url = KAKAO_MAP_API_BASE_URL + "/search/category.json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + KAKAO_API_KEY);
        headers.set("Content-Type", "application/json");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("category_group_code", "SW8")
                .queryParam("x", longitude)
                .queryParam("y", latitude)
                .queryParam("radius", FIND_RANGE)
                .queryParam("size", FIND_COUNTS);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<KakaoMapResponse> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                KakaoMapResponse.class
        );
        List<SubwayStation> stations = new ArrayList<SubwayStation>();
        for( Document stationInfo : response.getBody().getDocuments() ){
            String[] stationName = stationInfo.getPlace_name().split(" ");
            SubwayStation station = this.findStation( stationName[0], stationName[1] );
            if( station == null ) station = this.create( stationName[0], stationName[1], stationInfo.getRoad_address_name() );
            stations.add( station );
        }
        return stations;
    }

}
