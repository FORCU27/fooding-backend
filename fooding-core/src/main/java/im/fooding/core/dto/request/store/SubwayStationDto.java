package im.fooding.core.dto.request.store;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import im.fooding.core.model.store.subway.SubwayStation;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubwayStationDto {
    private long id;
    private String name;
    private String line;
    private String address;

    public static SubwayStationDto of(SubwayStation subwayStation){
        return SubwayStationDto.builder()
                .id(subwayStation.getId())
                .name(subwayStation.getName())
                .line(subwayStation.getLine())
                .address(subwayStation.getAddress())
                .build();
    }
}
