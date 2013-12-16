package fr.sg.fls.service.impl;

import fr.sg.fls.domain.SctMessage;
import fr.sg.fls.repository.SctMessageRepository;
import fr.sg.fls.service.MessageResolver;
import fr.sg.fls.service.SctMessageService;
import fr.sg.fls.util.SctMessageMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.integration.MessagingException;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.Payload;
import org.springframework.integration.transformer.MessageTransformationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;

import javax.validation.ConstraintViolationException;

/**
 * Implémentation des services sur le {@link SctMessage} contenant les informations sur le SCT filtré.
 *
 * @author jntakpe
 */
@Service
public class SctMessageServiceImpl implements SctMessageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SctMessageRepository sctMessageRepository;

    @Autowired
    private LineTokenizer sctLineTokenizer;

    @Autowired
    private MessageResolver messageResolver;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void save(@Payload SctMessage sctMessage) {
        sctMessageRepository.save(sctMessage);
        logger.info(messageResolver.findMessage("MSG00004", sctMessage));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SctMessage transform(@Header("jms_messageId") String msgId, @Payload String payload) throws BindException {
        logger.info(messageResolver.findMessage("MSG00002", msgId)); //Réception d'un nouveau message.
        SctMessage sctMessage = new SctMessageMapper().mapFieldSet(sctLineTokenizer.tokenize(payload));
        sctMessage.setJMSMessageId(msgId);
        logger.info(messageResolver.findMessage("MSG00003", sctMessage)); //Transformation du message : {0} effectuée.
        return sctMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleError(@Payload MessagingException e) {
        e.printStackTrace();
        String msgId = (String) e.getFailedMessage().getHeaders().get("jms_messageId");
        if (ExceptionUtils.indexOfThrowable(e, MessageTransformationException.class) != -1)
            logger.error(messageResolver.findMessage("MSG00005", msgId));
        else if (ExceptionUtils.indexOfThrowable(e, DataIntegrityViolationException.class) != -1)
            logger.error(messageResolver.findMessage("MSG00006", msgId));
        else if (ExceptionUtils.indexOfThrowable(e, CannotCreateTransactionException.class) != -1)
            logger.error(messageResolver.findMessage("MSG00007"));
        else if (ExceptionUtils.indexOfThrowable(e, ConstraintViolationException.class) != -1)
            logger.error(messageResolver.findMessage("MSG00008", msgId));
        else logger.error(messageResolver.findMessage("MSG00009"));
    }

}
