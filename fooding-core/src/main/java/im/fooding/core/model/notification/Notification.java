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
@Table(name = "notification")
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User destination;

    private String sourceEmail;

    private String destinationEmail;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    private LocalDateTime sentAt;

    private LocalDateTime readAt;

    private LocalDateTime scheduledAt;

    @Builder
    public Notification(User source, User destination, String sourceEmail, String destinationEmail,
                        String title, String content, NotificationChannel channel, NotificationStatus status) {
      this.source = source;
      this.destination = destination;
      this.sourceEmail = sourceEmail;
      this.destinationEmail = destinationEmail;
      this.title = title;
      this.content = content;
      this.channel = channel;
      this.status = status;
    }

    public void markAsSent() {
      this.sentAt = LocalDateTime.now();
      this.status = NotificationStatus.COMPLETED;
    }

    public void markAsRead() {
      this.readAt = LocalDateTime.now();
      }

    public void scheduleAt(LocalDateTime scheduledAt) {
    this.scheduledAt = scheduledAt;
    this.status = NotificationStatus.PENDING;
  }
}
