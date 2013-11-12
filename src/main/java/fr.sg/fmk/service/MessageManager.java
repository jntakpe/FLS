package fr.sg.fmk.service;

import fr.sg.fmk.constant.LogLevel;
import fr.sg.fmk.exception.ErrorCode;

/**
 * Récupération des messages via des différents messages bundles
 *
 * @author cegiraud
 * @author jntakpe
 */
public interface MessageManager {

    /**
     * Récupère un message d'erreur dans les bundles de l'application et remplace les variables
     *
     * @param codeMessage code du message
     * @param args        paramètres du message
     * @return le message completé
     */
    String getMessage(String codeMessage, Object... args);

    /**
     * Récupère un message d'erreur dans les bundles de l'application et remplace les variables
     *
     * @param errorCode code du message correspondant à un code erreur
     * @param args      paramètres du message
     * @return le message completé
     */
    String getMessage(ErrorCode errorCode, Object... args);

    /**
     * Log un message d'erreur récupéré dans un des bundles de l'application
     *
     * @param codeMessage code du message
     * @param logLevel    niveau de log
     * @param args        paramètres du message
     * @return le message completé
     */
    String logMessage(String codeMessage, LogLevel logLevel, Object... args);

    /**
     * Log un message d'erreur récupéré dans un des bundles de l'application
     *
     * @param errorCode code du message correspondant à un code erreur
     * @param logLevel  niveau de log
     * @param args      paramètres du message
     * @return le message completé
     */
    String logMessage(ErrorCode errorCode, LogLevel logLevel, Object... args);
}
