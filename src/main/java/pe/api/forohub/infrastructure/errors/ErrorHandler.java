package pe.api.forohub.infrastructure.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.api.forohub.exceptions.RequestApiException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(RequestApiException.class)
    public ResponseEntity<RequestApiExceptionDTO> badPayloadException(RequestApiException e ) {
        return ResponseEntity.
            badRequest().
            body(new RequestApiExceptionDTO(e));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<RequestApiExceptionDTO> integrityConstraintViolationHandler(SQLIntegrityConstraintViolationException e){
        return ResponseEntity.badRequest().body(new RequestApiExceptionDTO(
            LocalDateTime.now(),
            "bad request",
            e.getMessage()
        ));
    }

}
