package fr.sg.fls.integration;

import fr.sg.fls.generator.SCTMessageGenerator;
import fr.sg.fls.generator.SCTWrapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests du batch d'extraction et stockage des SCTs
 *
 * @author jntakpe
 */
@ContextConfiguration("classpath*:spring/applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SCTStorageTests {

    private static final int NB_MSG = 100;

    private static List<SCTWrapper> sctWrapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @BeforeClass
    public static void getSCTMessages() throws Exception {
        SCTMessageGenerator generator = new SCTMessageGenerator();
        sctWrapper = new ArrayList<SCTWrapper>(NB_MSG);
        for (int i = 0; i < NB_MSG; i++) sctWrapper.add(generator.generateMessage());
    }

    @Test
    public void readQueueTest() {
        enqueueMessages(NB_MSG);
    }

    private void enqueueMessages(int nbMsg) {
        if (nbMsg > NB_MSG) nbMsg = NB_MSG;
        for (int i = 0; i < nbMsg; i++) {
            jmsTemplate.send(new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(sctWrapper.get(0).getMessage());
                }
            });
        }
    }
}
