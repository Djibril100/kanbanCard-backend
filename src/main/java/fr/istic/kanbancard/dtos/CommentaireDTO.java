package fr.istic.kanbancard.dtos;

import java.util.Date;


import lombok.Data;

@Data
public class CommentaireDTO {
	
	private Long id;
	private String contenu;
	private Date dateCreation;
	
	private UtilisateurDTO utilisateurDTO;
	private TicketDTO ticketDTO;
	

}
