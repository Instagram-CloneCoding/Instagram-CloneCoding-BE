package project.instagram.exception.customexception;

public class UserNotCorrectException extends RuntimeException{
    public UserNotCorrectException() {
    }

    public UserNotCorrectException(String s) {
        super(s);
    }
}
