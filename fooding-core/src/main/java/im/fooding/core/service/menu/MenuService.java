package im.fooding.core.service.menu;

import im.fooding.core.model.menu.Menu;
import im.fooding.core.repository.menu.MenuRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MenuService {

    private final MenuRepository menuRepository;

    /**
     * 메뉴 목록 조회
     *
     * @param categoryIds
     * @return List<Menu>
     */
    public List<Menu> list(List<Long> categoryIds) {
        return menuRepository.list(categoryIds);
    }
}
