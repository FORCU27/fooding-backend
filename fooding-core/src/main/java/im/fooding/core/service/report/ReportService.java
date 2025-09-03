package im.fooding.core.service.report;

import im.fooding.core.model.report.Report;
import im.fooding.core.model.report.ReportStatus;
import im.fooding.core.model.report.ReportTargetType;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.report.QReportRepository;
import im.fooding.core.repository.report.QReportRepositoryImpl;
import im.fooding.core.repository.report.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final ReportRepository repository;

    public void create(
            ReportTargetType targetType, long referenceId, String description,
            String memo, User reporter
    ){
        Report report = Report.builder()
                .targetType( targetType )
                .referenceId( referenceId )
                .description( description )
                .memo( memo )
                .reporter( reporter )
                .status( ReportStatus.REPORTED )
                .build();
        repository.save( report );
    }

    public void delete(long id, long deletedBy){
        Report report = repository.findById( id ).orElseThrow();
        report.delete( deletedBy );
    }

    public Page<Report> list(
            String searchString, ReportStatus status, ReportTargetType targetType,
            Long referenceId, Long reporterId, Long chargerId, Pageable pageable
    ){
        return repository.list( pageable, searchString, status, targetType, referenceId, reporterId, chargerId );
    }

    @Transactional
    public void updateStatus(long id, ReportStatus status){
        Report report = repository.findById( id ).orElseThrow();
        report.updateStatus( status );
    }

    @Transactional
    public void updateCharger(long id, User charger){
        Report report = repository.findById( id ).orElseThrow();
        report.updateCharger( charger );
    }

    @Transactional
    public void updateMemo(long id, String memo){
        Report report = repository.findById( id ).orElseThrow();
        report.updateMemo( memo );
    }
}
