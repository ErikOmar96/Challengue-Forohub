package pe.api.forohub.infrastructure.errors;

import pe.api.forohub.exceptions.RequestApiException;

import java.time.LocalDateTime;

public record RequestApiExceptionDTO(
    LocalDateTime timestamp,
    String error,
    String details
) {

    public RequestApiExceptionDTO(RequestApiException e){
        this(e.getTimestamp(), e.getError(), e.getDetails());
    }
}
