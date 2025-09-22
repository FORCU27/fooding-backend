package im.fooding.core.model.store;

import com.querydsl.core.types.OrderSpecifier;

public enum StoreImageSortType {
    RECENT {
        @Override
        public OrderSpecifier<?>[] getOrder(QStoreImage storeImage) {
            return new OrderSpecifier[]{
                    storeImage.isMain.desc(),  // 1. 대표 이미지 먼저
                    storeImage.id.desc(),      // 2. 최신순
                    storeImage.sortOrder.asc() // 3. sortOrder 오름차순
            };
        }
    },
    OLD {
        @Override
        public OrderSpecifier<?>[] getOrder(QStoreImage storeImage) {
            return new OrderSpecifier[]{
                    storeImage.isMain.desc(),  // 1. 대표 이미지 먼저
                    storeImage.id.asc(),       // 2. 오래된순
                    storeImage.sortOrder.asc() // 3. sortOrder 오름차순
            };
        }
    };

    public abstract OrderSpecifier<?>[] getOrder(QStoreImage storeImage);
}
