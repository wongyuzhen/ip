package jane;

/**
 * Custom exception type for Jane.
 *
 * <p>Represents errors encountered while parsing user input,
 * executing commands, or interacting with storage.</p>
 */
public class JaneException extends Exception {
    public JaneException(String message) {
        super(message);
    }

    public JaneException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
