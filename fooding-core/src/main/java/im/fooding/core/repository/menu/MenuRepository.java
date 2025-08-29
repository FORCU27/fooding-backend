package im.fooding.core.repository.menu;

import im.fooding.core.model.menu.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long>, QMenuRepository {

    Page<Menu> findAllByDeletedFalse(Pageable pageable);

    Page<Menu> findAllByDeletedFalseAndStore_Id(Long storeId, Pageable pageable);
}
