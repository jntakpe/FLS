package fr.sg.fmk.service;

import java.lang.annotation.*;

/**
 * Annotation permettant � Spring de binder les param�tres envoy�s par DataTables avec l'objet
 * {@link fr.sg.fmk.dto.DatatablesRequest}.
 *
 * @author jntakpe
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DatatablesParams {

}
