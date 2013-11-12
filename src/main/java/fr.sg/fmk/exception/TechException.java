package fr.sg.fmk.exception;

/**
 * Exception encapsulant les exceptions techniques du framework (accès DB ou accès fichier ...)
 *
 * @author jntakpe
 */
public final class TechException extends FmkException {

    /**
     * Constructeur d'exception techniques
     *
     * @param cause    cause de l'exception
     * @param message  message de l'exception
     * @param techCode code d'erreur technique
     */
    public TechException(Exception cause, String message, TechCode techCode) {
        super(cause, message, techCode);
    }

}
