package exceptions;

public class StackIsLimitedException extends RuntimeException{

    @Override
    public String getMessage(){
        return "Too much scripts, stack is limited.";
    }
}
