package fr.sg.fls.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.integration.MessageHandlingException;
import org.springframework.integration.MessagingException;
import org.springframework.integration.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import javax.validation.ConstraintViolationException;
import java.util.Locale;

/**
 * G�re les erreurs du batch 'Extraction des donn�es des SCT filtr�s et stockage en base'
 *
 * @author jntakpe
 */
@Component
public class SctBatchErrorHandler {

    @Autowired
    private MessageSource messageSource;

    /**
     * M�thode captant les exceptions envoy�s dans le canal d�di�
     *
     * @param e wrapper de l'exception
     */
    public void handleError(@Payload MessagingException e) {
        Logger logger = LoggerFactory.getLogger("error");
        String msgId = (String) e.getFailedMessage().getHeaders().get("jms_messageId");
        Throwable cause = e.getCause();
        if (cause instanceof MessageHandlingException) cause = cause.getCause();
        if (cause instanceof BindException)
            logger.error(messageSource.getMessage("MSG00004", new Object[]{msgId}, Locale.getDefault()), e.getCause());
        if (cause instanceof ConstraintViolationException)
            logger.error(messageSource.getMessage("MSG00005", new Object[]{msgId}, Locale.getDefault()), e.getCause());
        else if (cause instanceof DataIntegrityViolationException)
            logger.error(messageSource.getMessage("MSG00006", new Object[]{msgId}, Locale.getDefault()), e.getCause());
        else
            logger.error(messageSource.getMessage("MSG00007", new Object[]{msgId}, Locale.getDefault()), e.getCause());
    }

}
