package im.fooding.core.model.pointshop;

import com.querydsl.core.types.OrderSpecifier;

public enum PointShopSortType {
    RECENT {
        @Override
        public OrderSpecifier<?>[] getOrder(QPointShop pointShop) {
            return new OrderSpecifier[]{
                    pointShop.id.desc(),
            };
        }
    },
    OLD {
        @Override
        public OrderSpecifier<?>[] getOrder(QPointShop pointShop) {
            return new OrderSpecifier[]{
                    pointShop.id.asc(),
            };
        }
    };

    public abstract OrderSpecifier<?>[] getOrder(QPointShop pointShop);
}
