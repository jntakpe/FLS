package fr.sg.fmk.service;

import fr.sg.fmk.constant.LogLevel;
import fr.sg.fmk.exception.ErrorCode;

/**
 * R�cup�ration des messages via des diff�rents messages bundles
 *
 * @author cegiraud
 * @author jntakpe
 */
public interface MessageManager {

    /**
     * R�cup�re un message d'erreur dans les bundles de l'application et remplace les variables
     *
     * @param codeMessage code du message
     * @param args        param�tres du message
     * @return le message complet�
     */
    String getMessage(String codeMessage, Object... args);

    /**
     * R�cup�re un message d'erreur dans les bundles de l'application et remplace les variables
     *
     * @param errorCode code du message correspondant � un code erreur
     * @param args      param�tres du message
     * @return le message complet�
     */
    String getMessage(ErrorCode errorCode, Object... args);

    /**
     * Log un message d'erreur r�cup�r� dans un des bundles de l'application
     *
     * @param codeMessage code du message
     * @param logLevel    niveau de log
     * @param args        param�tres du message
     * @return le message complet�
     */
    String logMessage(String codeMessage, LogLevel logLevel, Object... args);

    /**
     * Log un message d'erreur r�cup�r� dans un des bundles de l'application
     *
     * @param errorCode code du message correspondant � un code erreur
     * @param logLevel  niveau de log
     * @param args      param�tres du message
     * @return le message complet�
     */
    String logMessage(ErrorCode errorCode, LogLevel logLevel, Object... args);
}
