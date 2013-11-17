package fr.sg.fls.integration;

import fr.sg.fls.generator.SCTMessageGenerator;
import fr.sg.fls.generator.SCTWrapper;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.message.GenericMessage;
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

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests du batch d'extraction et stockage des SCTs
 *
 * @author jntakpe
 */
@ContextConfiguration("classpath*:spring/applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SCTStorageTests {

    private static final Integer NB_MSG = 100;

    private final SCTMessageGenerator generator = new SCTMessageGenerator();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MessageChannel controlChannel;

    @Test
    public void batchControlTest() {
        int msgNb = 1;
        String req = "SELECT count(*) FROM sct_message";
        controlChannel.send(new GenericMessage<String>("@jmsIn.stop()"));
        jmsTemplate.setReceiveTimeout(1000);
        controlChannel.send(new GenericMessage<String>("@jmsIn.stop()"));
        Integer initialNb = jdbcTemplate.queryForObject(req, Integer.class);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        enqueueMsg(msgNb);
        while (true) {
            stopWatch.split();
            if (stopWatch.getSplitTime() > 1000) break;
        }
        stopWatch.reset();
        assertThat(jdbcTemplate.queryForObject(req, Integer.class)).isEqualTo(initialNb);
        controlChannel.send(new GenericMessage<String>("@jmsIn.start()"));
        stopWatch.start();
        while (true) {
            stopWatch.split();
            if (stopWatch.getSplitTime() > 1000) break;
        }
        assertThat(jdbcTemplate.queryForObject(req, Integer.class)).isEqualTo(initialNb + msgNb);
    }

    @Test
    public void readAndPersistTest() {
        controlChannel.send(new GenericMessage<String>("@jmsIn.start()"));
        Integer initialNb = jdbcTemplate.queryForObject("SELECT count(*) FROM sct_message", Integer.class);
        enqueueMsg(NB_MSG);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        boolean msgStored = false;
        long chrono = 0L;
        while (!msgStored) {
            if (NB_MSG + initialNb == jdbcTemplate.queryForObject("SELECT count(*) FROM sct_message", Integer.class))
                msgStored = true;
            stopWatch.split();
            chrono = stopWatch.getSplitTime();
            if (chrono > NB_MSG * 100 && chrono > 100L) break;
        }
        if (!msgStored) Assert.fail("Les messages n'ont pas été ajoutés dans le temps imparti");
        logger.info(NB_MSG + " messages lus et stockés en : " + chrono + " ms");
        controlChannel.send(new GenericMessage<String>("@jmsIn.stop()"));
    }


    @Test
    public void testFailSameMessage() {
        controlChannel.send(new GenericMessage<String>("@jmsIn.start()"));
        String req = "SELECT count(*) FROM sct_message";
        Integer initialNb = jdbcTemplate.queryForObject(req, Integer.class);
        final SCTWrapper sctWrapper = generator.generateMessage();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(sctWrapper.getMessage());
            }
        };
        jmsTemplate.send(messageCreator);
        jmsTemplate.send(messageCreator);
        while (true) {
            stopWatch.split();
            if (stopWatch.getSplitTime() > 1000) break;
        }
        assertThat(jdbcTemplate.queryForObject(req, Integer.class)).isEqualTo(initialNb + 1);
        controlChannel.send(new GenericMessage<String>("@jmsIn.stop()"));
    }

    @Test
    public void testFailFormatMessage() {
        final String stupidMsg = "H";
        controlChannel.send(new GenericMessage<String>("@jmsIn.start()"));
        String req = "SELECT count(*) FROM sct_message";
        Integer initialNb = jdbcTemplate.queryForObject(req, Integer.class);
        final SCTWrapper sctWrapper = generator.generateMessage();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(stupidMsg);
            }
        });
        while (true) {
            stopWatch.split();
            if (stopWatch.getSplitTime() > 1000) break;
        }
        assertThat(jdbcTemplate.queryForObject(req, Integer.class)).isEqualTo(initialNb);
        controlChannel.send(new GenericMessage<String>("@jmsIn.stop()"));
    }

    private void enqueueMsg(int msgNb) {
        final List<SCTWrapper> sctWrappers = new ArrayList<SCTWrapper>(msgNb);
        for (int i = 0; i < msgNb; i++) sctWrappers.add(generator.generateMessage());
        for (int i = 0; i < msgNb; i++) {
            final int finalI = i;
            jmsTemplate.send(new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(sctWrappers.get(finalI).getMessage());
                }
            });
        }
    }

}
