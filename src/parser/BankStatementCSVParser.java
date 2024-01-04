package parser;

import domain.BankTransaction;
import exception.ParseValidateException;
import notification.Notification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BankStatementCSVParser implements BankStatementParser{

    private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @Override
    public BankTransaction parseFrom(String line) {
        final String[] columns = line.split(";");
        String parseDate = columns[0];
        String parseAmount = columns[1];
        String parseDescription = columns[2];
        if(getValidateErrors(validate(parseDate, parseAmount, parseDescription))){
            throw new ParseValidateException("Date not valid");
        }
        final LocalDate date = LocalDate.parse(parseDate, DATE_PATTERN);
        final double amount = Double.parseDouble(parseAmount);
        return new BankTransaction(date, amount, parseDescription);
    }

    @Override
    public List<BankTransaction> parseLinesFrom(List<String> lines) {
        final List<BankTransaction> bankTransactions = new ArrayList<>();
        for (final String line : lines){
            bankTransactions.add(parseFrom(line));
        }
        return bankTransactions;
    }

    private Notification validate(String date,String amount,String description){
        final Notification notification = new Notification();
        if(description.length() > 100){
            notification.addError("The description is too long");
        }
        final LocalDate parseDate;
        try {
            parseDate = LocalDate.parse(date , DATE_PATTERN);
            if(parseDate.isAfter(LocalDate.now())){
                notification.addError("date cannot be in the future");
            }
        }catch (DateTimeParseException e){
            notification.addError("Invalid format for date");
        }

        try {
            Double.parseDouble(amount);
        }catch (NumberFormatException e){
            notification.addError("Invalid format to amount");
        }
        return notification;
    }

    private boolean getValidateErrors(Notification notification){
        if(!Objects.isNull(notification)){
            if(notification.hasErrors()){
                notification.getErrors().forEach(System.out::println);
                return true;
            }
        }
        return false;
    }
}
