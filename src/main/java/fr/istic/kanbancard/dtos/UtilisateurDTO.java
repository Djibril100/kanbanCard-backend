package fr.istic.kanbancard.dtos;

import lombok.Data;

@Data
public class UtilisateurDTO {
	
	private Long id;
	private String email;
	private String password;
	private String prenom;
	private String nom;

}
