package fr.sg.fmk.dto;

/**
 * Wrapper renvoyée par l'application aux requêtes notamment aux requêtes AJAX.
 * Peut aussi être ajouté à un objet ModelAndView.
 *
 * @author jntakpe
 * @author cegiraud
 */
public final class ResponseMessage {

    /**
     * Message qui sera affiché à l'écran
     */
    private String message;

    /**
     * Indicateur permettant de savoir si le message doit être affiché dans une alerte d'erreur
     */
    private boolean success;

    /**
     * Objet permettant de passer n'importe quel paramètre additionnel
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
     * Créé un message de succès pour les appels AJAX contenant un message
     *
     * @param message message à afficher
     * @return ResponseMessage contenant le message
     */
    public static ResponseMessage getSuccessMessage(String message) {
        return new ResponseMessage(true, message);
    }

    /**
     * Créé un message de succès pour les appels AJAX contenant des données
     *
     * @param data données à utiliser côté client
     * @return ResponseMessage contenant les données
     */
    public static ResponseMessage getSuccessMessage(Object data) {
        return new ResponseMessage(true, data);
    }

    /**
     * Créé un message de succès pour les appels AJAX contenant un message et des données
     *
     * @param message message à afficher
     * @param data    données à utiliser côté client
     * @return ResponseMessage contenant les données
     */
    public static ResponseMessage getSuccessMessage(String message, Object data) {
        return new ResponseMessage(true, message, data);
    }

    /**
     * Créé un message de succès pour les appels AJAX contenant des données
     *
     * @param data données à utiliser côté client
     * @return ResponseMessage contenant les données
     */
    public static ResponseMessage getErrorMessage(Object data) {
        return new ResponseMessage(false, data);
    }

    /**
     * Créé un message d'erreur pour les appels AJAX contenant un message
     *
     * @param message message à afficher
     * @return ResponseMessage contenant le message
     */
    public static ResponseMessage getErrorMessage(String message) {
        return new ResponseMessage(false, message);
    }

    /**
     * Créé un message de succès pour les appels AJAX contenant un message et des données
     *
     * @param message message à afficher
     * @param data    données à utiliser côté client
     * @return ResponseMessage contenant les données
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
