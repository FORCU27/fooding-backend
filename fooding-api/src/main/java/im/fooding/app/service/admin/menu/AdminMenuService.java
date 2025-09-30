package im.fooding.app.service.admin.menu;


import im.fooding.app.dto.request.admin.menu.AdminMenuCreateRequest;
import im.fooding.app.dto.request.admin.menu.AdminMenuUpdateRequest;
import im.fooding.app.dto.response.admin.menu.AdminMenuResponse;
import im.fooding.app.service.file.FileUploadService;
import im.fooding.app.dto.request.admin.menu.AdminMenuListRequest;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.event.store.StoreAveragePriceUpdatedEvent;
import im.fooding.core.global.kafka.EventProducerService;
import im.fooding.core.model.file.File;
import im.fooding.core.model.menu.Menu;
import im.fooding.core.model.menu.MenuCategory;
import im.fooding.core.model.menu.MenuImage;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.menu.MenuCategoryService;
import im.fooding.core.service.menu.MenuImageService;
import im.fooding.core.service.menu.MenuService;
import im.fooding.core.service.store.StoreService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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
    private final MenuImageService menuImageService;
    private final StoreService storeService;
    private final MenuCategoryService menuCategoryService;
    private final FileUploadService fileUploadService;
    private final EventProducerService eventProducerService;

    @Transactional
    public void create(AdminMenuCreateRequest request) {
        Store store = storeService.findById(request.storeId());
        MenuCategory menuCategory = menuCategoryService.get(request.categoryId());

        long menuId = menuService.create(request.toMenuCreateRequest(store, menuCategory));
        Menu menu = menuService.get(menuId);

        request.imageIds().forEach(imageId -> {
            if (StringUtils.hasText(imageId)) {
                File file = fileUploadService.commit(imageId);
                if (file != null) {
                    menuImageService.create(menu, imageId, file.getUrl());
                }
            }
        });

        updateStorageAveragePrice(store);
    }

    public AdminMenuResponse get(long id) {
        Menu menu = menuService.get(id);
        List<MenuImage> menuImages = menuImageService.listByMenuId(id);

        return AdminMenuResponse.of(menu, menuImages);
    }

    public PageResponse<AdminMenuResponse> list(AdminMenuListRequest search) {
        Page<Menu> menus = menuService.list(search.getStoreId(), search.getSearchString(), search.getPageable());
        Map<Menu, List<MenuImage>> menuImages = new HashMap<>();

        menus.forEach(menu -> menuImages.put(menu, menuImageService.listByMenuId(menu.getId())));
        List<AdminMenuResponse> menuResponses = menus.stream().map(
                menu -> AdminMenuResponse.of(menu, menuImages.get(menu))
        ).toList();

        return PageResponse.of(menuResponses, PageInfo.of(menus));
    }

    @Transactional
    public void update(long id, AdminMenuUpdateRequest request, long updatedBy) {
        Store store = storeService.findById(request.storeId());
        MenuCategory menuCategory = menuCategoryService.get(request.categoryId());

        Menu menu = menuService.get(id);
        List<MenuImage> menuImages = menuImageService.listByMenuId(id);

        Set<Long> requestedExistingIds = request.imageIds().stream()
                .filter(StringUtils::hasText)
                .filter(this::isNumeric)
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        menuImages.stream()
                .filter(menuImage -> !requestedExistingIds.contains(menuImage.getId()))
                .forEach(menuImage -> menuImage.delete(updatedBy));

        request.imageIds().stream()
                .filter(StringUtils::hasText)
                .filter(imageId -> !isNumeric(imageId))
                .map(fileUploadService::commit)
                .filter(Objects::nonNull)
                .forEach(file -> menuImageService.create(menu, file.getId(), file.getUrl()));

        menuService.update(request.toMenuUpdateRequest(id, store, menuCategory));
        updateStorageAveragePrice(store);
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        menuService.delete(id, deletedBy);
    }

    private void updateStorageAveragePrice(Store store) {
        int averagePrice = menuService.getAveragePrice(store.getId());
        eventProducerService.publishEvent("StoreAveragePriceUpdatedEvent", new StoreAveragePriceUpdatedEvent(store.getId(), averagePrice));
    }

    private boolean isNumeric(String value) {
        if (!StringUtils.hasText(value)) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
