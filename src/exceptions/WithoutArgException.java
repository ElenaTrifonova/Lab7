package exceptions;

public class WithoutArgException extends RuntimeException{

    @Override
    public String getMessage(){
        return "Command is expected with argument.";
    }
}
