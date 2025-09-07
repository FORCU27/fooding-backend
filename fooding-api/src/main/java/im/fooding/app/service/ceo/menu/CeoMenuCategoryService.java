package im.fooding.app.service.ceo.menu;

import im.fooding.app.dto.request.ceo.menu.CeoSortMenuCategoryRequest;
import im.fooding.app.dto.response.ceo.menu.CeoMenuCategoryResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.menu.MenuCategoryService;
import im.fooding.core.service.store.StoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CeoMenuCategoryService {

    private final MenuCategoryService menuCategoryService;
    private final StoreService storeService;

    public Long create(Long storeId, String categoryName) {
        Store store = storeService.findById(storeId);
        int sortOrder = menuCategoryService.getSortOrder(storeId);

        return menuCategoryService.create(store, categoryName, sortOrder);
    }

    public Long update(Long categoryId, String categoryName) {
        return menuCategoryService.update(categoryId, categoryName);
    }

    public void delete(Long categoryId, Long userId) {
        menuCategoryService.delete(categoryId, userId);
    }

    public List<CeoMenuCategoryResponse> list(Long storeId) {
        return menuCategoryService.list(storeId)
                .stream()
                .map(CeoMenuCategoryResponse::of)
                .toList();
    }

    public void sort(CeoSortMenuCategoryRequest request) {
        menuCategoryService.sort(request.getMenuCategoryIds());
    }
}
