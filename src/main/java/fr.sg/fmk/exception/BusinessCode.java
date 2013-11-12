package fr.sg.fmk.exception;

/**
 * Enumération des exceptions métiers
 *
 * @author jntakpe
 */
public enum BusinessCode implements ErrorCode {
    /**
     * Exception lancée si la méthode standard de recherche l'objet courant est absente dans la couche repository
     */
    REPOSITORY_METHOD_MISSING,

    /**
     * Exception lancée si le champ donné est introuvable est introuvable sur l'entité
     */
    ENTITY_FIELD_MISSING,

    /**
     * Exception levée lorsque l'identifiant recherché n'est associé à aucune entité
     */
    ENTITY_NOT_FOUND
}
