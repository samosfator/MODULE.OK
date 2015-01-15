package ua.samosfator.moduleok;

public class SessionIdExpiredException extends RuntimeException {

    @Override
    public String getMessage() {
        return "User sessionId is expired or corrupted. Open LoginFragment to log in one more time.";
    }
}
