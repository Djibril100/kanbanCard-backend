package fr.istic.kanbancard.servicesInterface;

import java.util.List;

import fr.istic.kanbancard.dtos.BugTicketDTO;
import fr.istic.kanbancard.dtos.FeatureTicketDTO;
import fr.istic.kanbancard.dtos.TicketDTO;
import fr.istic.kanbancard.exceptions.SectionNotFoundException;
import fr.istic.kanbancard.exceptions.SectionOrUtilisateurNotFoundException;
import fr.istic.kanbancard.exceptions.TicketNotFoundException;
import fr.istic.kanbancard.exceptions.UtilisateurNotFoundException;

public interface TicketServiceInterface {
	
	TicketDTO saveTicket(Long sectionId, Long userId, String titre, String description) throws SectionOrUtilisateurNotFoundException;
	TicketDTO updateTicket(Long ticketId, Long sectionId, Long userId, String titre, String description) throws SectionOrUtilisateurNotFoundException, TicketNotFoundException;
	BugTicketDTO saveBugTicket(Long sectionId, Long userId, String titre, String description, String bugTrackingUrl) throws SectionOrUtilisateurNotFoundException;
	FeatureTicketDTO saveFeatureTicket(Long sectionId, Long userId, String titre, String description, String featureRelease) throws SectionOrUtilisateurNotFoundException;
	BugTicketDTO updateBugTicket(Long ticketId, Long sectionId, Long userId, String titre, String description, String bugTrackingUrl) throws SectionOrUtilisateurNotFoundException;
	FeatureTicketDTO updateFeatureTicket(Long ticketId, Long sectionId, Long userId, String titre, String description, String featureRelease) throws SectionOrUtilisateurNotFoundException;
	void deleteTicket(Long ticketId);
	
	List<TicketDTO> tickets();
	TicketDTO getTicket(Long ticketId) throws TicketNotFoundException;
	List<TicketDTO> getTicketsByUser(Long userId) throws UtilisateurNotFoundException;
	List<TicketDTO> getTicketsBySection(Long sectionId) throws SectionNotFoundException;
	
}
