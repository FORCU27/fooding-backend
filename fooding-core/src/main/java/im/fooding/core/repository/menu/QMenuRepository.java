package im.fooding.core.repository.menu;

import im.fooding.core.model.menu.Menu;
import im.fooding.core.model.menu.MenuCategory;
import java.util.List;

public interface QMenuRepository {

    List<Menu> list(MenuCategory menuCategory);

}
