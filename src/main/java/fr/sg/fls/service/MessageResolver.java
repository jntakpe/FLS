package fr.sg.fls.service;

/**
 * Couche supplémentaire sur l'utile mais fatiguant {@link org.springframework.context.MessageSource}
 *
 * @author jntakpe
 */
public interface MessageResolver {

    /**
     * Récupère les messages des différents bundles à partir d'un code et y ajoute les paramètres.
     *
     * @param code  code du message
     * @param attrs paramètres à remplacer dans le corps du message
     * @return le message prêt à être loggé
     */
    String findMessage(String code, Object... attrs);
}
