package fr.sg.fmk.service;

import fr.sg.fmk.domain.GenericDomain;
import fr.sg.fmk.dto.DatatablesRequest;
import fr.sg.fmk.dto.Unicity;
import fr.sg.fmk.exception.BusinessCode;
import fr.sg.fmk.exception.BusinessException;
import org.springframework.data.domain.Page;

/**
 * Interface fournissant les services usuels
 *
 * @author jntakpe
 */
public interface GenericService<T extends GenericDomain> {

    /**
     * Compte le nombre d'entit� dans une table
     *
     * @return nombre d'entit�
     */
    long count();

    /**
     * Retrouve une entit� � l'aide de son identifiant
     *
     * @param id identifiant de l'entit�
     * @return l'entit� poss�dant cette id
     * @nullable
     */
    T findOne(Long id);

    /**
     * Retrouve toutes les entit�s de la table
     *
     * @return entit�s de la table
     */
    Iterable<T> findAll();

    /**
     * Renvoi uniquement les donn�es � afficher dans une page.
     * Filtre, tri et pagine les donn�es.
     *
     * @param datatablesRequest �tat de la liste DataTables
     * @return les informations n�cessaires � l'affichage de la page
     * @see <a href="http://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html#repositories.special-parameters">Pagination</a>
     */
    Page<T> page(DatatablesRequest datatablesRequest);

    /**
     * Indique si l'entit� existe en table
     *
     * @param id identifiant de l'entit�
     * @return true si exist
     */
    boolean exists(Long id);

    /**
     * Supprime l'entit� ayant cet identifiant
     *
     * @param id id de l'entit�
     */
    void delete(Long id);

    /**
     * Supprime l'entit� pass�e en param�tre
     *
     * @param entity entit� � supprimer
     */
    void delete(T entity);

    /**
     * Sauvegarde l'entit�. Si elle n'existe pas fait un 'persist' sinon un 'merge'.
     * Seule l'entit� renvoy�e est 'managed', celle  pass�e en param�tre est 'detached'.
     * En d'autres termes, toutes les modifications effectu�es sur l'entit� renvoy�e par la fonction seront persist�es
     * � la fin de la transaction alors que celles effectu�es sur l'objet pass� en param�tre ne seront pas persist�es.
     *
     * @param entity entit� � sauvegarder
     * @return l'entit� 'managed'. Attention ce n'est pas le m�me objet que celui pass� en param�tre.
     */
    T save(T entity);

    /**
     * Si une contrainte d'unicit� existe sur un champ renvoi true si cette derni�re est respect�e
     *
     * @param unicity objet contenant les informations sur le champ � contr�ler
     * @return true si cette valeur respecte la contrainte d'unicit� sinon false
     */
    boolean isAvaillable(Unicity unicity);

    /**
     * Cr�� une exception m�tier
     *
     * @param code        code repr�sentant l'exception
     * @param errorParams param�tres du message d'erreur
     * @return BusinessException initialis�e
     */
    BusinessException createBussinessException(BusinessCode code, Object... errorParams);

}
