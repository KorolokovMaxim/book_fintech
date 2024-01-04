package export;

import domain.SummaryStatistics;

public class HtmlExporter implements Exporter {
    @Override
    public String export(SummaryStatistics summaryStatistics) {
        return "<!doctype html><html lang='en'>" +
                "<head><title>Bank Transaction Reports</title></head>" +
                STR."<body><ul><li><strong>The sum is</strong>: \{summaryStatistics.sum()}</li>" +
                STR."<li><strong>The average is</strong>: \{summaryStatistics.average()}</li>" +
                STR."<li><strong>The max is</strong>: \{summaryStatistics.max()}</li>" +
                STR."<li><strong>The min is</strong>: \{summaryStatistics.min()}</li>" +
                " </ul></body></html>";

    }
}
