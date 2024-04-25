package fr.istic.kanbancard.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import fr.istic.kanbancard.dtos.BoardDTO;
import fr.istic.kanbancard.dtos.BugTicketDTO;
import fr.istic.kanbancard.dtos.CommentaireDTO;
import fr.istic.kanbancard.dtos.FeatureTicketDTO;
import fr.istic.kanbancard.dtos.SectionDTO;
import fr.istic.kanbancard.dtos.TagDTO;
import fr.istic.kanbancard.dtos.TicketDTO;
import fr.istic.kanbancard.dtos.UtilisateurDTO;
import fr.istic.kanbancard.entities.Board;
import fr.istic.kanbancard.entities.BugTicket;
import fr.istic.kanbancard.entities.Commentaire;
import fr.istic.kanbancard.entities.FeatureRequestTicket;
import fr.istic.kanbancard.entities.Section;
import fr.istic.kanbancard.entities.Tag;
import fr.istic.kanbancard.entities.Ticket;
import fr.istic.kanbancard.entities.Utilisateur;

@Service
public class KanbanCardMapper {

	/***
	 * Board Mapper
	 */
	public BoardDTO fromBoard(Board board) {
		BoardDTO boardDTO = new BoardDTO();
		BeanUtils.copyProperties(board, boardDTO);
		return boardDTO;
	}

	public Board fromBoardDTO(BoardDTO boardDTO) {
		Board board = new Board();
		BeanUtils.copyProperties(boardDTO, board);
		return board;
	}

	/**
	 * Section Mapper
	 */
	public SectionDTO fromSection(Section section) {
		SectionDTO sectionDTO = new SectionDTO();
		BeanUtils.copyProperties(section, sectionDTO);
		sectionDTO.setBoardDTO(fromBoard(section.getBoard()));
		return sectionDTO;
	}

	public Section fromSectionDTO(SectionDTO sectionDTO) {
		Section section = new Section();
		BeanUtils.copyProperties(sectionDTO, section);
		section.setBoard(fromBoardDTO(sectionDTO.getBoardDTO()));

		return section;
	}

	/**
	 * Utilisateur Mapper
	 */
	public UtilisateurDTO fromUtilisateur(Utilisateur utilisateur) {
		UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
		BeanUtils.copyProperties(utilisateur, utilisateurDTO);
		return utilisateurDTO;
	}

	public Utilisateur fromUtilisateurDTO(UtilisateurDTO utilisateurDTO) {
		Utilisateur utilisateur = new Utilisateur();
		BeanUtils.copyProperties(utilisateurDTO, utilisateur);
		return utilisateur;
	}

	/**
	 * Commentaire Mapper
	 */
	public CommentaireDTO fromCommentaire(Commentaire commentaire) {
		CommentaireDTO commentaireDTO = new CommentaireDTO();
		BeanUtils.copyProperties(commentaire, commentaireDTO);
		commentaireDTO.setUtilisateurDTO(fromUtilisateur(commentaire.getUtilisateur()));
		commentaireDTO.setTicketDTO(fromTicket(commentaire.getTicket()));
		return commentaireDTO;
	}

	public Commentaire fromCommentaireDTO(CommentaireDTO commentaireDTO) {
		Commentaire commentaire = new Commentaire();
		BeanUtils.copyProperties(commentaireDTO, commentaire);
		commentaire.setUtilisateur(fromUtilisateurDTO(commentaireDTO.getUtilisateurDTO()));
		commentaire.setTicket(fromTicketDTO(commentaireDTO.getTicketDTO()));
		return commentaire;
	}

	/**
	 * Ticket Mapper
	 */
	public TicketDTO fromTicket(Ticket ticket) {
		TicketDTO ticketDTO = new TicketDTO();
		BeanUtils.copyProperties(ticket, ticketDTO);
		ticketDTO.setUtilisateurDTO(fromUtilisateur(ticket.getUtilisateur()));
		ticketDTO.setSectionDTO(fromSection(ticket.getSection()));
		return ticketDTO;
	}

	public Ticket fromTicketDTO(TicketDTO ticketDTO) {
		Ticket ticket = new Ticket();
		BeanUtils.copyProperties(ticketDTO, ticket);
		ticket.setUtilisateur(fromUtilisateurDTO(ticketDTO.getUtilisateurDTO()));
		ticket.setSection(fromSectionDTO(ticketDTO.getSectionDTO()));
		return ticket;
	}

	/**
	 * BugTicket Mapper
	 */
	public BugTicketDTO fromBugTicket(BugTicket bugTicket) {
		BugTicketDTO bugTicketDTO = new BugTicketDTO();
		BeanUtils.copyProperties(bugTicket, bugTicketDTO);
		bugTicketDTO.setSectionDTO(fromSection(bugTicket.getSection()));
		bugTicketDTO.setUtilisateurDTO(fromUtilisateur(bugTicket.getUtilisateur()));
		bugTicketDTO.setType(bugTicket.getClass().getSimpleName());
		return bugTicketDTO;
	}

	public BugTicket fromBugTicketDTO(BugTicketDTO bugTicketDTO) {
		BugTicket bugTicket = new BugTicket();
		BeanUtils.copyProperties(bugTicketDTO, bugTicket);
		bugTicket.setSection(fromSectionDTO(bugTicketDTO.getSectionDTO()));
		bugTicket.setUtilisateur(fromUtilisateurDTO(bugTicketDTO.getUtilisateurDTO()));
		return bugTicket;
	}

	/**
	 * FeatureTicket Mapper
	 */
	public FeatureTicketDTO fromFeatureTicket(FeatureRequestTicket featureTicket) {
		FeatureTicketDTO featureTicketDTO = new FeatureTicketDTO();
		BeanUtils.copyProperties(featureTicket, featureTicketDTO);
		featureTicketDTO.setSectionDTO(fromSection(featureTicket.getSection()));
		featureTicketDTO.setUtilisateurDTO(fromUtilisateur(featureTicket.getUtilisateur()));
		featureTicketDTO.setType(featureTicket.getClass().getSimpleName());
		return featureTicketDTO;
	}

	public FeatureRequestTicket fromFeatureTicketDTO(FeatureTicketDTO featureTicketDTO) {
		FeatureRequestTicket featureTicket = new FeatureRequestTicket();
		BeanUtils.copyProperties(featureTicketDTO, featureTicket);
		featureTicket.setSection(fromSectionDTO(featureTicketDTO.getSectionDTO()));
		featureTicket.setUtilisateur(fromUtilisateurDTO(featureTicketDTO.getUtilisateurDTO()));
		return featureTicket;
	}

	/**
	 * Tag Mapper
	 */
	public TagDTO fromTag(Tag tag) {
		TagDTO tagDTO = new TagDTO();
		BeanUtils.copyProperties(tag, tagDTO);
		return tagDTO;
	}

	public Tag fromTagDTO(TagDTO tagDTO) {
		Tag tag = new Tag();
		BeanUtils.copyProperties(tagDTO, tag);
		return tag;
	}

}
