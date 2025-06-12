package im.fooding.app.service.admin.menu;

import im.fooding.app.dto.request.admin.menu.AdminMenuCategoryCreateRequest;
import im.fooding.app.dto.request.admin.menu.AdminMenuCategoryListRequest;
import im.fooding.app.dto.request.admin.menu.AdminMenuCategoryUpdateRequest;
import im.fooding.app.dto.response.admin.menu.AdminMenuCategoryResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.menu.MenuCategory;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.menu.MenuCategoryService;
import im.fooding.core.service.store.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    public AdminMenuCategoryResponse get(long id) {
        return AdminMenuCategoryResponse.from(menuCategoryService.get(id));
    }

    public PageResponse<AdminMenuCategoryResponse> list(@Valid AdminMenuCategoryListRequest request) {
        Page<MenuCategory> menuCategories = menuCategoryService.list(request.getPageable());
        return PageResponse.of(
                menuCategories.stream().map(AdminMenuCategoryResponse::from).toList(),
                PageInfo.of(menuCategories)
        );
    }

    @Transactional
    public void update(long id, @Valid AdminMenuCategoryUpdateRequest request) {
        Store store = storeService.findById(request.getStoreId());

        menuCategoryService.update(id, store, request.getName(), request.getDescription(), request.getSortOrder());
    }
}
