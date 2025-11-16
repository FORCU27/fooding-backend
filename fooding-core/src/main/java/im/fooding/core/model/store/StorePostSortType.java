package im.fooding.core.model.store;

import com.querydsl.core.types.OrderSpecifier;

public enum StorePostSortType {
    RECENT {
        @Override
        public OrderSpecifier<?>[] getOrder(QStorePost storePost) {
            return new OrderSpecifier[]{
                    storePost.isFixed.desc(),
                    storePost.isNotice.desc(),
                    storePost.id.desc(),
            };
        }
    },
    OLD {
        @Override
        public OrderSpecifier<?>[] getOrder(QStorePost storePost) {
            return new OrderSpecifier[]{
                    storePost.isFixed.desc(),
                    storePost.isNotice.desc(),
                    storePost.id.asc(),
            };
        }
    };

    public abstract OrderSpecifier<?>[] getOrder(QStorePost storePost);
}
