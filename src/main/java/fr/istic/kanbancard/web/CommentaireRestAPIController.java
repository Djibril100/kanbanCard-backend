package fr.istic.kanbancard.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.istic.kanbancard.dtos.CommentaireDTO;
import fr.istic.kanbancard.exceptions.CommentaireNotFoundException;
import fr.istic.kanbancard.exceptions.TicketNotFoundException;
import fr.istic.kanbancard.exceptions.UtilisateurNotFoundException;
import fr.istic.kanbancard.exceptions.UtilisateurOrTicketNotFoundException;
import fr.istic.kanbancard.services.CommentaireService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class CommentaireRestAPIController {
	
	private CommentaireService service;
	
	// Enregistrer un nouveau commentaire
    @PostMapping("/commentaires")
    public CommentaireDTO saveCommentaire(@RequestBody CommentaireDTO commentaireDTO) 
                                          throws UtilisateurOrTicketNotFoundException {
        Long userId = commentaireDTO.getUtilisateurDTO().getId();
        Long ticketId = commentaireDTO.getTicketDTO().getId();
        String contenu = commentaireDTO.getContenu();
        return service.saveCommentaire(userId, ticketId, contenu);
    }

    // Mettre à jour un commentaire existant
    @PutMapping("/commentaires/{id}")
    public CommentaireDTO updateCommentaire(@PathVariable(name = "id") Long commentaireId,
                                            @RequestBody CommentaireDTO commentaireDTO) 
                                            throws UtilisateurOrTicketNotFoundException, CommentaireNotFoundException {
        Long userId = commentaireDTO.getUtilisateurDTO().getId();
        Long ticketId = commentaireDTO.getTicketDTO().getId();
        String contenu = commentaireDTO.getContenu();
        commentaireDTO.setId(commentaireId);
        return service.updateCommentaire(userId, ticketId, contenu, commentaireDTO);
    }

    // Supprimer un commentaire
    @DeleteMapping("/commentaires/{id}")
    public void deleteCommentaire(@PathVariable(name = "id") Long commentaireId) {
        service.deleteCommentaire(commentaireId);
    }

    // Récupérer tous les commentaires
    @GetMapping("/commentaires")
    public List<CommentaireDTO> commentaires() {
        return service.commentaires();
    }

    // Récupérer un commentaire par son ID
    @GetMapping("/commentaires/{id}")
    public CommentaireDTO getCommentaire(@PathVariable(name = "id") Long commentaireId) 
                                         throws CommentaireNotFoundException {
        return service.getCommentaire(commentaireId);
    }

    // Récupérer tous les commentaires d'un utilisateur par son ID
    @GetMapping("/utilisateurs/{id}/commentaires")
    public List<CommentaireDTO> getCommentairesByUser(@PathVariable(name = "id") Long userId) 
                                                       throws UtilisateurNotFoundException {
        return service.getCommentairesByUser(userId);
    }

    // Récupérer tous les commentaires d'un ticket par son ID
    @GetMapping("/tickets/{id}/commentaires")
    public List<CommentaireDTO> getCommentairesByTicket(@PathVariable(name = "id") Long ticketId) 
                                                         throws TicketNotFoundException {
        return service.getCommentairesByTicket(ticketId);
    }

    // Récupérer tous les commentaires d'un utilisateur et d'un ticket par leurs IDs
    @GetMapping("/utilisateurs/{userId}/tickets/{ticketId}/commentaires")
    public List<CommentaireDTO> getCommentairesByUserAndTicket(@PathVariable(name = "userId") Long userId,
                                                               @PathVariable(name = "ticketId") Long ticketId)
                                                               throws UtilisateurNotFoundException, TicketNotFoundException {
        return service.getCommentairesByUserAndTicket(userId, ticketId);
    }

}
