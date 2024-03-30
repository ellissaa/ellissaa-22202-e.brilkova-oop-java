package exceptions;

public class NoSuchCommandException extends Exception {
    public NoSuchCommandException() {}

    public NoSuchCommandException(String msg) { super(msg); }

    public NoSuchCommandException(String msg, Throwable cause) { super(msg, cause); }

}