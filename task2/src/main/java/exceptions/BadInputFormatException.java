package exceptions;

public class BadInputFormatException extends CommandException {
    public BadInputFormatException() {}

    public BadInputFormatException(String msg) { super(msg); }

    public BadInputFormatException(String msg, Throwable cause) { super(msg, cause); } //можем указать причину исключения
}
