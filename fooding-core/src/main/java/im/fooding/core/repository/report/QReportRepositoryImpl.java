package im.fooding.core.repository.report;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.report.Report;
import im.fooding.core.model.report.ReportStatus;
import im.fooding.core.model.report.ReportTargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static im.fooding.core.model.report.QReport.report;

@RequiredArgsConstructor
public class QReportRepositoryImpl implements QReportRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<Report> list(
            Pageable pageable,
            String searchString,
            ReportStatus status,
            ReportTargetType targetType,
            Long referenceId,
            Long reporterId,
            Long chargerId
    ){
        BooleanBuilder condition = new BooleanBuilder();
        condition.and( report.deleted.isFalse() );

        if( status != null ) condition.and( report.status.eq( status ) );
        if( chargerId != null ) condition.and( report.charger.id.eq( chargerId ) );
        if( reporterId != null ) condition.and( report.reporter.id.eq( reporterId ) );
        if( referenceId != null && targetType != null ) condition.and( report.referenceId.eq( referenceId ) );
        if( targetType != null ) condition.and( report.targetType.eq( targetType ) );

        List<Report> result = query
                .select( report )
                .from( report )
                .where( condition )
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
        JPAQuery<Long> countQuery = query
                .select( report.count() )
                .from( report )
                .where( condition );
        return PageableExecutionUtils.getPage(
                result, pageable, countQuery::fetchCount
        );
    }
}
