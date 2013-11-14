package fr.sg.fls.domain;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * @author jntakpe
 */
public class SctMessageMapper implements FieldSetMapper<SctMessage> {

    @Override
    public SctMessage mapFieldSet(FieldSet fieldSet) throws BindException {
        return new SctMessage(fieldSet.readString("versionTag"), fieldSet.readString("messageId"), fieldSet.readString("entity"));
    }
}
