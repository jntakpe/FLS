package fr.sg.fls.service;

import fr.sg.fls.domain.SctMessage;
import org.joda.time.Instant;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.Payload;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
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

    @Transformer
    public SctMessage transform(@Payload String payload, @Header("jms_messageId") String msgId) throws BindException {
        FieldSet fs = sctLineTokenizer.tokenize(payload);
        SctMessage sctMessage = autoSctMapper.mapFieldSet(fs);
        sctMessage.setFilterDate(Instant.now().toDate());
        sctMessage.setJmsMsgId(msgId);
        return validate(sctMessage);
    }

    private SctMessage validate(SctMessage sctMessage) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<SctMessage>> erros = validator.validate(sctMessage);
        if (erros.isEmpty()) return sctMessage;
        else throw new ConstraintViolationException(erros.toString(), new HashSet<ConstraintViolation<?>>(erros));
    }

}
