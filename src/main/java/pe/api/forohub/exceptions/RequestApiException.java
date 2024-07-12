package pe.api.forohub.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class RequestApiException extends RuntimeException{
    private LocalDateTime timestamp;
    private String error;
    private Exception internalException;
    private String details;

    public RequestApiException(String error, Exception internalException, String details) {
        this.timestamp = LocalDateTime.now();
        this.error = error;
        this.internalException = internalException;
        this.details = details;
    }
}
