package fr.sg.fls.service;

import fr.sg.fls.domain.SctMessage;
import org.joda.time.Instant;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.Payload;
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
    public SctMessage transform(@Payload String payload, @Header("jms_messageId") String msgId) {
        SctMessage sctMessage;
        try {
            sctMessage = autoSctMapper.mapFieldSet(sctLineTokenizer.tokenize(payload));
        } catch (BindException e) {
            e.printStackTrace();

        }
        sctMessage.setFilterDate(Instant.now().toDate());
        sctMessage.setJmsMsgId(msgId);
        return sctMessage;
    }

}
