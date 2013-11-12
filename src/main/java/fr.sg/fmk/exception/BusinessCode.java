package fr.sg.fmk.exception;

/**
 * Enum�ration des exceptions m�tiers
 *
 * @author jntakpe
 */
public enum BusinessCode implements ErrorCode {
    /**
     * Exception lanc�e si la m�thode standard de recherche l'objet courant est absente dans la couche repository
     */
    REPOSITORY_METHOD_MISSING,

    /**
     * Exception lanc�e si le champ donn� est introuvable est introuvable sur l'entit�
     */
    ENTITY_FIELD_MISSING,

    /**
     * Exception lev�e lorsque l'identifiant recherch� n'est associ� � aucune entit�
     */
    ENTITY_NOT_FOUND
}
