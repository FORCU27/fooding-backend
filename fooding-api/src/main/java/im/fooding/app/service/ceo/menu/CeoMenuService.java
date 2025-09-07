package im.fooding.app.service.ceo.menu;


import im.fooding.app.dto.request.ceo.menu.CeoMenuCreateRequest;
import im.fooding.app.dto.request.ceo.menu.CeoMenuListRequest;
import im.fooding.app.dto.request.ceo.menu.CeoMenuUpdateRequest;
import im.fooding.app.dto.response.ceo.menu.CeoMenuResponse;
import im.fooding.app.service.file.FileUploadService;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.file.File;
import im.fooding.core.model.menu.Menu;
import im.fooding.core.model.menu.MenuCategory;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.menu.MenuCategoryService;
import im.fooding.core.service.menu.MenuService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CeoMenuService {

    private final MenuService menuService;
    private final StoreService storeService;
    private final MenuCategoryService menuCategoryService;
    private final FileUploadService fileUploadService;

    @Transactional
    public void create(CeoMenuCreateRequest request) {
        Store store = storeService.findById(request.getStoreId());
        MenuCategory menuCategory = menuCategoryService.get(request.getCategoryId());

        String menuImageUrl = null;
        if (StringUtils.hasText(request.getImageId())) {
            File file = fileUploadService.commit(request.getImageId());
            menuImageUrl = file.getUrl();
        }

        menuService.create(request.toMenuCreateRequest(store, menuCategory, menuImageUrl));
    }

    public CeoMenuResponse get(long id) {
        return CeoMenuResponse.from(menuService.get(id));
    }

    public PageResponse<CeoMenuResponse> list(CeoMenuListRequest search) {
        Page<Menu> menus = menuService.list(search.getStoreId(), search.getSearchString(), search.getPageable());
        return PageResponse.of(menus.stream().map(CeoMenuResponse::from).toList(), PageInfo.of(menus));
    }

    @Transactional
    public void update(long id, CeoMenuUpdateRequest request) {
        Store store = storeService.findById(request.getStoreId());
        MenuCategory menuCategory = menuCategoryService.get(request.getCategoryId());

        String menuImageUrl = null;
        if (StringUtils.hasText(request.getImageId())) {
            File file = fileUploadService.commit(request.getImageId());
            menuImageUrl = file.getUrl();
        }

        menuService.update(request.toMenuUpdateRequest(id, store, menuCategory, menuImageUrl));
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        menuService.delete(id, deletedBy);
    }
}
