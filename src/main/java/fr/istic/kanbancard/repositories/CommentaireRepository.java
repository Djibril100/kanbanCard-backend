package fr.istic.kanbancard.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.istic.kanbancard.entities.Commentaire;
import fr.istic.kanbancard.entities.Ticket;
import fr.istic.kanbancard.entities.Utilisateur;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
	List<Commentaire> findByTicketId(Long ticketId);
	List<Commentaire> findByUtilisateurId(Long utilisateurId);
	List<Commentaire> findByUtilisateurAndTicket(Utilisateur utilisateur, Ticket ticket);
}
