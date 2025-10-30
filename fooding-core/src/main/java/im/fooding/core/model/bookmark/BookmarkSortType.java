package im.fooding.core.model.bookmark;

import com.querydsl.core.types.OrderSpecifier;

public enum BookmarkSortType {
    RECENT {
        @Override
        public OrderSpecifier<?>[] getOrder(QBookmark bookmark) {
            return new OrderSpecifier[]{
                    bookmark.isStarred.desc(),
                    bookmark.id.desc()
            };
        }
    },
    OLD {
        @Override
        public OrderSpecifier<?>[] getOrder(QBookmark bookmark) {
            return new OrderSpecifier[]{
                    bookmark.isStarred.desc(),
                    bookmark.id.asc()
            };
        }
    };

    public abstract OrderSpecifier<?>[] getOrder(QBookmark bookmark);
}
