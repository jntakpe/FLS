package fr.sg.fls.service;

import fr.sg.fls.domain.SctMessage;
import org.joda.time.Instant;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

/**
 * @author jntakpe
 */
@Component
public class SctTransformer {

    @Autowired
    private LineTokenizer sctLineTokenizer;

    @Autowired
    private BeanWrapperFieldSetMapper<SctMessage> autoSctMapper;

    @Transformer
    public SctMessage transform(Message message) throws BindException {
        SctMessage sctMessage = autoSctMapper.mapFieldSet(sctLineTokenizer.tokenize(message.getPayload().toString()));
        sctMessage.setFilterDate(Instant.now().toDate());
        return sctMessage;
    }

}
