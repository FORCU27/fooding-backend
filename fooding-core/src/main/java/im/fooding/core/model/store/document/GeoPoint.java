package im.fooding.core.model.store.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GeoPoint {
    private double lat;
    private double lon;
}
