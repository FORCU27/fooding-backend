package im.fooding.core.model.menu;

import im.fooding.core.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "is_signature")
    private boolean isSignature;

    @Builder
    private Menu(
            String name,
            BigDecimal price,
            String description,
            String imageUrl,
            int sortOrder,
            boolean isSignature
    ) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
        this.isSignature = isSignature;
    }

    private void updateName(String name) {
        this.name = name;
    }

    private void updatePrice(BigDecimal price) {
        this.price = price;
    }

    private void updateDescription(String description) {
        this.description = description;
    }

    private void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private void updateSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    private void updateIsSignature(boolean isSignature) {
        this.isSignature = isSignature;
    }
}
