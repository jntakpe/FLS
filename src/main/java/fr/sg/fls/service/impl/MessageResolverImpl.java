package fr.sg.fls.service.impl;

import fr.sg.fls.service.MessageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Implémentation de la surcouche de {@link MessageSource}
 *
 * @author jntakpe
 */
@Service
public class MessageResolverImpl implements MessageResolver {

    @Autowired
    private MessageSource messageSource;

    @Override
    public String findMessage(String code, Object... attrs) {
        return messageSource.getMessage(code, attrs, Locale.getDefault());
    }
}
