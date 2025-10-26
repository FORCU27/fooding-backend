package im.fooding.core.repository.device;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.device.Device;
import im.fooding.core.model.device.QDevice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QDeviceRepositoryImpl implements QDeviceRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Device> list(String searchString, Long userId, Pageable pageable) {
        QDevice device = QDevice.device;

        BooleanExpression whereClause = device.deleted.isFalse();

        if (userId != null) {
            whereClause = whereClause.and(device.user.id.eq(userId));
        }
        
        if (StringUtils.hasText(searchString)) {
            whereClause = whereClause.and(
                device.name.containsIgnoreCase(searchString)
                .or(device.uuid.containsIgnoreCase(searchString))
            );
        }

        List<Device> devices = queryFactory
            .selectFrom(device)
            .where(whereClause)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(device.createdAt.desc())
            .fetch();

        Long total = queryFactory
            .select(device.count())
            .from(device)
            .where(whereClause)
            .fetchOne();

        return new PageImpl<>(devices, pageable, total != null ? total : 0L);
    }
}
