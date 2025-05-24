package im.fooding.core.service.store;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreMember;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.store.StoreMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StoreMemberService {

    private final StoreMemberRepository storeMemberRepository;

    public Page<Store> getStores(Long userId, Pageable pageable) {
        return storeMemberRepository.findByUserIdAndDeletedFalse(userId, pageable)
                .map(StoreMember::getStore);
    }

    public void create(Store store, User user) {
        storeMemberRepository.save(
                StoreMember.builder()
                    .store(store)
                    .user(user)
                    .build()
        );
    }
}
