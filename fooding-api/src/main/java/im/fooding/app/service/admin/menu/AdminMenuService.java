package im.fooding.app.service.admin.menu;


import im.fooding.app.dto.request.admin.menu.AdminMenuCreateRequest;
import im.fooding.app.dto.request.admin.menu.AdminMenuUpdateRequest;
import im.fooding.app.dto.response.admin.menu.AdminMenuResponse;
import im.fooding.app.dto.response.admin.waiting.AdminWaitingResponse;
import im.fooding.app.service.file.FileUploadService;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.file.File;
import im.fooding.core.model.menu.Menu;
import im.fooding.core.model.menu.MenuCategory;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.service.menu.MenuCategoryService;
import im.fooding.core.service.menu.MenuService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.waiting.WaitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMenuService {

    private final MenuService menuService;
    private final StoreService storeService;
    private final MenuCategoryService menuCategoryService;
    private final FileUploadService fileUploadService;

    @Transactional
    public void create(AdminMenuCreateRequest request) {
        Store store = storeService.findById(request.storeId());
        MenuCategory menuCategory = menuCategoryService.get(request.categoryId());

        String menuImageUrl = null;
        if (StringUtils.hasText(request.imageId())) {
            File file = fileUploadService.commit(request.imageId());
            menuImageUrl = file.getUrl();
        }

        menuService.create(request.toMenuCreateRequest(store, menuCategory, menuImageUrl));
    }

    public AdminMenuResponse get(long id) {
        return AdminMenuResponse.from(menuService.get(id));
    }

    public PageResponse<AdminMenuResponse> list(BasicSearch search) {
        Page<Menu> menus = menuService.list(search.getPageable());
        return PageResponse.of(menus.stream().map(AdminMenuResponse::from).toList(), PageInfo.of(menus));
    }

    @Transactional
    public void update(long id, AdminMenuUpdateRequest request) {
        Store store = storeService.findById(request.storeId());
        MenuCategory menuCategory = menuCategoryService.get(id);

        String menuImageUrl = null;
        if (StringUtils.hasText(request.imageId())) {
            File file = fileUploadService.commit(request.imageId());
            menuImageUrl = file.getUrl();
        }

        menuService.update(request.toMenuUpdateRequest(id, store, menuCategory, menuImageUrl));
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        menuService.delete(id, deletedBy);
    }
}
