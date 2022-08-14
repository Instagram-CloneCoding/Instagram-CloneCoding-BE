package project.instagram.exception.customexception;

public class ImageNotExistException extends NullPointerException{
    public ImageNotExistException() {
    }
    public ImageNotExistException(String s) {
        super(s);
    }
}
