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
import im.fooding.core.model.store.Store;
import im.fooding.core.repository.menu.MenuFilter;
import im.fooding.core.service.menu.MenuCategoryService;
import im.fooding.core.service.menu.MenuService;
import im.fooding.core.service.store.StoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CeoMenuService {
    private final MenuService menuService;
    private final StoreService storeService;
    private final MenuCategoryService menuCategoryService;
    private final FileUploadService fileUploadService;
    private final EventProducerService eventProducerService;

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
        updateStorageAveragePrice(store);
    }

    public CeoMenuResponse get(long id) {
        return CeoMenuResponse.from(menuService.get(id));
    }

    public PageResponse<CeoMenuResponse> list(CeoMenuListRequest search) {
        MenuFilter filter = MenuFilter.builder()
                .storeId(search.getStoreId())
                .menuCategoryIds(List.of(search.getCategoryId()))
                .build();
        Page<Menu> menus = menuService.list(filter, search.getPageable());
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
