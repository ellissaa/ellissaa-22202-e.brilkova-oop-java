package exceptions;

public class CreateCommandException extends Exception {
    public CreateCommandException() {}

    public CreateCommandException(String msg) { super(msg); }

    public CreateCommandException(String msg, Throwable cause) { super(msg, cause); }

}