package im.fooding.core.repository.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static im.fooding.core.model.user.QUser.user;

@RequiredArgsConstructor
public class QUserRepositoryImpl implements QUserRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<User> list(String searchString, Pageable pageable) {
        List<User> results = query
                .select(user)
                .from(user)
                .where(
                        user.deleted.isFalse(),
                        search(searchString)
                )
                .orderBy(user.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<User> countQuery = query
                .select(user)
                .from(user)
                .where(
                        user.deleted.isFalse(),
                        search(searchString)
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    private BooleanExpression search(String searchString) {
        return StringUtils.hasText(searchString)
                ? user.nickname.contains(searchString).or(user.email.contains(searchString))
                : null;
    }
}
