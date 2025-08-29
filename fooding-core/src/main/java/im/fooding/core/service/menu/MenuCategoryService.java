package im.fooding.core.service.menu;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.menu.MenuCategory;
import im.fooding.core.model.store.Store;
import im.fooding.core.repository.menu.MenuCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;

    public MenuCategory get(Long id) {
        return menuCategoryRepository.findById(id)
                .filter(menuCategory -> !menuCategory.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.MENUCATEGORY_NOT_FOUND));
    }

    /**
     * 메뉴 카테고리 생성
     *
     * @param store
     * @param categoryName
     * @param sortOrder
     */
    @Transactional
    public Long create(Store store, String categoryName, int sortOrder) {
        MenuCategory menuCategory = MenuCategory.builder()
                .store(store)
                .name(categoryName)
                .sortOrder(sortOrder)
                .build();
        return menuCategoryRepository.save(menuCategory).getId();
    }

    @Transactional
    public Long create(Store store, String categoryName, String description, int sortOrder) {
        MenuCategory menuCategory = MenuCategory.builder()
                .store(store)
                .name(categoryName)
                .description(description)
                .sortOrder(sortOrder)
                .build();

        return menuCategoryRepository.save(menuCategory).getId();
    }

    /**
     * 메뉴 카테고리 수정
     *
     * @param categoryId
     * @param categoryName
     */
    @Transactional
    public Long update(Long categoryId, String categoryName) {
        MenuCategory menuCategory = findByid(categoryId);
        menuCategory.updateCategoryName(categoryName);
        return menuCategory.getId();
    }

    @Transactional
    public void update(long categoryId, Store store, String categoryName, String description, int sortOrder) {
        MenuCategory menuCategory = findByid(categoryId);

        menuCategory.updateStore(store);
        menuCategory.updateCategoryName(categoryName);
        menuCategory.updateDescription(description);
        menuCategory.updateSortOrder(sortOrder);
    }

    /**
     * 메뉴 카테고리 삭제
     *
     * @param userId
     * @param categoryId
     */
    @Transactional
    public void delete(Long userId, Long categoryId) {
        MenuCategory menuCategory = findByid(categoryId);
        menuCategory.delete(userId);
    }

    /**
     * 메뉴 카테고리 목록 조회
     *
     * @param storeId
     * @return List<MenuCategory>
     */
    public List<MenuCategory> list(long storeId) {
        return menuCategoryRepository.findAllByStoreIdAndDeletedFalse(storeId);
    }

    public Page<MenuCategory> list(Pageable pageable) {
        return menuCategoryRepository.findAllByDeletedFalse(pageable);
    }

    public Page<MenuCategory> list(Long storeId, String searchString, Pageable pageable) {
        return menuCategoryRepository.list(storeId, searchString, pageable);
    }

    /**
     * 메뉴 카테고리 정렬
     *
     * @param menuCategoryIds
     */
    @Transactional
    public void sort(List<Long> menuCategoryIds) {
        int sortOrder = 1;
        for (Long id : menuCategoryIds) {
            MenuCategory menuCategory = findByid(id);
            menuCategory.updateSortOrder(sortOrder++);
        }
    }

    /**
     * 메뉴 카테고리 최대 정렬 순서 조회
     *
     * @param storeId
     */
    public int getSortOrder(Long storeId) {
        return menuCategoryRepository.getMaxSortOrder(storeId);
    }

    private MenuCategory findByid(Long id) {
        return menuCategoryRepository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.MENUCATEGORY_NOT_FOUND));
    }
}
