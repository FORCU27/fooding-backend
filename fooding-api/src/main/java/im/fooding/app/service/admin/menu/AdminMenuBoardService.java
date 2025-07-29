package im.fooding.app.service.admin.menu;

import im.fooding.app.dto.request.admin.menu.AdminMenuBoardCreateRequest;
import im.fooding.app.dto.request.admin.menu.AdminMenuBoardListRequest;
import im.fooding.app.dto.request.admin.menu.AdminMenuBoardUpdateRequest;
import im.fooding.app.dto.response.admin.menu.AdminMenuBoardResponse;
import im.fooding.app.service.file.FileUploadService;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.file.File;
import im.fooding.core.model.menu.MenuBoard;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.menu.MenuBoardService;
import im.fooding.core.service.store.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AdminMenuBoardService {

    private final StoreService storeService;
    private final FileUploadService fileUploadService;
    private final MenuBoardService menuBoardService;

    @Transactional
    public void create(AdminMenuBoardCreateRequest request) {
        Store store = storeService.findById(request.getStoreId());
        String imageUrl = null;
        if (StringUtils.hasText(request.getImageId())) {
            File file = fileUploadService.commit(request.getImageId());
            imageUrl = file.getUrl();
        }

        menuBoardService.create(request.toMenuBoardCreateRequest(store, imageUrl));
    }

    public AdminMenuBoardResponse get(long id) {
        return AdminMenuBoardResponse.from(menuBoardService.get(id));
    }

    public PageResponse<AdminMenuBoardResponse> list(@Valid AdminMenuBoardListRequest request) {
        Page<MenuBoard> menuBoards = menuBoardService.list(request.getPageable());
        return PageResponse.of(
                menuBoards.stream().map(AdminMenuBoardResponse::from).toList(),
                PageInfo.of(menuBoards)
        );
    }

    @Transactional
    public void update(long id, AdminMenuBoardUpdateRequest request) {
        Store store = storeService.findById(request.getStoreId());
        String imageUrl = null;
        if (StringUtils.hasText(request.getImageId())) {
            File file = fileUploadService.commit(request.getImageId());
            imageUrl = file.getUrl();
        }

        menuBoardService.update(request.toMenuBoardUpdateRequest(id, store, imageUrl));
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        menuBoardService.delete(deletedBy, id);
    }
}
