package im.fooding.core.model.notification;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "user_notification")
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

    @Column(nullable = false)
    private LocalDateTime sentAt;

    private LocalDateTime readAt;

    @Builder
    public UserNotification(User user, String title, String content, LocalDateTime sentAt) {
      this.user = user;
      this.title = title;
      this.content = content;
      this.sentAt = sentAt;
    }

    public void read() {
      this.readAt = LocalDateTime.now();
  }
}
