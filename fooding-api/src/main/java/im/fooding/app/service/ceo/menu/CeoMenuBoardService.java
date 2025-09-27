package im.fooding.app.service.ceo.menu;

import im.fooding.app.controller.ceo.menu.CeoMenuBoardCreateRequest;
import im.fooding.app.controller.ceo.menu.CeoMenuBoardListRequest;
import im.fooding.app.dto.response.ceo.menu.CeoMenuBoardResponse;
import im.fooding.app.service.file.FileUploadService;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.dto.request.menu.MenuBoardCreateRequest;
import im.fooding.core.model.file.File;
import im.fooding.core.model.menu.MenuBoard;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.menu.MenuBoardService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CeoMenuBoardService {

    private final MenuBoardService menuBoardService;
    private final StoreService storeService;
    private final FileUploadService fileUploadService;

    @Transactional
    public void create(long storeId, CeoMenuBoardCreateRequest request) {
        Store store = storeService.findById(storeId);

        String imageUrl = null;
        if (StringUtils.hasText(request.getImageId())) {
            File file = fileUploadService.commit(request.getImageId());
            imageUrl = file.getUrl();
        }

        MenuBoardCreateRequest menuBoardCreateRequest = MenuBoardCreateRequest.builder()
                .title(request.getTitle())
                .store(store)
                .imageUrl(imageUrl)
                .build();

        menuBoardService.create(menuBoardCreateRequest);
    }

    public CeoMenuBoardResponse get(long id) {
        return CeoMenuBoardResponse.from(menuBoardService.get(id));
    }

    public PageResponse<CeoMenuBoardResponse> list(long storeId, CeoMenuBoardListRequest request) {
        Page<MenuBoard> menuBoards = menuBoardService.listByStoreId(storeId, request.getPageable());
        Page<CeoMenuBoardResponse> responses = menuBoards.map(CeoMenuBoardResponse::from);

        return PageResponse.of(responses.toList(), PageInfo.of(responses));
    }

    @Transactional
    public void delete(long id, Long userId) {
        menuBoardService.delete(id, userId);
    }
}
