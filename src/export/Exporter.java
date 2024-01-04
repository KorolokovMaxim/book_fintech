package export;

import domain.SummaryStatistics;

public interface Exporter {
    String export(SummaryStatistics summaryStatistics);
}
