package pe.api.forohub.exceptions;


public class BadQueryParamValueException extends RequestApiException {

    public BadQueryParamValueException(String message, RuntimeException runtimeException) {
        super("Bad query param value", runtimeException, message);
    }
}
