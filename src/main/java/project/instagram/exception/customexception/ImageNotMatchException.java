package project.instagram.exception.customexception;

public class ImageNotMatchException extends RuntimeException{
    public ImageNotMatchException() {
    }

    public ImageNotMatchException(String message) {
        super(message);
    }

    public ImageNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageNotMatchException(Throwable cause) {
        super(cause);
    }

    public ImageNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
