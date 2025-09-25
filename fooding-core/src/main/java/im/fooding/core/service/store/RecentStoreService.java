package im.fooding.core.service.store;

import im.fooding.core.model.store.RecentStore;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreStatus;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.store.recent.RecentStoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecentStoreService {
    private final RecentStoreRepository repository;

    public RecentStore create(User user, Store store) {
        RecentStore recentStore = RecentStore.builder()
                .user(user)
                .store(store)
                .build();
        return repository.save(recentStore);
    }

    public void update(User user, Store store) {
        repository.findByUserIdAndStoreId(user.getId(), store.getId()).ifPresentOrElse(
                RecentStore::updateViewedAt,
                () -> this.create(user, store)
        );
    }

    public Page<Store> findRecentStores(long userId, Set<StoreStatus> statuses, Pageable pageable) {
        return repository.findRecentStores(userId, statuses, pageable);
    }
}
