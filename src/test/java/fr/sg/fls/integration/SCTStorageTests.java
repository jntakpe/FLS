package fr.sg.fls.integration;

import fr.sg.fls.generator.SCTMessageGenerator;
import fr.sg.fls.generator.SCTWrapper;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    private static final int NB_MSG = 200;

    private static List<SCTWrapper> sctWrapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeClass
    public static void getSCTMessages() throws Exception {
        SCTMessageGenerator generator = new SCTMessageGenerator();
        sctWrapper = new ArrayList<SCTWrapper>(NB_MSG);
        for (int i = 0; i < NB_MSG; i++) sctWrapper.add(generator.generateMessage());
    }

    @Test
    public void readAndPersistTest() {
        Integer initialNb = jdbcTemplate.queryForObject("SELECT count(*) FROM sct_message", Integer.class);
        enqueueMessages(NB_MSG);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        boolean msgStored = false;
        long chrono = 0L;
        while (!msgStored) {
            if (NB_MSG + initialNb == jdbcTemplate.queryForObject("SELECT count(*) FROM sct_message", Integer.class))
                msgStored = true;
            stopWatch.split();
            chrono = stopWatch.getSplitTime();
            if (chrono > NB_MSG * 10) break;
        }
        if (!msgStored) Assert.fail("Les messages n'ont pas été ajoutés dans le temps imparti");
        logger.info(NB_MSG + " messages lus et stockés en : " + chrono + " ms");
    }

    private void enqueueMessages(int nbMsg) {
        if (nbMsg > NB_MSG) nbMsg = NB_MSG;
        for (int i = 0; i < nbMsg; i++) {
            final int finalI = i;
            jmsTemplate.send(new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(sctWrapper.get(finalI).getMessage());
                }
            });
        }
    }
}
