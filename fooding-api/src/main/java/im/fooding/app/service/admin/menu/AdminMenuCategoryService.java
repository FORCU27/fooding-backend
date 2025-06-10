package im.fooding.app.service.admin.menu;

import im.fooding.app.dto.request.admin.menu.AdminMenuCategoryCreateRequest;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.menu.MenuCategoryService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMenuCategoryService {

    private final StoreService storeService;
    private final MenuCategoryService menuCategoryService;

    @Transactional
    public void create(AdminMenuCategoryCreateRequest request) {
        Store store = storeService.findById(request.getStoreId());

        menuCategoryService.create(store, request.getName(), request.getDescription(), request.getSortOrder());
    }
}
