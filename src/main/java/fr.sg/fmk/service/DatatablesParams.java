package fr.sg.fmk.service;

import java.lang.annotation.*;

/**
 * Annotation permettant à Spring de binder les paramètres envoyés par DataTables avec l'objet
 * {@link fr.sg.fmk.dto.DatatablesRequest}.
 *
 * @author jntakpe
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DatatablesParams {

}
