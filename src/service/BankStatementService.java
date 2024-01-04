package service;

import domain.BankTransaction;
import filter.BankTransactionFilter;
import filter.BankTransactionSummarizer;

import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class BankStatementService {
    private final List<BankTransaction> bankTransactions;

    public BankStatementService(List<BankTransaction> bankTransactionList) {
        this.bankTransactions = bankTransactionList;
    }

    public double calculationTotalAmount() {
        return bankTransactions.stream()
                .map(BankTransaction::amount)
                .reduce(0d, Double::sum);
    }

    public double calculationTotalInMonth(final Month month) {
        return summarizeTransactions(((acc, bk) ->
                bk.date().getMonth().equals(month) ? acc + bk.amount() : acc));
    }

    public double minAmountFromMonth(final Month month) {
        return bankTransactions.stream()
                .filter(e -> e.date().getMonth().equals(month))
                .map(BankTransaction::amount)
                .min(Comparator.naturalOrder()).orElseThrow(() -> new NoSuchElementException("Not value present"));
    }

    public double maxAmountFromMonth(final Month month) {
        return bankTransactions.stream()
                .filter(e -> e.date().getMonth().equals(month))
                .map(BankTransaction::amount)
                .max(Comparator.naturalOrder()).orElseThrow(() -> new NoSuchElementException("Not value present"));
    }


    public double calculationTotalForCategory(final String category) {
        return summarizeTransactions(((acc, bk) ->
                bk.description().equals(category) ? acc + bk.amount() : acc));
    }

    public double summarizeTransactions(final BankTransactionSummarizer summarizer) {
        double result = 0d;
        for (var bk : bankTransactions) {
            result = summarizer.summarize(result, bk);
        }
        return result;
    }


    public List<BankTransaction> findTransactions(final BankTransactionFilter bankTransactionFilter) {
        final List<BankTransaction> result = new ArrayList<>();
        for (var bt : bankTransactions) {
            if (bankTransactionFilter.test(bt)) {
                result.add(bt);
            }
        }
        return result;
    }
}
