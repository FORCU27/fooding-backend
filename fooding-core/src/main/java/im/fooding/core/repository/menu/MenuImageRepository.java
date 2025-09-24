package im.fooding.core.repository.menu;

import im.fooding.core.model.menu.MenuImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuImageRepository extends JpaRepository<MenuImage, Long> {

    List<MenuImage> findAllByMenuId(long menuId);
}
