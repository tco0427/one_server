package dgrowth.com.one_server.base.exception.controller;

import dgrowth.com.one_server.base.exception.BaseException;
import dgrowth.com.one_server.base.exception.response.ErrorResponse;
import java.nio.file.AccessDeniedException;
import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> BadRequestException(final BaseException e) {
        log.warn("error", e);
        return ResponseEntity.status(e.getHttpStatus()).body(
            new ErrorResponse(LocalTime.now(), e.getMessage(), e.getHttpStatus().value(),
                e.getHttpStatus().name()
            ));
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
