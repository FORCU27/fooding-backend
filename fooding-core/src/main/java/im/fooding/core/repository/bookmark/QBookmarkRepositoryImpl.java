package im.fooding.core.repository.bookmark;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.bookmark.Bookmark;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static im.fooding.core.model.bookmark.QBookmark.bookmark;
import static im.fooding.core.model.region.QRegion.region;
import static im.fooding.core.model.store.QStore.store;
import static im.fooding.core.model.store.QStoreImage.storeImage;
import static im.fooding.core.model.user.QUser.user;

@RequiredArgsConstructor
public class QBookmarkRepositoryImpl implements QBookmarkRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<Bookmark> list(Long storeId, Long userId, String searchString, Pageable pageable) {
        List<Bookmark> results = query
                .select(bookmark)
                .from(bookmark)
                .innerJoin(bookmark.store, store).fetchJoin()
                .innerJoin(bookmark.user, user).fetchJoin()
                .leftJoin(store.region, region).fetchJoin()
                .leftJoin(store.images, storeImage).fetchJoin()
                .where(
                        bookmark.deleted.isFalse(),
                        store.deleted.isFalse(),
                        user.deleted.isFalse(),
                        searchStore(storeId),
                        searchUser(userId),
                        searchString(searchString)
                )
                .orderBy(bookmark.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Bookmark> countQuery = query
                .select(bookmark)
                .from(bookmark)
                .where(
                        bookmark.deleted.isFalse(),
                        store.deleted.isFalse(),
                        user.deleted.isFalse(),
                        searchStore(storeId),
                        searchUser(userId),
                        searchString(searchString)
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    private BooleanExpression searchStore(Long storeId) {
        return null != storeId ? store.id.eq(storeId) : null;
    }

    private BooleanExpression searchUser(Long userId) {
        return null != userId ? user.id.eq(userId) : null;
    }

    private BooleanExpression searchString(String searchString) {
        if (!StringUtils.hasText(searchString)) {
            return null;
        }
        return user.nickname.contains(searchString).or(store.name.contains(searchString));
    }
}
