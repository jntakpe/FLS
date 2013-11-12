package fr.sg.fmk.exception;

/**
 * Exceptions g�r�es par le framework.
 *
 * @author jntakpe
 */
public abstract class FmkException extends RuntimeException {

    /**
     * Code erreur du framework
     */
    private final ErrorCode errorCode;


    /**
     * Constructeur permettant la cr�ation d'une exception g�r�e par le framework
     *
     * @param message   message de l'exception
     * @param errorCode code d'erreur
     */
    protected FmkException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructeur permettant la cr�ation d'un exception g�r�e par le framework en gardant la cause ayant provoqu� la
     * lev�e de l'exception
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
