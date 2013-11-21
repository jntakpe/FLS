package fr.sg.fls.web;

import fr.sg.fls.service.MessageResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Contrôle le démarrage et l'arrêt du batch 'Traitement de constitution des CRE de facturation'
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("cre")
public class ConstitutionCreController {

    private final Logger logger = LoggerFactory.getLogger("applicative");

    @Autowired
    private MessageResolver messageResolver;

    @Autowired
    private JobLauncher jobLauncher;

    /**
     * Lance le batch 'Extraction des données des SCT filtrés et stockage en base'
     */
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void startBatch() {
        logger.info(messageResolver.findMessage("MSG10000"));
    }

}
