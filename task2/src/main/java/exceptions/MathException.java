package exceptions;

public class MathException extends CommandException {
    public MathException() {}

    public MathException(String msg) { super(msg); }

    public MathException(String msg, Throwable cause) { super(msg, cause); } //можем указать причину исключения
}
