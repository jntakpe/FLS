package fr.sg.fls.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

/**
 * Contrôle le démarrage et l'arrêt du batch 'Extraction des données des SCT filtrés et stockage en base'
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("sct")
public class SctMessageController {

    private final Logger logger = LoggerFactory.getLogger("applicative");

    @Autowired
    private MessageChannel controlChannel;

    @Autowired
    private MessageSource messageSource;

    /**
     * Lance le batch 'Extraction des données des SCT filtrés et stockage en base'
     */
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void startBatch() {
        controlChannel.send(new GenericMessage<String>("@jmsIn.start()"));
        logger.info(messageSource.getMessage("MSG00000", new Object[]{}, Locale.getDefault()));
    }

    /**
     * Arrete le batch 'Extraction des données des SCT filtrés et stockage en base'
     */
    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void stopBatch() {
        controlChannel.send(new GenericMessage<String>("@jmsIn.stop()"));
        logger.info(messageSource.getMessage("MSG00001", new Object[]{}, Locale.getDefault()));
    }
}
