package im.fooding.core.model.naverplace;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NaverPlace {

    @Id
    private String id;

    private String name;
    private String category;
    private String address;
    private String contact;
    private List<Menu> menus;
    
    @Column(columnDefinition = "boolean default false")
    private boolean isUploaded = false;

    @Builder
    public NaverPlace(
            String id,
            String name,
            String category,
            String address,
            String contact,
            List<Menu> menus
    ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.address = address;
        this.contact = contact;
        this.menus = menus;
        this.isUploaded = false;
    }
    
    public void markAsUploaded() {
        this.isUploaded = true;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Menu {
        private String name;

        @Column(nullable = false)
        private Integer price;
    }
}
