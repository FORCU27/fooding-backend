package im.fooding.core.model.post;

import im.fooding.core.model.BaseEntity;
import jakarta.persistence.*;
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
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostType type;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isVisibleOnHomepage;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isVisibleOnPos;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isVisibleOnCeo;

    @Builder
    private Post(
            String     title,
            String     content,
            PostType   type,
            boolean    isVisibleOnHomepage,
            boolean    isVisibleOnPos,
            boolean    isVisibleOnCeo
    ) {
      this.title                 = title;
      this.content               = content;
      this.type                  = type;
      this.isVisibleOnHomepage   = isVisibleOnHomepage;
      this.isVisibleOnPos        = isVisibleOnPos;
      this.isVisibleOnCeo        = isVisibleOnCeo;
    }

    public void update(
            String  title,
            String  content,
            boolean isVisibleOnHomepage,
            boolean isVisibleOnPos,
            boolean isVisibleOnCeo
    ) {
      this.title               = title;
      this.content             = content;
      this.isVisibleOnHomepage = isVisibleOnHomepage;
      this.isVisibleOnPos      = isVisibleOnPos;
      this.isVisibleOnCeo      = isVisibleOnCeo;
    }
}
