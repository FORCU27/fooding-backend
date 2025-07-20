package im.fooding.core.model.store.subway;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class KakaoMapResponse {
    private Meta meta;
    private List<Document> documents;
}
