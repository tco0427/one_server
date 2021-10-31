package dgrowth.com.one_server.exception;

import java.nio.file.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> BadRequestException(final RuntimeException runtimeException) {
        log.warn("error", runtimeException);
        return ResponseEntity.badRequest().body(runtimeException.getMessage());
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity handleAccessDeniedException(final AccessDeniedException accessDeniedException) {
        log.warn("error", accessDeniedException);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(accessDeniedException.getMessage());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex) {
        log.info(ex.getClass().getName());
        log.error("error", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
