package fr.sg.fmk.service;

import fr.sg.fmk.domain.Parameter;

/**
 * Services associ�s � l'entit� {@link fr.sg.fmk.domain.Parameter}
 *
 * @author jntakpe
 * @see GenericService
 */
public interface ParameterService extends GenericService<Parameter> {

    /**
     * R�cup�re un {@link Parameter} � l'aide de son code
     *
     * @param code code du param�tre
     * @return le param�tre correspondant � ce code
     */
    Parameter findByCode(String code);
}
