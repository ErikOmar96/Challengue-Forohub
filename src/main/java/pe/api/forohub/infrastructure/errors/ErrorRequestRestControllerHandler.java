package pe.api.forohub.infrastructure.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.api.forohub.exceptions.RequestApiException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ErrorRequestRestControllerHandler {

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

    @ExceptionHandler(EntityNotFoundException.class)
    public <T> ResponseEntity<T> entityNotFoundHandler(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorValidationDTO>> validationExceptionHandler(MethodArgumentNotValidException e){
        List<ErrorValidationDTO> errors = e.getFieldErrors().stream().map(ErrorValidationDTO::new).toList();
        return ResponseEntity.badRequest().body(errors);
    }
}
