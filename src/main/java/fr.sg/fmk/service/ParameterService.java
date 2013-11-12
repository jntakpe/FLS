package fr.sg.fmk.service;

import fr.sg.fmk.domain.Parameter;

/**
 * Services associés à l'entité {@link fr.sg.fmk.domain.Parameter}
 *
 * @author jntakpe
 * @see GenericService
 */
public interface ParameterService extends GenericService<Parameter> {

    /**
     * Récupère un {@link Parameter} à l'aide de son code
     *
     * @param code code du paramètre
     * @return le paramètre correspondant à ce code
     */
    Parameter findByCode(String code);
}
