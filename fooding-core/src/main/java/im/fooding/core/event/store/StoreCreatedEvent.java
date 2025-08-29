package im.fooding.core.event.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import im.fooding.core.model.store.StoreCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreCreatedEvent {
    private Long id;

    private String name;

    private StoreCategory category;

    private String address;

    private int reviewCount;

    private double averageRating;

    private int visitCount;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    public StoreCreatedEvent(Long id) {
        this.id = id;
    }
}
