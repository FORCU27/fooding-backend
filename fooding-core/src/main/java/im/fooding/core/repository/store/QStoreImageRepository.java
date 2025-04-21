package im.fooding.core.repository.store;

import im.fooding.core.model.store.StoreImage;
import java.util.Optional;

public interface QStoreImageRepository {

    Optional<StoreImage> get(Long storeId);
}
