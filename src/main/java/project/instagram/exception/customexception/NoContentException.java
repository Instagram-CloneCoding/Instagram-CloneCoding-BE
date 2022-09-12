package project.instagram.exception.customexception;

public class NoContentException extends NullPointerException{
    public NoContentException() {
    }

    public NoContentException(String s) {
        super(s);
    }
}
