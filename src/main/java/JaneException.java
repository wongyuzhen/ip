public class JaneException extends Exception {
    public JaneException(String message) {
        super(message);
    }

    public JaneException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
