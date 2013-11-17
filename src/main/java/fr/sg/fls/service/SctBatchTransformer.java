package fr.sg.fls.service;

import fr.sg.fls.domain.SctMessage;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.Payload;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Transforme le message brut envoyé de MQ en POJO du batch 'Extraction des données des SCT filtrés et stockage en base'
 *
 * @author jntakpe
 */
@Component
public class SctBatchTransformer {

    @Autowired
    private LineTokenizer sctLineTokenizer;

    @Autowired
    private BeanWrapperFieldSetMapper<SctMessage> autoSctMapper;

    @Autowired
    private Validator validator;

    @Autowired
    private MessageSource messageSource;

    private Logger logger = LoggerFactory.getLogger("applicative");

    /**
     * Transforme la ligne brute en POJO et vérifie qu'il respecte les contraintes (JSR303)
     *
     * @param payload corps du message
     * @param msgId   identifiant du message
     * @return le bean contenant les informations du message à persister
     * @throws BindException si le format du message est incorrect
     */
    @Transformer
    public SctMessage transform(@Payload String payload, @Header("jms_messageId") String msgId) throws BindException {
        logger.info(messageSource.getMessage("MSG00002", new Object[]{msgId}, Locale.getDefault()));
        FieldSet fs = sctLineTokenizer.tokenize(payload);
        SctMessage sctMessage = autoSctMapper.mapFieldSet(fs);
        sctMessage.setFilterDate(Instant.now().toDate());
        sctMessage.setJmsMsgId(msgId);
        validate(sctMessage);
        logger.info(messageSource.getMessage("MSG00003", new Object[]{msgId}, Locale.getDefault()));
        return sctMessage;
    }

    /**
     * Valide le bean contenant les informations à persister
     *
     * @param sctMessage POJO contenant les informations à persister
     * @return le bean si validation ok sinon une exception contenant les champs invalides
     */
    private SctMessage validate(SctMessage sctMessage) {
        Set<ConstraintViolation<SctMessage>> errors = validator.validate(sctMessage);
        if (errors.isEmpty()) return sctMessage;
        else throw new ConstraintViolationException(errors.toString(), new HashSet<ConstraintViolation<?>>(errors));
    }

}
