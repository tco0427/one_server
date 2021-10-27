package dgrowth.com.one_server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CoreException extends RuntimeException {

    private final String message;

    public CoreException(String message) {
        this.message = message;
    }
}
