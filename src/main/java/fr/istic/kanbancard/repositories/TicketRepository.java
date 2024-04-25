package fr.istic.kanbancard.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.istic.kanbancard.entities.Section;
import fr.istic.kanbancard.entities.Ticket;
import fr.istic.kanbancard.entities.Utilisateur;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	List<Ticket> findBySectionId(Long sectionId);
	List<Ticket> findBySection(Section section);
    List<Ticket> findByUtilisateur(Utilisateur utilisateur);
}
