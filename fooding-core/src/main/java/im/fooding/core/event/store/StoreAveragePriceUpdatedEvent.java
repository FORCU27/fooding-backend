package im.fooding.core.event.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreAveragePriceUpdatedEvent {
    private Long id;
    private int averagePrice;
}
