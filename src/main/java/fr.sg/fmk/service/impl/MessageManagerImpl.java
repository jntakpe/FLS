package fr.sg.fmk.service.impl;

import fr.sg.fmk.constant.LogLevel;
import fr.sg.fmk.exception.ErrorCode;
import fr.sg.fmk.service.MessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Implémentation de gestion des messages bundles
 *
 * @author jntakpe
 */
@Component
public class MessageManagerImpl implements MessageManager {

    /**
     * Logger SLF4J
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Bean encapsulant tous les accès aux différents bundles déclarés à Spring
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage(String codeMessage, Object... args) {
        return messageSource.getMessage(codeMessage, args, Locale.getDefault());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage(ErrorCode errorCode, Object... args) {
        String codeMessage = errorCode.toString().replace('_', '.').toLowerCase();
        return messageSource.getMessage(codeMessage, args, Locale.getDefault());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String logMessage(String codeMessage, LogLevel logLevel, Object... args) {
        String msg = getMessage(codeMessage, args);
        resolveLevelAndLog(msg, logLevel);
        return msg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String logMessage(ErrorCode errorCode, LogLevel logLevel, Object... args) {
        String msg = getMessage(errorCode, args);
        resolveLevelAndLog(msg, logLevel);
        return msg;
    }

    /**
     * Log le message avec le bon niveau
     *
     * @param msg      message a logger
     * @param logLevel niveau souhaité
     */
    private void resolveLevelAndLog(String msg, LogLevel logLevel) {
        switch (logLevel) {
            case ERROR:
                logger.error(msg);
                break;
            case WARN:
                logger.warn(msg);
                break;
            case INFO:
                logger.info(msg);
                break;
            case DEBUG:
                logger.debug(msg);
                break;
            case TRACE:
                logger.trace(msg);
                break;
        }
    }

}
