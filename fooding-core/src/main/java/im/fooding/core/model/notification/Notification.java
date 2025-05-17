package im.fooding.core.model.notification;

import im.fooding.core.model.BaseEntity;
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
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationCategory category;

    private LocalDateTime sentAt;

    private LocalDateTime readAt;

    private LocalDateTime scheduledAt;

    @Builder
    public Notification(String source, String destination,
                        String title, String content,
                        NotificationChannel channel, NotificationStatus status,
                        NotificationCategory category,
                        LocalDateTime scheduledAt) {
      this.source = source;
      this.destination = destination;
      this.title = title;
      this.content = content;
      this.channel = channel;
      this.status = status;
      this.category = category;
      this.scheduledAt = scheduledAt;
    }

    public void update(String title, String content, NotificationChannel channel, LocalDateTime scheduledAt) {
      this.title = title;
      this.content = content;
      this.channel = channel;
      this.scheduledAt = scheduledAt;
      this.status = NotificationStatus.PENDING;
    }

    public void send() {
      this.sentAt = LocalDateTime.now();
      this.status = NotificationStatus.COMPLETED;
    }

    public void read() {
      this.readAt = LocalDateTime.now();
      }

    public void schedule(LocalDateTime scheduledAt) {
    this.scheduledAt = scheduledAt;
    this.status = NotificationStatus.PENDING;
  }
}
