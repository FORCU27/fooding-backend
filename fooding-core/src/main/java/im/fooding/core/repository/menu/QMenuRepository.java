package im.fooding.core.repository.menu;

import im.fooding.core.model.menu.Menu;
import java.util.List;

public interface QMenuRepository {

    List<Menu> list(List<Long> categoryIds);

}
