package exceptions;

public class InputIntervalException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Error of entering";
    }
}
