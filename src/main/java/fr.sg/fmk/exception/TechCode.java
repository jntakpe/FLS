package fr.sg.fmk.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;

/**
 * Codes d'erreurs relatifs aux exceptions techniques
 *
 * @author jntakpe
 */
public enum TechCode implements ErrorCode {

    /**
     * Exception lev�e en cas de violation de contrainte
     */
    CONSTRAINT_VIOLATION(DataIntegrityViolationException.class),
    /**
     * Exception lev�e en cas de modification concurrente
     */
    OPTIMISTIC_LOCKING(OptimisticLockingFailureException.class);

    /**
     * Classe de l'exception lev�e
     */
    private final Class<?> sourceException;

    private TechCode(Class<?> sourceException) {
        this.sourceException = sourceException;
    }

    public Class<?> getSourceException() {
        return sourceException;
    }
}
