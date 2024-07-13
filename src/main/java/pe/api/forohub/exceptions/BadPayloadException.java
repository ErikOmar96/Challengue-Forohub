package pe.api.forohub.exceptions;


public class BadPayloadException extends RequestApiException {

    public BadPayloadException(String message) {
        super( "Bad payload", new RuntimeException(message), message);
    }
}
