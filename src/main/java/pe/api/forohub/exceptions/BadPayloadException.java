package pe.api.forohub.exceptions;

import java.time.LocalDateTime;

public class BadPayloadException extends RequestApiException {

    public BadPayloadException(String message) {
        super(LocalDateTime.now(), "bad payload", new RuntimeException(message), message);
    }
}
