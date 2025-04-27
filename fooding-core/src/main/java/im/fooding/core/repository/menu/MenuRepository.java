package im.fooding.core.repository.menu;

import im.fooding.core.model.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long>, QMenuRepository {

}
