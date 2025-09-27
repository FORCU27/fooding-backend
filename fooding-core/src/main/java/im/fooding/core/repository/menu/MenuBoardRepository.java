package im.fooding.core.repository.menu;

import im.fooding.core.model.menu.MenuBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuBoardRepository extends JpaRepository<MenuBoard, Long>, QMenuBoardRepository {

    Page<MenuBoard> findAllByDeletedFalse(Pageable pageable);
}
