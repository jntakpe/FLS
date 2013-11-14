package fr.sg.fls.service.impl;

import fr.sg.fls.domain.SctMessage;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jntakpe
 */
@Component
public class StupidWriter implements ItemWriter<SctMessage> {

    @Override
    public void write(List<? extends SctMessage> items) throws Exception {
        for (SctMessage item : items) {
            System.out.println("VersionTag : " + item.getVersionTag() + ", MessageId : " + item.getMessageId() +
                    ", entity : " + item.getEntity());
        }
    }
}
