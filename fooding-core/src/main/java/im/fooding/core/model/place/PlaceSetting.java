package im.fooding.core.model.place;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.Store;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "place_setting")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceSetting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "store_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Store store;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    @Column(name = "header_title", nullable = false)
    private String headerTitle;

    @Embedded
    private PlaceSettingBody body;

    @Builder
    private PlaceSetting(Store store, String metadata, String headerTitle, PlaceSettingBody body) {
        this.store = store;
        this.metadata = metadata;
        this.headerTitle = headerTitle;
        this.body = body;
    }

    public static PlaceSetting create(Store store, String metadata, String headerTitle, boolean bodyEnabled, String bodyContent) {
        return PlaceSetting.builder()
                .store(store)
                .metadata(metadata)
                .headerTitle(headerTitle)
                .body(PlaceSettingBody.builder()
                        .enabled(bodyEnabled)
                        .content(bodyContent)
                        .build())
                .build();
    }

    public void update(String metadata, String headerTitle, boolean bodyEnabled, String bodyContent) {
        this.metadata = metadata;
        this.headerTitle = headerTitle;
        if (this.body == null) {
            this.body = PlaceSettingBody.builder()
                    .enabled(bodyEnabled)
                    .content(bodyContent)
                    .build();
            return;
        }
        this.body.update(bodyEnabled, bodyContent);
    }
}
