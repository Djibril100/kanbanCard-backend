package fr.istic.kanbancard.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.istic.kanbancard.dtos.CommentaireDTO;
import fr.istic.kanbancard.entities.Commentaire;
import fr.istic.kanbancard.entities.Ticket;
import fr.istic.kanbancard.entities.Utilisateur;
import fr.istic.kanbancard.exceptions.CommentaireNotFoundException;
import fr.istic.kanbancard.exceptions.TicketNotFoundException;
import fr.istic.kanbancard.exceptions.UtilisateurNotFoundException;
import fr.istic.kanbancard.exceptions.UtilisateurOrTicketNotFoundException;
import fr.istic.kanbancard.mappers.KanbanCardMapper;
import fr.istic.kanbancard.repositories.CommentaireRepository;
import fr.istic.kanbancard.repositories.TicketRepository;
import fr.istic.kanbancard.repositories.UtilisateurRepository;
import fr.istic.kanbancard.servicesInterface.CommentaireServiceInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CommentaireService implements CommentaireServiceInterface {
	
	private CommentaireRepository commentaireRepository;
	private UtilisateurRepository utilisateurRepository;
	private TicketRepository ticketRepository;
	private KanbanCardMapper mapperService;
	
	
	@Override
	public CommentaireDTO saveCommentaire(Long userId, Long ticketId, String contenu)
			throws UtilisateurOrTicketNotFoundException {
		
		log.info("Enregistrement d'un nouveau Commentaire");
		Utilisateur user = utilisateurRepository.findById(userId).orElse(null);
		Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
		if(user == null || ticket == null)
			throw new UtilisateurOrTicketNotFoundException("Utilisateur ou Ticket non trouves !");
		
		Commentaire commentaire = new Commentaire();
		commentaire.setContenu(contenu);
		commentaire.setDateCreation(new Date());
		commentaire.setUtilisateur(user);
		commentaire.setTicket(ticket);
		Commentaire savedCommentaire = commentaireRepository.save(commentaire);
		return this.mapperService.fromCommentaire(savedCommentaire);	
	}
	@Override
	public CommentaireDTO updateCommentaire(Long userId, Long ticketId, String contenu, CommentaireDTO commentaireDTO)
			throws UtilisateurOrTicketNotFoundException, CommentaireNotFoundException {
		log.info("Mise a jour d'un Commentaire");
		Utilisateur user = utilisateurRepository.findById(userId).orElse(null);
		Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
		if(user == null || ticket == null)
			throw new UtilisateurOrTicketNotFoundException("Utilisateur ou Ticket non trouves !");
		
		Commentaire commentaire = commentaireRepository.findById(commentaireDTO.getId())
	            .orElseThrow(() -> new CommentaireNotFoundException("Commentaire non trouvé avec l'ID " + commentaireDTO.getId()));
		commentaire.setContenu(contenu);
		commentaire.setDateCreation(new Date());
		commentaire.setUtilisateur(user);
		commentaire.setTicket(ticket);
		Commentaire updatedCommentaire = commentaireRepository.save(commentaire);
		CommentaireDTO updatedCommentaireDTO = this.mapperService.fromCommentaire(updatedCommentaire);
		return updatedCommentaireDTO;
	}
	@Override
	public void deleteCommentaire(Long commentaireId) {
		commentaireRepository.deleteById(commentaireId);
		
	}
	@Override
	public List<CommentaireDTO> commentaires() {
		 List<Commentaire> commentaires = commentaireRepository.findAll();
	        return commentaires.stream()
	                .map(mapperService::fromCommentaire)
	                .collect(Collectors.toList());
		
	}
	@Override
	public CommentaireDTO getCommentaire(Long commentaireId) throws CommentaireNotFoundException {
		Commentaire commentaire = commentaireRepository.findById(commentaireId)
                .orElseThrow(() -> new CommentaireNotFoundException("Commentaire non trouve !"));
        return this.mapperService.fromCommentaire(commentaire);
	}
	@Override
	public List<CommentaireDTO> getCommentairesByUser(Long userId) throws UtilisateurNotFoundException {
		List<Commentaire> commentaires = commentaireRepository.findByUtilisateurId(userId);
        return commentaires.stream()
                .map(mapperService::fromCommentaire)
                .collect(Collectors.toList());
	}
	@Override
	public List<CommentaireDTO> getCommentairesByTicket(Long ticketId) throws TicketNotFoundException {
		 List<Commentaire> commentaires = commentaireRepository.findByTicketId(ticketId);
	        return commentaires.stream()
	                .map(mapperService::fromCommentaire)
	                .collect(Collectors.toList());
	}
	@Override
	public List<CommentaireDTO> getCommentairesByUserAndTicket(Long userId, Long ticketId)
			throws UtilisateurNotFoundException, TicketNotFoundException {
	    Utilisateur utilisateur = utilisateurRepository.findById(userId)
	            .orElseThrow(() -> new UtilisateurNotFoundException("Utilisateur non trouve !"));
	    Ticket ticket = ticketRepository.findById(ticketId)
	            .orElseThrow(() -> new TicketNotFoundException("Ticket non trouve !"));
	    
	    List<Commentaire> commentaires = commentaireRepository.findByUtilisateurAndTicket(utilisateur, ticket);
	    return commentaires.stream()
	            .map(mapperService::fromCommentaire)
	            .collect(Collectors.toList());
	}
	

}
