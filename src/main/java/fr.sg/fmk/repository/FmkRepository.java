package fr.sg.fmk.repository;

import fr.sg.fmk.domain.GenericDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interface à étendre lors de la création d'un repository géré par le framework.
 * Permet de bénéficier grâce à {@link JpaRepository} de tous les CRUDs et d'autres méthodes usuelles et grâce à
 * {@link QueryDslPredicateExecutor} de la création de méthodes de filtrage type safe.
 *
 * @author jntakpe
 * @see JpaRepository
 * @see QueryDslPredicateExecutor
 */
@NoRepositoryBean
public interface FmkRepository<T extends GenericDomain> extends JpaRepository<T, Long>, QueryDslPredicateExecutor<T> {

}
