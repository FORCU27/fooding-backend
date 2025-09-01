package im.fooding.core.event.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import im.fooding.core.model.store.StoreCategory;
import im.fooding.core.model.store.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreUpdatedEvent {
    private Long id;

    private String name;

    private StoreCategory category;

    private String address;

    private int reviewCount;

    private double averageRating;

    private int visitCount;

    private String regionId;

    private StoreStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;
}
