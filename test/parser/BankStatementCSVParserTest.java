package parser;

import domain.BankTransaction;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

public class BankStatementCSVParserTest {

    private final BankStatementParser statementParser = new BankStatementCSVParser();

    @Test
    public void shouldParseOneCorrectLine() {
        final String line = "30-01-2017;-50;Tesco";
        final BankTransaction result = statementParser.parseFrom(line);
        final BankTransaction expected = new BankTransaction(LocalDate.of(2017, Month.JANUARY,30),
                -50, "Tesco");
        final double tolerance = 0.0d;

        Assert.assertEquals(expected.date(), result.date());
        Assert.assertEquals(expected.amount(), result.amount() , tolerance);
        Assert.assertEquals(expected.description(), result.description());
    }

}
