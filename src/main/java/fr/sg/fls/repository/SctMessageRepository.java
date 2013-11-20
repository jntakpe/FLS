package fr.sg.fls.repository;

import fr.sg.fls.domain.SctMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Publication des méthodes de gestion de l'entité {@link SctMessage}
 *
 * @author jntakpe
 * @see JpaRepository
 */
public interface SctMessageRepository extends JpaRepository<SctMessage, Long> {

}
