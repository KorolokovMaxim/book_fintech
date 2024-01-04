package service;

import domain.BankTransaction;
import parser.BankStatementParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.List;

import static java.lang.StringTemplate.STR;

public class BankStatementAnalyzerService {
    private final static String RESOURCES = "resources/";

    public void analyze(final String fileName, final BankStatementParser bankStatementParser) {
        try {
            final Path path = Paths.get(RESOURCES + fileName);
            final List<String> lines = Files.readAllLines(path);
            final List<BankTransaction> bankTransactions = bankStatementParser.parseLinesFrom(lines);
            final BankStatementService bankStatementService = new BankStatementService(bankTransactions);
            final List<BankTransaction> transactions = bankStatementService.findTransactions(
                    bk -> bk.date().getMonth().equals(Month.FEBRUARY)
                            && bk.amount() >= 1_000
            );
            transactions.forEach(System.out::println);
            collectSummary(bankStatementService);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void collectSummary(final BankStatementService bankStatementService) {
        System.out.println(STR."The total for all transaction is \{bankStatementService.calculationTotalAmount()}");
        System.out.println(STR."Transaction in january \{bankStatementService.calculationTotalInMonth(Month.JANUARY)}");
        System.out.println(STR."Transaction in February \{bankStatementService.calculationTotalInMonth(Month.FEBRUARY)}");
        System.out.println(STR."Transaction total salary recevoed is \{bankStatementService.calculationTotalForCategory("Salary")}");
        System.out.println(STR."Transaction in january max \{bankStatementService.maxAmountFromMonth(Month.JANUARY)}");
        System.out.println(STR."Transaction in january min  \{bankStatementService.minAmountFromMonth(Month.JANUARY)}");
    }
}
