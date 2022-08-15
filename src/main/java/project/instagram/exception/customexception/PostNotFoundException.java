package project.instagram.exception.customexception;

public class PostNotFoundException extends NullPointerException{
    public PostNotFoundException() {
    }

    public PostNotFoundException(String s) {
        super(s);
    }
}
