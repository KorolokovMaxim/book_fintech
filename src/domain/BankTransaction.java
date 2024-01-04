package domain;

import notification.Notification;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import static java.lang.StringTemplate.STR;

public record BankTransaction(LocalDate date, double amount, String description) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankTransaction that = (BankTransaction) o;
        return Double.compare(amount, that.amount) == 0 && Objects.equals(date, that.date) && Objects.equals(description, that.description);
    }

    @Override
    public String toString() {
        return STR."BankTransaction \n date \{date}, \n amount \{amount}, \n description \{description} \n\n";
    }
}
