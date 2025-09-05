package im.fooding.core.model.banner;

import com.querydsl.core.annotations.QueryEntity;
import im.fooding.core.model.BaseDocument;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "banner")
@QueryEntity
public class Banner extends BaseDocument {

    @Id
    private ObjectId id;

    private String name;

    private String description;

    private boolean active = false;

    private int priority;

    private String link;

    private LinkType linkType;

    public enum Type {
        MAIN,
    }

    public enum LinkType {
        INTERNAL,       // 같은 사이트 내 페이지 이동
        EXTERNAL,       // 외부 사이트로 이동 (새창)
        // 특수 동작
        MODAL,          // 모달/팝업 열기
        DOWNLOAD,       // 파일 다운로드
        NONE,           // 클릭 불가 (순수 이미지)
        // 딥링크/앱 관련
        DEEPLINK,       // 앱 딥링크
        APP_STORE,      // APP_STORE 이동
        // 기능적 링크
        PHONE,          // 전화걸기
        EMAIL,          // 이메일
        SMS,            // SMS 발신
    }

    @Builder
    public Banner(
            String name,
            String description,
            boolean active,
            int priority,
            String link,
            LinkType linkType
    ) {
        this.name = name;
        this.description = description;
        this.active = active;
        this.priority = priority;
        this.link = link;
        this.linkType = linkType;
    }

    public void update(
            String name,
            String description,
            boolean active,
            int priority,
            String link,
            LinkType linkType
    ) {
        this.name = name;
        this.description = description;
        this.active = active;
        this.priority = priority;
        this.link = link;
        this.linkType = linkType;
    }
}
