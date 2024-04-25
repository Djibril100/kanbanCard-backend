package fr.istic.kanbancard.servicesInterface;

import java.util.List;

import fr.istic.kanbancard.dtos.CommentaireDTO;
import fr.istic.kanbancard.exceptions.CommentaireNotFoundException;
import fr.istic.kanbancard.exceptions.TicketNotFoundException;
import fr.istic.kanbancard.exceptions.UtilisateurNotFoundException;
import fr.istic.kanbancard.exceptions.UtilisateurOrTicketNotFoundException;

public interface CommentaireServiceInterface {
	
	CommentaireDTO saveCommentaire(Long userId, Long ticketId, String contenu) throws UtilisateurOrTicketNotFoundException;
	CommentaireDTO updateCommentaire(Long userId, Long ticketId, String contenu, CommentaireDTO commentaireDTO) throws UtilisateurOrTicketNotFoundException, CommentaireNotFoundException;
	void deleteCommentaire(Long commentaireId);
	
	List<CommentaireDTO> commentaires();
	CommentaireDTO getCommentaire(Long commentaireId) throws CommentaireNotFoundException;
	List<CommentaireDTO> getCommentairesByUser(Long userId) throws UtilisateurNotFoundException;
	List<CommentaireDTO> getCommentairesByTicket(Long ticketId) throws TicketNotFoundException;
	List<CommentaireDTO> getCommentairesByUserAndTicket(Long userId, Long ticketId) throws UtilisateurNotFoundException, TicketNotFoundException;
	
}
