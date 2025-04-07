package im.fooding.core.model.store;

import im.fooding.core.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "stores")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "store_number", nullable = false)
    private String storeNumber;

    @Column(name = "home_page_url")
    private String homePageUrl;

    @Column(nullable = false)
    private String direction;

    @Column(nullable = false)
    private String storeInfo;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isParkingAvailable;

    @Builder
    public Store(
            String name,
            String description,
            String storeNumber,
            String homePageUrl,
            String direction,
            String storeInfo,
            boolean isParkingAvailable
    ) {
        this.name = name;
        this.description = description;
        this.storeNumber = storeNumber;
        this.homePageUrl = homePageUrl;
        this.direction = direction;
        this.storeInfo = storeInfo;
        this.isParkingAvailable = isParkingAvailable;
    }
}
