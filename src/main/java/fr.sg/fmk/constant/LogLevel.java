package fr.sg.fmk.constant;

/**
 * Enumération des différents niveau de logging possibles
 *
 * @author jntakpe
 */
public enum LogLevel {

    /**
     * Niveau le plus haut de log. A utiliser pour les erreurs
     */
    ERROR,
    /**
     * Messages d'avertissement
     */
    WARN,
    /**
     * Messages d'information
     */
    INFO,
    /**
     * Messages de debug
     */
    DEBUG,
    /**
     * Niveau le plus bas de log. Messages de trace
     */
    TRACE

}
