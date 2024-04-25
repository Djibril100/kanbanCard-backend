package fr.istic.kanbancard.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class TicketDTO {
	
	private Long id;
	private String titre;
	private String description;
	private Date dateCreation;
	private Date dateEcheance;
	private String type;
	
	private SectionDTO sectionDTO;
	private UtilisateurDTO utilisateurDTO;
}
