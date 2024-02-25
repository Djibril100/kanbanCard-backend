package fr.istic.kanbancard.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.istic.kanbancard.entities.Commentaire;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
	List<Commentaire> findByTicketId(Long ticketId);
}
