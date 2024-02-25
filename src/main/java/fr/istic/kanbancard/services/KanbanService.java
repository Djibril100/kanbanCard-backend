package fr.istic.kanbancard.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.istic.kanbancard.entities.Board;
import fr.istic.kanbancard.entities.BugTicket;
import fr.istic.kanbancard.entities.Commentaire;
import fr.istic.kanbancard.entities.DeplacementTicket;
import fr.istic.kanbancard.entities.FeatureRequestTicket;
import fr.istic.kanbancard.entities.Section;
import fr.istic.kanbancard.entities.Tag;
import fr.istic.kanbancard.entities.Ticket;
import fr.istic.kanbancard.entities.Utilisateur;
import fr.istic.kanbancard.enums.BugStatus;
import fr.istic.kanbancard.exceptions.BoardNotFoundException;
import fr.istic.kanbancard.exceptions.SectionNotFoundException;
import fr.istic.kanbancard.exceptions.TagNotFoundException;
import fr.istic.kanbancard.exceptions.TicketNotFoundException;
import fr.istic.kanbancard.exceptions.UtilisateurNotFoundException;
import fr.istic.kanbancard.repositories.BoardRepository;
import fr.istic.kanbancard.repositories.CommentaireRepository;
import fr.istic.kanbancard.repositories.DeplacementTicketRepository;
import fr.istic.kanbancard.repositories.SectionRepository;
import fr.istic.kanbancard.repositories.TagRepository;
import fr.istic.kanbancard.repositories.TagRepository;
import fr.istic.kanbancard.repositories.TicketRepository;
import fr.istic.kanbancard.repositories.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class KanbanService implements KanbanServiceInterface {
	
	private BoardRepository boardRepository;
	private SectionRepository sectionRepository;
	private TicketRepository ticketRepository;
	private UtilisateurRepository utilisateurRepository;
	private DeplacementTicketRepository deplacementTicketRepository;
	private CommentaireRepository commentaireRepository;
	private TagRepository tagRepository;

	@Override
	public Board saveBoard(Board board) {
		return boardRepository.save(board);
	}

	@Override
	public Board updateBoard(Board board) {
		if (board.getId() != null && boardRepository.existsById(board.getId())) {
            return boardRepository.save(board);
        } else {
            throw new IllegalArgumentException("Board must exist to be updated");
        }
	}

	@Override
	public void deleteBoard(Long boardId) {
		
		 boardRepository.deleteById(boardId);
	}

	@Override
	public List<Board> boards() {
		return boardRepository.findAll();
	}

	@Override
	public Board getBoard(Long boardId) throws BoardNotFoundException {
		return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("Board not found with ID: " + boardId));
	}

	@Override
	public Section saveSection(Long boardId, Section section) throws BoardNotFoundException {
		Board board = boardRepository.findById(boardId)
	            .orElseThrow(() -> new BoardNotFoundException("Board not found with ID: " + boardId));
	    section.setBoard(board);
	    return sectionRepository.save(section);
	}

	@Override
	public Section updateSection(Section section) {
		if (section.getId() == null || !sectionRepository.existsById(section.getId())) {
	        throw new IllegalArgumentException("Section must exist to be updated");
	    }
	    return sectionRepository.save(section);
	}

	@Override
	public void deleteSection(Long sectionId) {
		sectionRepository.deleteById(sectionId);
		
	}

	@Override
	public List<Section> sections() {
		return sectionRepository.findAll();
	}

	@Override
	public Section getSection(Long sectionId) throws SectionNotFoundException {
		return sectionRepository.findById(sectionId)
	            .orElseThrow(() -> new SectionNotFoundException("Section not found with ID: " + sectionId));
	}

	@Override
	public List<Section> getSectionsByBoard(Long boardId) throws BoardNotFoundException {
		if(!boardRepository.existsById(boardId)) {
	        throw new BoardNotFoundException("Board not found with ID: " + boardId);
	    }
	    return sectionRepository.findByBoardId(boardId);
	}

	@Override
	public Ticket saveTicket(Long sectionId, Ticket ticket) throws SectionNotFoundException {
		Section section = sectionRepository.findById(sectionId)
	            .orElseThrow(() -> new SectionNotFoundException("Section not found with ID: " + sectionId));
	    ticket.setSection(section);
	    return ticketRepository.save(ticket);
	}

	@Override
	public Ticket updateTicket(Ticket ticket) {
		 if (ticket.getId() == null || !ticketRepository.existsById(ticket.getId())) {
		        throw new IllegalArgumentException("Ticket must exist to be updated");
		    }
		    return ticketRepository.save(ticket);
	}

	@Override
	public void deleteTicket(Long ticketId) {
		ticketRepository.deleteById(ticketId);
		
	}

	@Override
	public List<Ticket> tickets() {
		return ticketRepository.findAll();
	}

	@Override
	public Ticket getTicket(Long ticketId) throws TicketNotFoundException {
		return ticketRepository.findById(ticketId)
	            .orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));
	}

	@Override
	public List<Ticket> getTicketsBySection(Long sectionId) {
		return ticketRepository.findBySectionId(sectionId);
	}

	@Override
	public List<Ticket> getTicketsByUser(Long utilisateurId) {
		return ticketRepository.findTicketsByUtilisateurId(utilisateurId);
	}

	@Override
	public Ticket movedTicket(Long ticketId, Long toSectionId, Utilisateur utilisateur)
			throws SectionNotFoundException, TicketNotFoundException {
		Ticket ticket = ticketRepository.findById(ticketId)
	            .orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));
	    Section toSection = sectionRepository.findById(toSectionId)
	            .orElseThrow(() -> new SectionNotFoundException("Section not found with ID: " + toSectionId));
	    //Section fromSection = ticket.getSection(); // Get current section of the ticket

	    ticket.setSection(toSection); 
	    Ticket updatedTicket = ticketRepository.save(ticket);
	    
	    return updatedTicket;
	}

	@Override
	public void assignTicketsToUsers(Long ticketId, List<Long> utilisateurId) throws TicketNotFoundException {
		Ticket ticket = getTicket(ticketId);
	    List<Utilisateur> utilisateurs = utilisateurRepository.findAllById(utilisateurId);
	    ticket.setUtilisateurs(utilisateurs);
	    ticketRepository.save(ticket);
		
	}

	@Override
	public List<DeplacementTicket> ticketsMovedByUser(Long userId) {
		 return deplacementTicketRepository.findByUtilisateurId(userId);
	}

	@Override
	public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
		return utilisateurRepository.save(utilisateur);
	}

	@Override
	public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
		if (utilisateur.getId() == null || !utilisateurRepository.existsById(utilisateur.getId())) {
	        throw new IllegalArgumentException("Utilisateur must exist to be updated");
	    }
	    return utilisateurRepository.save(utilisateur);
	}

	@Override
	public void deleteUtilisateur(Long utilisateurId) {
		utilisateurRepository.deleteById(utilisateurId);
		
	}

	@Override
	public List<Utilisateur> utilisateurs() {
		return utilisateurRepository.findAll();
	}

	@Override
	public Utilisateur getUtilisateur(Long utilisateurId) throws UtilisateurNotFoundException {
		return utilisateurRepository.findById(utilisateurId)
	            .orElseThrow(() -> new UtilisateurNotFoundException("Utilisateur not found with ID: " + utilisateurId));
	}

	@Override
	public Commentaire addCommentToTicket(Long ticketId, Commentaire commentaire) throws TicketNotFoundException {
		Ticket ticket = ticketRepository.findById(ticketId)
	            .orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));
	    commentaire.setTicket(ticket);
	    return commentaireRepository.save(commentaire);
	}

	@Override
	public void deleteComment(Long commentaireId) {
		commentaireRepository.deleteById(commentaireId);
		
	}

	@Override
	public List<Commentaire> commentsByTicket(Long ticketId) {
		return commentaireRepository.findByTicketId(ticketId);
	}

	@Override
	public Tag saveTag(Tag tag) {
		return tagRepository.save(tag);
	}

	@Override
	public Tag updateTag(Tag tag) {
		if (tag.getId() == null || !tagRepository.existsById(tag.getId())) {
	        throw new IllegalArgumentException("Tag must exist to be updated");
	    }
	    return tagRepository.save(tag);
	}

	@Override
	public void deleteTag(Long tagId) {
		tagRepository.deleteById(tagId);
	}

	@Override
	public List<Tag> tags() {
		return tagRepository.findAll();
	}

	@Override
	public Ticket assignTagToTicket(Long ticketId, Long tagId) throws TicketNotFoundException, TagNotFoundException {
		Ticket ticket = ticketRepository.findById(ticketId)
	            .orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));
	    Tag tag = tagRepository.findById(tagId)
	            .orElseThrow(() -> new TagNotFoundException("Tag not found with ID: " + tagId));
	    ticket.getTags().add(tag);
	    return ticketRepository.save(ticket);
	}

	@Override
	public BugTicket saveBugTicket(Long sectionId, Ticket ticket) throws SectionNotFoundException {
		Section section = sectionRepository.findById(sectionId)
	            .orElseThrow(() -> new SectionNotFoundException("Section not found with ID: " + sectionId));
	    ticket.setSection(section);
	    return (BugTicket) ticketRepository.save(ticket);
	}

	@Override
	public BugTicket updateBugTicketStatus(Long ticketId, BugStatus status) throws TicketNotFoundException {
		Ticket ticket = ticketRepository.findById(ticketId)
	            .orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));
	    BugTicket bugTicket = (BugTicket) ticket;
	    bugTicket.setStatus(status);
	    return (BugTicket) ticketRepository.save(bugTicket);
	}

	@Override
	public List<BugTicket> findBugTicketsByStatus(BugStatus status) {
		return ticketRepository.findBugTicketsByStatus(status);
	}

	@Override
	public FeatureRequestTicket saveFeatureRequestTicket(Long sectionId, Ticket ticket)
			throws SectionNotFoundException {
		Section section = sectionRepository.findById(sectionId)
	            .orElseThrow(() -> new SectionNotFoundException("Section not found with ID: " + sectionId));
	    ticket.setSection(section);
	    return (FeatureRequestTicket) ticketRepository.save(ticket);
	}

	@Override
	public FeatureRequestTicket updateFeatureRelease(Long ticketId, String featureRelease)
			throws TicketNotFoundException {
		Ticket ticket = ticketRepository.findById(ticketId)
	            .orElseThrow(() -> new TicketNotFoundException("Ticket not found with ID: " + ticketId));
	    FeatureRequestTicket featureRequestTicket = (FeatureRequestTicket) ticket;
	    featureRequestTicket.setFeatureRelease(featureRelease);
	    return (FeatureRequestTicket) ticketRepository.save(featureRequestTicket);
	}

	@Override
	public List<FeatureRequestTicket> findFeatureRequestTicketsByRelease(String featureRelease) {
		return ticketRepository.findFeatureRequestTicketsByRelease(featureRelease);
	}

	

}
