package fr.sg.fmk.exception;


/**
 * Exception encapsulant les exceptions métier du framework
 *
 * @author jntakpe
 */
public final class BusinessException extends FmkException {

    /**
     * Constructeur d'exception métier
     *
     * @param message      message de l'exception
     * @param businessCode code d'erreur métier
     */
    public BusinessException(String message, BusinessCode businessCode) {
        super(message, businessCode);
    }

}
