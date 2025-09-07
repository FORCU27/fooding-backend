package im.fooding.core.repository.report;


import im.fooding.core.model.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long>, QReportRepository {
}
