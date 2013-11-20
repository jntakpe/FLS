package fr.sg.fls.service;

import fr.sg.fls.domain.SctMessage;
import org.springframework.integration.MessagingException;
import org.springframework.integration.annotation.Payload;
import org.springframework.validation.BindException;

/**
 * Publication des services associés à l'entité
 *
 * @author jntakpe
 */
public interface SctMessageService {

    /**
     * Créé ou modifie un enregistrement {@link SctMessage} en base de données
     *
     * @param sctMessage {@link SctMessage} à enregistrer
     */
    void save(SctMessage sctMessage);

    /**
     * Transforme la ligne brute récupérée dans le messageMQ et la transforme en {@link SctMessage}
     *
     * @param msgId   identifiant JMS unique du message
     * @param payload corps du message
     * @return le bean contenant les informations du message à persister
     * @throws BindException si le format du message est incorrect
     */
    SctMessage transform(String msgId, String payload) throws BindException;

    /**
     * Gère les erreurs pouvant survenir lors du batch 'Extraction des données des SCT filtrés et stockage en base'
     *
     * @param e exception levée encapsulée par Spring integration dans une {@link MessagingException}
     */
    void handleError(@Payload MessagingException e);
}
