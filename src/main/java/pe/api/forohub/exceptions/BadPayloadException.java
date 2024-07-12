package pe.api.forohub.exceptions;


public class BadPayloadException extends RequestApiException {

    public BadPayloadException(String message) {
        super( "bad payload", new RuntimeException(message), message);
    }
}
