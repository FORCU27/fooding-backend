package im.fooding.core.model.notification;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class UserNotification extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private User user;

  @Column(nullable = false)
  private String title;

  @Lob
  @Column(nullable = false, columnDefinition = "LONGTEXT")
  private String content;

  // EVENT
  @Column(nullable = false)
  private String category;

  @Column(nullable = false)
  private LocalDateTime sentAt;

  @Column(nullable = false)
  @ColumnDefault("false")
  private boolean isRead;

  @Builder
  public UserNotification(User user, String title, String content, String category, LocalDateTime sentAt) {
    this.user = user;
    this.title = title;
    this.content = content;
    this.category = category;
    this.sentAt = sentAt;
  }

  public void update(String title, String content, String category) {
    this.title = title;
    this.content = content;
    this.category = category;
  }

  public void read() {
    this.isRead = true;
  }
}
