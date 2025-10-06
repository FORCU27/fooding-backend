package im.fooding.core.service.menu;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.menu.Menu;
import im.fooding.core.model.menu.MenuImage;
import im.fooding.core.repository.menu.MenuImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MenuImageService {

    private final MenuImageRepository menuImageRepository;

    @Transactional
    public long create(Menu menu, String imageId, String url) {
        if (listByMenuId(menu.getId()).size() >= 3) {
            throw new ApiException(ErrorCode.MENU_IMAGE_COUNT_OVER_LIMIT);
        }

        MenuImage menuImage = new MenuImage(menu, imageId, url);
        menuImageRepository.save(menuImage);

        return menuImage.getId();
    }

    public List<MenuImage> listByMenuId(long menuId) {
        return menuImageRepository.findAllByMenuId(menuId).stream()
                .filter(menuImage -> !menuImage.isDeleted())
                .toList();
    }
}
