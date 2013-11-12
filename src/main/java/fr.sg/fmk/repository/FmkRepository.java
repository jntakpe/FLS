package fr.sg.fmk.repository;

import fr.sg.fmk.domain.GenericDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interface � �tendre lors de la cr�ation d'un repository g�r� par le framework.
 * Permet de b�n�ficier gr�ce � {@link JpaRepository} de tous les CRUDs et d'autres m�thodes usuelles et gr�ce �
 * {@link QueryDslPredicateExecutor} de la cr�ation de m�thodes de filtrage type safe.
 *
 * @author jntakpe
 * @see JpaRepository
 * @see QueryDslPredicateExecutor
 */
@NoRepositoryBean
public interface FmkRepository<T extends GenericDomain> extends JpaRepository<T, Long>, QueryDslPredicateExecutor<T> {

}
