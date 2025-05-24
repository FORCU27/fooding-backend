package im.fooding.core.service.menu;

import im.fooding.core.dto.request.menu.MenuCreateRequest;
import im.fooding.core.dto.request.menu.MenuUpdateRequest;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.menu.Menu;
import im.fooding.core.model.menu.MenuCategory;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.repository.menu.MenuRepository;
import java.math.BigDecimal;
import java.util.List;
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
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public void create(MenuCreateRequest request) {
        Menu menu = Menu.builder()
                .category(request.category())
                .name(request.name())
                .price(request.price())
                .description(request.description())
                .imageUrl(request.imageUrl())
                .sortOrder(request.sortOrder())
                .isSignature(request.isSignature())
                .isRecommend(request.isRecommend())
                .build();

        menuRepository.save(menu);
    }

    public Menu get(Long id) {
        return menuRepository.findById(id)
                .filter(menu -> !menu.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.MENU_NOT_FOUND));
    }

    public Page<Menu> list(Pageable pageable) {
        return menuRepository.findAllByDeletedFalse(pageable);
    }

    @Transactional
    public void update(MenuUpdateRequest request) {
        Menu menu = menuRepository.findById(request.id())
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.MENU_NOT_FOUND));

        menu.updateCategory(request.category());
        menu.updateName(request.name());
        menu.updatePrice(request.price());
        menu.updateDescription(request.description());
        menu.updateImageUrl(request.imageUrl());
        menu.updateSortOrder(request.sortOrder());
        menu.updateIsSignature(request.isSignature());
        menu.updateIsRecommend(request.isRecommend());
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        Menu menu = get(id);

        menu.delete(deletedBy);
    }
    /**
     * 메뉴 목록 조회
     *
     * @param categoryIds
     * @return List<Menu>
     */
    public List<Menu> list(List<Long> categoryIds) {
        return menuRepository.list(categoryIds);
    }
}
