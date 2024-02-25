package fr.istic.kanbancard.services;

import java.util.List;

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

public interface KanbanServiceInterface {
	
	// Board operations
    Board saveBoard(Board board);
    Board updateBoard(Board board);
    void deleteBoard(Long boardId);
    
    List<Board> boards();
    Board getBoard(Long boardId) throws BoardNotFoundException;
    
    // Section operations
    Section saveSection(Long boardId, Section section) throws BoardNotFoundException;
    Section updateSection(Section section);
    void deleteSection(Long sectionId);
    
    List<Section> sections();
    Section getSection(Long sectionId) throws SectionNotFoundException;
    List<Section> getSectionsByBoard(Long boardId) throws BoardNotFoundException;
    
    // Ticket operations
    Ticket saveTicket(Long sectionId, Ticket ticket) throws SectionNotFoundException;
    Ticket updateTicket(Ticket ticket);
    void deleteTicket(Long ticketId);
    
    List<Ticket> tickets();
    Ticket getTicket(Long ticketId) throws TicketNotFoundException;
    List<Ticket> getTicketsBySection(Long sectionId);
    List<Ticket> getTicketsByUser(Long utilisateurId);
    Ticket movedTicket(Long ticketId, Long toSectionId, Utilisateur utilisateur) throws SectionNotFoundException, TicketNotFoundException;
    void assignTicketsToUsers(Long ticketId, List<Long> utilisateurId) throws TicketNotFoundException;
   
    List<DeplacementTicket> ticketsMovedByUser(Long userId);
    
    //Ticket sous-classes operations
    BugTicket saveBugTicket(Long sectionId, Ticket ticket) throws SectionNotFoundException;
    BugTicket updateBugTicketStatus(Long ticketId, BugStatus status) throws TicketNotFoundException;
    List<BugTicket> findBugTicketsByStatus(BugStatus status);
    
    FeatureRequestTicket saveFeatureRequestTicket(Long sectionId, Ticket ticket) throws SectionNotFoundException;
    FeatureRequestTicket updateFeatureRelease(Long ticketId, String featureRelease) throws TicketNotFoundException;
    List<FeatureRequestTicket> findFeatureRequestTicketsByRelease(String featureRelease);
    
    
    // Utilisateur operations
    Utilisateur saveUtilisateur(Utilisateur utilisateur);
    Utilisateur updateUtilisateur(Utilisateur utilisateur);
    void deleteUtilisateur(Long utilisateurId);
    
    List<Utilisateur> utilisateurs();
    Utilisateur getUtilisateur(Long utilisateurId) throws UtilisateurNotFoundException;
    
    
    // Commentaire operations
    Commentaire addCommentToTicket(Long ticketId, Commentaire commentaire) throws TicketNotFoundException;
    void deleteComment(Long commentaireId);
    List<Commentaire> commentsByTicket(Long ticketId);
    
    // Tag operations
    Tag saveTag(Tag tag);
    Tag updateTag(Tag tag);
    void deleteTag(Long tagId);
    List<Tag> tags();
    Ticket assignTagToTicket(Long ticketId, Long tagId) throws TicketNotFoundException, TagNotFoundException;

}
