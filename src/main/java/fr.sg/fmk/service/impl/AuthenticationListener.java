package fr.sg.fmk.service.impl;

import fr.sg.fmk.constant.LogLevel;
import fr.sg.fmk.service.MessageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Classe permettant d'intercepter toute authentification
 *
 * @author jntakpe
 */
@Service
public class AuthenticationListener implements ApplicationListener<AuthenticationSuccessEvent> {

    /**
     * Encapsulation des appels aux loggers
     */
    @Autowired
    private MessageManager messageManager;


    /**
     * Méthode appellée à chaque login d'un utilisateur
     */
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        messageManager.logMessage("MSG00000", LogLevel.INFO, userDetails.getUsername());
    }
}
