package fr.sg.fmk.service.impl;

import fr.sg.fmk.domain.Parameter;
import fr.sg.fmk.repository.FmkRepository;
import fr.sg.fmk.repository.ParameterRepository;
import fr.sg.fmk.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation des services associés à l'entité {@link fr.sg.fmk.domain.Parameter}
 *
 * @author jntakpe
 * @see GenericServiceImpl
 */
@Service
public class ParameterServiceImpl extends GenericServiceImpl<Parameter> implements ParameterService {

    /**
     * Encapsulation des appels aux loggers
     */
    @Autowired
    private ParameterRepository parameterRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public FmkRepository<Parameter> getRepository() {
        return parameterRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Parameter findByCode(String code) {
        return parameterRepository.findByCode(code);
    }

}
