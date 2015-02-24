package ua.samosfator.moduleok;

public class WrongCredentialsException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Login or password is wrong.";
    }
}
