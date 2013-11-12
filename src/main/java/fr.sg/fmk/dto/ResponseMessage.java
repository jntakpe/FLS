package fr.sg.fmk.dto;

/**
 * Wrapper renvoy�e par l'application aux requ�tes notamment aux requ�tes AJAX.
 * Peut aussi �tre ajout� � un objet ModelAndView.
 *
 * @author jntakpe
 * @author cegiraud
 */
public final class ResponseMessage {

    /**
     * Message qui sera affich� � l'�cran
     */
    private String message;

    /**
     * Indicateur permettant de savoir si le message doit �tre affich� dans une alerte d'erreur
     */
    private boolean success;

    /**
     * Objet permettant de passer n'importe quel param�tre additionnel
     */
    private Object data;


    private ResponseMessage(boolean success, String message) {
        this.message = message;
        this.success = success;
    }

    private ResponseMessage(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    private ResponseMessage(boolean success, String message, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    /**
     * Cr�� un message de succ�s pour les appels AJAX contenant un message
     *
     * @param message message � afficher
     * @return ResponseMessage contenant le message
     */
    public static ResponseMessage getSuccessMessage(String message) {
        return new ResponseMessage(true, message);
    }

    /**
     * Cr�� un message de succ�s pour les appels AJAX contenant des donn�es
     *
     * @param data donn�es � utiliser c�t� client
     * @return ResponseMessage contenant les donn�es
     */
    public static ResponseMessage getSuccessMessage(Object data) {
        return new ResponseMessage(true, data);
    }

    /**
     * Cr�� un message de succ�s pour les appels AJAX contenant un message et des donn�es
     *
     * @param message message � afficher
     * @param data    donn�es � utiliser c�t� client
     * @return ResponseMessage contenant les donn�es
     */
    public static ResponseMessage getSuccessMessage(String message, Object data) {
        return new ResponseMessage(true, message, data);
    }

    /**
     * Cr�� un message de succ�s pour les appels AJAX contenant des donn�es
     *
     * @param data donn�es � utiliser c�t� client
     * @return ResponseMessage contenant les donn�es
     */
    public static ResponseMessage getErrorMessage(Object data) {
        return new ResponseMessage(false, data);
    }

    /**
     * Cr�� un message d'erreur pour les appels AJAX contenant un message
     *
     * @param message message � afficher
     * @return ResponseMessage contenant le message
     */
    public static ResponseMessage getErrorMessage(String message) {
        return new ResponseMessage(false, message);
    }

    /**
     * Cr�� un message de succ�s pour les appels AJAX contenant un message et des donn�es
     *
     * @param message message � afficher
     * @param data    donn�es � utiliser c�t� client
     * @return ResponseMessage contenant les donn�es
     */
    public static ResponseMessage getErrorMessage(String message, Object data) {
        return new ResponseMessage(false, message, data);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
