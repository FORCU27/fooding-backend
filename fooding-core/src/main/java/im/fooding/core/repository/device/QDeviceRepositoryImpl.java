package im.fooding.core.repository.device;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.device.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static im.fooding.core.model.device.QDevice.device;

@RequiredArgsConstructor
public class QDeviceRepositoryImpl implements QDeviceRepository{
    private final JPAQueryFactory query;
    @Override
    public Page<Device> list(String searchString, Pageable pageable, Long storeId) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and( device.deleted.isFalse() );
        if( search( searchString ) != null ) condition.and( search( searchString ) );
        if( storeId != null ) condition.and( device.store.id.eq( storeId ) );

        List<Device> results = query
                .select(device)
                .from(device)
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Device> countQuery = query
                .select(device)
                .from(device)
                .where(condition);
        return PageableExecutionUtils.getPage( results, pageable, countQuery::fetchCount );
    }

    private BooleanExpression search(String searchString){
        return StringUtils.hasText(searchString)
                ? device.name.contains(searchString) : null;
    }
}
