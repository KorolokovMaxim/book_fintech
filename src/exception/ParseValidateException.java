package exception;

public class ParseValidateException extends RuntimeException{
    public ParseValidateException(final String errorMessage){
        super(errorMessage);
    }
}
