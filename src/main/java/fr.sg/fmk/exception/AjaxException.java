package fr.sg.fmk.exception;

/**
 * Exception encapsulant les exceptions lev�es dans le cas d'un appel AJAX
 *
 * @author jntakpe
 */
public class AjaxException extends RuntimeException {

    /**
     * Constructeur d'exceptions lev�es par les m�thodes AJAX
     *
     * @param cause cause de l'exception
     */
    public AjaxException(Throwable cause) {
        super(cause);
    }
}
