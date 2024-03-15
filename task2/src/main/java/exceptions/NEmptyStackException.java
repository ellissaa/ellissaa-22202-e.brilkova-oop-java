package exceptions;

public class NEmptyStackException extends CommandException {
    public NEmptyStackException() {}

    public NEmptyStackException(String msg) { super(msg); }

    public NEmptyStackException(String msg, Throwable cause) { super(msg, cause); } //можем указать причину исключения
}