package im.fooding.core.repository.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.user.Authentication;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static im.fooding.core.model.user.QAuthentication.authentication;

@RequiredArgsConstructor
public class QAuthenticationRepositoryImpl implements QAuthenticationRepository{
    private final JPAQueryFactory query;

    @Override
    public List<Authentication> list(
            String email,
            String phoneNumber,
            int code,
            boolean isSuccess)
    {
        BooleanBuilder condition = new BooleanBuilder();
        if( isSuccess ) condition.and( authentication.isSuccess.isTrue() );
        else condition.and( authentication.isSuccess.isFalse() );
        if( email != null ) condition.and( authentication.email.eq( email ) );
        if( phoneNumber != null ) condition.and( authentication.phoneNumber.eq( phoneNumber ) );
        if( code != 0 ) condition.and( authentication.code.eq( code ) );

        return query.
                select( authentication )
                .from( authentication )
                .where( condition )
                .fetch();
    }
}
