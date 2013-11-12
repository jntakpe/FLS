package fr.sg.fmk.exception;

/**
 * Exception encapsulant les exceptions levées dans le cas d'un appel AJAX
 *
 * @author jntakpe
 */
public class AjaxException extends RuntimeException {

    /**
     * Constructeur d'exceptions levées par les méthodes AJAX
     *
     * @param cause cause de l'exception
     */
    public AjaxException(Throwable cause) {
        super(cause);
    }
}
