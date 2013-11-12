package fr.sg.fmk.exception;

/**
 * Exceptions gérées par le framework.
 *
 * @author jntakpe
 */
public abstract class FmkException extends RuntimeException {

    /**
     * Code erreur du framework
     */
    private final ErrorCode errorCode;


    /**
     * Constructeur permettant la création d'une exception gérée par le framework
     *
     * @param message   message de l'exception
     * @param errorCode code d'erreur
     */
    protected FmkException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructeur permettant la création d'un exception gérée par le framework en gardant la cause ayant provoqué la
     * levée de l'exception
     *
     * @param e         exception d'origine
     * @param message   message de l'exception
     * @param errorCode code d'erreur
     */
    protected FmkException(Exception e, String message, ErrorCode errorCode) {
        super(message, e.getCause());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
