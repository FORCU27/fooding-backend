package im.fooding.core.model.bookmark;

import com.querydsl.core.types.OrderSpecifier;

public enum BookmarkSortType {
    RECENT {
        @Override
        public OrderSpecifier<?>[] getOrder(QBookmark bookmark) {
            return new OrderSpecifier[]{
                    bookmark.id.desc(),
            };
        }
    },
    OLD {
        @Override
        public OrderSpecifier<?>[] getOrder(QBookmark bookmark) {
            return new OrderSpecifier[]{
                    bookmark.id.asc(),
            };
        }
    },
    STARRED {
        @Override
        public OrderSpecifier<?>[] getOrder(QBookmark bookmark) {
            return new OrderSpecifier[]{
                    bookmark.isStarred.desc(),
            };
        }
    }
    ;

    public abstract OrderSpecifier<?>[] getOrder(QBookmark bookmark);
}
