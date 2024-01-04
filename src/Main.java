import parser.BankStatementCSVParser;
import parser.BankStatementParser;
import service.BankStatementAnalyzerService;

public class Main {
    private final static String RESOURCES = "resources/";

    public static void main(String[] args) {
        BankStatementParser csvParser = new BankStatementCSVParser();
        BankStatementAnalyzerService analyzerService = new BankStatementAnalyzerService();
        String fileName = args[0];
        analyzerService.analyze(fileName, csvParser);
    }
}