package fr.istic.kanbancard.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.istic.kanbancard.entities.DeplacementTicket;

public interface DeplacementTicketRepository extends JpaRepository<DeplacementTicket, Long> {
	
}
