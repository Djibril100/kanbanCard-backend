package fr.istic.kanbancard.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.istic.kanbancard.entities.BugTicket;
import fr.istic.kanbancard.entities.FeatureRequestTicket;
import fr.istic.kanbancard.entities.Ticket;
import fr.istic.kanbancard.enums.BugStatus;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	List<Ticket> findBySectionId(Long sectionId);
	
	@Query("SELECT t FROM Ticket t JOIN t.utilisateurs u WHERE u.id = :utilisateurId")
    List<Ticket> findTicketsByUtilisateurId(@Param("utilisateurId") Long utilisateurId);
	
	@Query("SELECT t FROM Ticket t WHERE TYPE(t) = BugTicket AND t.status = :status")
    List<BugTicket> findBugTicketsByStatus(@Param("status") BugStatus status);
	
	@Query("SELECT t FROM Ticket t WHERE TYPE(t) = FeatureRequestTicket AND t.featureRelease = :featureRelease")
	List<FeatureRequestTicket> findFeatureRequestTicketsByRelease(@Param("featureRelease") String featureRelease);

}
