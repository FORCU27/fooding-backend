package im.fooding.app.service.ceo.menu;


import im.fooding.app.dto.request.ceo.menu.CeoMenuCreateRequest;
import im.fooding.app.dto.request.ceo.menu.CeoMenuListRequest;
import im.fooding.app.dto.request.ceo.menu.CeoMenuUpdateRequest;
import im.fooding.app.dto.response.ceo.menu.CeoMenuResponse;
import im.fooding.app.service.file.FileUploadService;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.event.store.StoreAveragePriceUpdatedEvent;
import im.fooding.core.global.kafka.EventProducerService;
import im.fooding.core.model.file.File;
import im.fooding.core.model.menu.Menu;
import im.fooding.core.model.menu.MenuCategory;
import im.fooding.core.model.menu.MenuImage;
import im.fooding.core.model.store.Store;
import im.fooding.core.repository.menu.MenuFilter;
import im.fooding.core.service.menu.MenuCategoryService;
import im.fooding.core.service.menu.MenuImageService;
import im.fooding.core.service.menu.MenuService;
import im.fooding.core.service.store.StoreService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final MenuImageService menuImageService;
    private final StoreService storeService;
    private final MenuCategoryService menuCategoryService;
    private final FileUploadService fileUploadService;
    private final EventProducerService eventProducerService;

    @Transactional
    public void create(CeoMenuCreateRequest request) {
        Store store = storeService.findById(request.getStoreId());
        MenuCategory menuCategory = menuCategoryService.get(request.getCategoryId());

        List<String> menuImageUrls = new ArrayList<>();
        request.getImageIds().forEach(imageId -> {
            if (StringUtils.hasText(imageId)) {
                File file = fileUploadService.commit(imageId);
                menuImageUrls.add(file.getUrl());
            }
        });

        long menuId = menuService.create(request.toMenuCreateRequest(store, menuCategory));
        Menu menu = menuService.get(menuId);
        menuImageUrls.forEach(imageUrl -> menuImageService.create(menu, imageUrl));

        updateStorageAveragePrice(store);
    }

    public CeoMenuResponse get(long id) {
        Menu menu = menuService.get(id);
        List<MenuImage> menuImages = menuImageService.listByMenuId(id);

        return CeoMenuResponse.of(menu, menuImages);
    }

    public PageResponse<CeoMenuResponse> list(CeoMenuListRequest search) {
        MenuFilter filter = MenuFilter.builder()
                .storeId(search.getStoreId())
                .menuCategoryIds(List.of(search.getCategoryId()))
                .build();
        Page<Menu> menus = menuService.list(filter, search.getPageable());
        Map<Menu, List<MenuImage>> menuImages = new HashMap<>();

        menus.forEach(menu -> menuImages.put(menu, menuImageService.listByMenuId(menu.getId())));
        List<CeoMenuResponse> menuResponses = menus.stream().map(
                menu -> CeoMenuResponse.of(menu, menuImages.get(menu))
        ).toList();

        return PageResponse.of(menuResponses, PageInfo.of(menus));
    }

    @Transactional
    public void update(long id, CeoMenuUpdateRequest request, long updatedBy) {
        Store store = storeService.findById(request.getStoreId());
        MenuCategory menuCategory = menuCategoryService.get(request.getCategoryId());

        List<String> menuImageUrls = new ArrayList<>();
        request.getImageIds().forEach(imageId -> {
            if (StringUtils.hasText(imageId)) {
                File file = fileUploadService.commit(imageId);
                menuImageUrls.add(file.getUrl());
            }
        });
        Menu menu = menuService.get(id);
        List<MenuImage> menuImages = menuImageService.listByMenuId(id);
        menuImages.stream().filter(menuImage -> !menuImageUrls.contains(menuImage.getImageUrl()))
                .forEach(menuImage -> menuImage.delete(updatedBy));
        menuImageUrls.stream().filter(menuImageUrl -> !menuImages.contains(menuImageUrl))
                .forEach(menuImageUrl -> menuImageService.create(menu, menuImageUrl));

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
}
