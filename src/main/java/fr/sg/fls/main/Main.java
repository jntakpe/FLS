package fr.sg.fls.main;

import fr.sg.fls.service.MessageResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Classe mère de l'application. Contrôle le démarrage de l'application.
 *
 * @author jntakpe
 */
public class Main {

    private static final String SPRING_ROOT_CTX = "classpath:spring/applicationContext.xml";

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    /**
     * Point d'entrée de l'application
     *
     * @param args arguments passés en CLI
     */
    public static void main(final String[] args) {
        System.out.println("Lancement de l'application");
        ApplicationContext springCtx = new ClassPathXmlApplicationContext(SPRING_ROOT_CTX);
        MessageResolver messageResolver = springCtx.getBean(MessageResolver.class);
        LOG.info(messageResolver.findMessage("app.startup"));
    }
}
