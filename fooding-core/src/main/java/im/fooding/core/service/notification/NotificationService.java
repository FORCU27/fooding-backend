package im.fooding.core.service.notification;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.notification.Notification;
import im.fooding.core.model.notification.NotificationChannel;
import im.fooding.core.model.notification.NotificationSortType;
import im.fooding.core.repository.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Notification findById(Long id) {
        return notificationRepository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.NOTIFICATION_NOT_FOUND));
    }

    @Transactional
    public Notification create(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Transactional
    public void update(Long id, String title, String content, NotificationChannel channel, LocalDateTime scheduledAt) {
        Notification notification = findById(id);
        notification.update(title, content, channel, scheduledAt);
    }

    @Transactional
    public void delete(Long id, Long deletedBy) {
        Notification notification = findById(id);
        notification.delete(deletedBy);
    }

    public Page<Notification> findAll(Pageable pageable, NotificationSortType sortType, SortDirection sortDirection) {
        Sort sort;
        switch (sortType) {
            case TITLE -> sort = Sort
                    .by(sortDirection == SortDirection.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC, "title");
            case RECENT ->
                sort = Sort.by(sortDirection == SortDirection.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC,
                        "createdAt");
            default -> sort = Sort.by(Sort.Direction.DESC, "createdAt");
        }
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return notificationRepository.findAll(sortedPageable);
    }
}
