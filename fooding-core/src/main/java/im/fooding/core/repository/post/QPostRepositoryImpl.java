package im.fooding.core.repository.post;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.post.Post;
import im.fooding.core.model.post.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static im.fooding.core.model.post.QPost.post;

@RequiredArgsConstructor
public class QPostRepositoryImpl implements QPostRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<Post> list(String searchString, Pageable pageable, PostType type) {
        return list(searchString, pageable, type, null);
    }

    @Override
    public Page<Post> list(String searchString, Pageable pageable, PostType type, Boolean isVisibleOnCeo) {
        BooleanExpression visibleCondition = isVisibleOnCeo == null ? null : post.isVisibleOnCeo.eq(isVisibleOnCeo);

        return listInternal(searchString, pageable, type, visibleCondition);
    }

    private BooleanExpression search(String searchString) {
        return StringUtils.hasText(searchString)
                ? post.title.contains(searchString).or(post.content.contains(searchString))
                : null;
    }

    private BooleanExpression searchType(PostType type) {
        return type != null ? post.type.eq(type) : null;
    }

    private Page<Post> listInternal(String searchString, Pageable pageable, PostType type, BooleanExpression visibilityCondition) {
        List<Post> results = query
                .select(post)
                .from(post)
                .where(
                        post.deleted.isFalse(),
                        visibilityCondition,
                        searchType(type),
                        search(searchString)
                )
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Post> countQuery = query
                .select(post)
                .from(post)
                .where(
                        post.deleted.isFalse(),
                        visibilityCondition,
                        searchType(type),
                        search(searchString)
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }
}
