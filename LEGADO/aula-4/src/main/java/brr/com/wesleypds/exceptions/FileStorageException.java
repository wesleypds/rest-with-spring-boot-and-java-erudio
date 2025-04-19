package brr.com.wesleypds.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class FileStorageException extends RuntimeException {
    public FileStorageException(String msg) {
        super(msg);
    }
    public FileStorageException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
