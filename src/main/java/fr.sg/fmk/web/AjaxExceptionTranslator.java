package fr.sg.fmk.web;

import fr.sg.fmk.dto.ResponseMessage;
import fr.sg.fmk.exception.AjaxException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Capte les exceptions sortant des m�thodes AJAX et les encapsule dans une {@link fr.sg.fmk.exception.AjaxException}
 *
 * @author jntakpe
 */
@Aspect
@Component
@Order(Integer.MAX_VALUE)
public class AjaxExceptionTranslator {

    /**
     * M�thode interceptant les exceptions lanc�es par la couche web. Dans le cas d'un appel AJAX avec renvoi d'un
     * {@link ResponseMessage} on encapsule l'exception
     *
     * @param joinPoint m�thode initialement appel�e (greffon)
     * @return L'objet normalement retourn� par la m�thode appel�e
     * @throws Throwable rethrow les exceptions
     */
    @Around("execution(* fr.sg.*.web..*.*(..))" +
            "&& @annotation(org.springframework.web.bind.annotation.ResponseBody)")
    public Object catchExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            throw new AjaxException(e);
        }
        return result;
    }
}

