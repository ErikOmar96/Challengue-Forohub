package pe.api.forohub.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestApiException extends RuntimeException{
    private LocalDateTime timestamp;
    private String error;
    private Exception internalException;
    private String details;
}
