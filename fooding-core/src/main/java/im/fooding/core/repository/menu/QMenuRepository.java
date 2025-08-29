package im.fooding.core.repository.menu;

import im.fooding.core.model.menu.Menu;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QMenuRepository {

    List<Menu> list(List<Long> categoryIds);

    Page<Menu> list(Long storeId, String searchString, Pageable pageable);

}
