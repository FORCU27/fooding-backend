package im.fooding.core.repository.store;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.store.QStorePostComment;
import im.fooding.core.model.store.StorePostComment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static im.fooding.core.model.store.QStorePost.storePost;
import static im.fooding.core.model.store.QStorePostComment.storePostComment;
import static im.fooding.core.model.user.QUser.user;

@RequiredArgsConstructor
public class QStorePostCommentRepositoryImpl implements QStorePostCommentRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<StorePostComment> list(Long storePostId, String searchText, Pageable pageable) {
        QStorePostComment reply = new QStorePostComment("reply");

        // 최상위 댓글 + 대댓글 fetch join
        List<StorePostComment> results = query
                .selectFrom(storePostComment)
                .innerJoin(storePostComment.storePost, storePost).fetchJoin()
                .innerJoin(storePostComment.writer, user).fetchJoin()
                .leftJoin(storePostComment.replies, reply).fetchJoin() // 대댓글 fetch join
                .where(
                        storePost.deleted.isFalse(),
                        storePostComment.deleted.isFalse(),
                        storePostComment.storePost.id.eq(storePostId),
                        storePostComment.parent.id.isNull()
                )
                .orderBy(storePostComment.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct() // fetch join 중복 제거
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(storePostComment.count())
                .from(storePostComment)
                .innerJoin(storePostComment.storePost, storePost).fetchJoin()
                .innerJoin(storePostComment.writer, user).fetchJoin()
                .where(
                        storePost.deleted.isFalse(),
                        storePostComment.deleted.isFalse(),
                        storePostComment.storePost.id.eq(storePostId),
                        storePostComment.parent.id.isNull()
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }
}
