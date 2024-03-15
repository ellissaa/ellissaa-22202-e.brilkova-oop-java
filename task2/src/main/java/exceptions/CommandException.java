package exceptions;

public class CommandException extends Exception {
    public CommandException() {}

    public CommandException(String msg) { super(msg); }

    public CommandException(String msg, Throwable cause) { super(msg, cause); } //можем указать причину исключения
}