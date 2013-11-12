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
     * Compte le nombre d'entité dans une table
     *
     * @return nombre d'entité
     */
    long count();

    /**
     * Retrouve une entité à l'aide de son identifiant
     *
     * @param id identifiant de l'entité
     * @return l'entité possédant cette id
     * @nullable
     */
    T findOne(Long id);

    /**
     * Retrouve toutes les entités de la table
     *
     * @return entités de la table
     */
    Iterable<T> findAll();

    /**
     * Renvoi uniquement les données à afficher dans une page.
     * Filtre, tri et pagine les données.
     *
     * @param datatablesRequest état de la liste DataTables
     * @return les informations nécessaires à l'affichage de la page
     * @see <a href="http://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html#repositories.special-parameters">Pagination</a>
     */
    Page<T> page(DatatablesRequest datatablesRequest);

    /**
     * Indique si l'entité existe en table
     *
     * @param id identifiant de l'entité
     * @return true si exist
     */
    boolean exists(Long id);

    /**
     * Supprime l'entité ayant cet identifiant
     *
     * @param id id de l'entité
     */
    void delete(Long id);

    /**
     * Supprime l'entité passée en paramètre
     *
     * @param entity entité à supprimer
     */
    void delete(T entity);

    /**
     * Sauvegarde l'entité. Si elle n'existe pas fait un 'persist' sinon un 'merge'.
     * Seule l'entité renvoyée est 'managed', celle  passée en paramètre est 'detached'.
     * En d'autres termes, toutes les modifications effectuées sur l'entité renvoyée par la fonction seront persistées
     * à la fin de la transaction alors que celles effectuées sur l'objet passé en paramètre ne seront pas persistées.
     *
     * @param entity entité à sauvegarder
     * @return l'entité 'managed'. Attention ce n'est pas le même objet que celui passé en paramètre.
     */
    T save(T entity);

    /**
     * Si une contrainte d'unicité existe sur un champ renvoi true si cette dernière est respectée
     *
     * @param unicity objet contenant les informations sur le champ à contrôler
     * @return true si cette valeur respecte la contrainte d'unicité sinon false
     */
    boolean isAvaillable(Unicity unicity);

    /**
     * Créé une exception métier
     *
     * @param code        code représentant l'exception
     * @param errorParams paramètres du message d'erreur
     * @return BusinessException initialisée
     */
    BusinessException createBussinessException(BusinessCode code, Object... errorParams);

}
