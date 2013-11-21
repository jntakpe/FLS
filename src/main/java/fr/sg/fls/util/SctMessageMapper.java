package fr.sg.fls.util;

import fr.sg.fls.domain.SctMessage;
import fr.sg.fls.enums.AppCode;
import fr.sg.fls.enums.IOIndicator;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * Permet de mapper les champs d'un message SCT avec le POJO {@link SctMessage}
 *
 * @author jntakpe
 */
public class SctMessageMapper implements FieldSetMapper<SctMessage> {

    /**
     * {@inheritDoc}
     */
    @Override
    public SctMessage mapFieldSet(FieldSet fieldSet) throws BindException {
        SctMessage sctMessage = new SctMessage();
        sctMessage.setMessageId(fieldSet.readString("messageId"));
        sctMessage.setEntity(fieldSet.readString("entity"));
        sctMessage.setIOIndicator(IOIndicator.valueOf(fieldSet.readString("IOIndicator")));
        sctMessage.setSender(fieldSet.readString("senderReference"));
        sctMessage.setAmount(fieldSet.readBigDecimal("amount"));
        sctMessage.setAppCode(AppCode.parse(fieldSet.readString("appCode")));
        sctMessage.setSender(fieldSet.readString("sender"));
        sctMessage.setReceiver(fieldSet.readString("receiver"));
        return sctMessage;
    }
}
