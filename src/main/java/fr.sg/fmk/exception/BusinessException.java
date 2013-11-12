package fr.sg.fmk.exception;


/**
 * Exception encapsulant les exceptions m�tier du framework
 *
 * @author jntakpe
 */
public final class BusinessException extends FmkException {

    /**
     * Constructeur d'exception m�tier
     *
     * @param message      message de l'exception
     * @param businessCode code d'erreur m�tier
     */
    public BusinessException(String message, BusinessCode businessCode) {
        super(message, businessCode);
    }

}
