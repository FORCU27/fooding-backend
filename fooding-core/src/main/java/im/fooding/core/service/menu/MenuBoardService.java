package im.fooding.core.service.menu;

import im.fooding.core.dto.request.menu.MenuBoardCreateRequest;
import im.fooding.core.dto.request.menu.MenuBoardUpdateRequest;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.menu.MenuBoard;
import im.fooding.core.repository.menu.MenuBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuBoardService {

    private final MenuBoardRepository menuBoardRepository;

    @Transactional
    public void create(MenuBoardCreateRequest request) {
        MenuBoard menuBoard = MenuBoard.builder()
                .store(request.getStore())
                .title(request.getTitle())
                .imageUrl(request.getImageUrl())
                .build();

        menuBoardRepository.save(menuBoard);
    }

    public MenuBoard get(Long id) {
        return menuBoardRepository.findById(id)
                .filter(menuBoard -> !menuBoard.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.MENU_BOARD_NOT_FOUND));
    }

    public Page<MenuBoard> list(Pageable pageable) {
        return menuBoardRepository.findAllByDeletedFalse(pageable);
    }

    @Transactional
    public void update(MenuBoardUpdateRequest request) {
        MenuBoard menuBoard = menuBoardRepository.findById(request.getId())
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.MENU_BOARD_NOT_FOUND));

        menuBoard.updateStore(request.getStore());
        menuBoard.updateTitle(request.getTitle());
        menuBoard.updateImageUrl(request.getImageUrl());
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        MenuBoard menuBoard = get(id);

        menuBoard.delete(deletedBy);
    }
}
