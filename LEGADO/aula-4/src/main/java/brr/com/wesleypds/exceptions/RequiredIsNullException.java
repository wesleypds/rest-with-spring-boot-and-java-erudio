package brr.com.wesleypds.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredIsNullException extends RuntimeException {
    public RequiredIsNullException(String msg) {
        super(msg);
    }

    public RequiredIsNullException() {
        super("It is not allowed to persist a null object");
    }
}
