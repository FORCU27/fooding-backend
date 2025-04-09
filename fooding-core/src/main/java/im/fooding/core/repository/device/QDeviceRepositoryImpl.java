package im.fooding.core.repository.device;

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
    public Page<Device> list(String searchString, Pageable pageable, long storeId) {
        List<Device> results = query
                .select(device)
                .from(device)
                .where(
                        device.deleted.isFalse(),
                        device.store.id.eq( storeId ),
                        search(searchString)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Device> countQuery = query
                .select(device)
                .from(device)
                .where(
                        device.deleted.isFalse(),
                        search(searchString)
                );
        return PageableExecutionUtils.getPage( results, pageable, countQuery::fetchCount );
    }

    private BooleanExpression search(String searchString){
        return StringUtils.hasText(searchString)
                ? device.name.contains(searchString) : null;
    }
}
