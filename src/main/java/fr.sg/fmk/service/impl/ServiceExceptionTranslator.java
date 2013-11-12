package fr.sg.fmk.service.impl;


import fr.sg.fmk.exception.TechCode;
import fr.sg.fmk.exception.TechException;
import fr.sg.fmk.service.MessageManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Capte les exceptions courantes sortant de la couche service puis si possible les traduit en exception géré par le
 * framework.
 * Cette classe est éxécutée après le passages des post-processeurs Spring
 *
 * @author jntakpe
 */
@Aspect
@Component
@Order(Integer.MIN_VALUE)
public class ServiceExceptionTranslator {

    /**
     * Encapsulation des appels aux loggers
     */
    @Autowired
    private MessageManager messageManager;

    /**
     * Méthode interceptant les exceptions lancées par la couche service/business
     *
     * @param joinPoint méthode initialement appelée (greffon)
     * @return L'objet normalement retourné par la méthode appelée
     * @throws Throwable rethrow les exceptions
     */
    @Around("execution(* fr.sg.*.service..*.*(..))")
    public Object catchExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            e.printStackTrace();
            for (TechCode techCode : TechCode.values()) {
                if (e.getClass().isAssignableFrom(techCode.getSourceException()))
                    throw new TechException(e, messageManager.getMessage(techCode), techCode);
            }
            throw e;
        }
        return result;
    }

}
