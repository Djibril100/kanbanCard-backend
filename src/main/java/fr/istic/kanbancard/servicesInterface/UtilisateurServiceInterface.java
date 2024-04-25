package fr.istic.kanbancard.servicesInterface;

import java.util.List;

import fr.istic.kanbancard.dtos.UtilisateurDTO;
import fr.istic.kanbancard.exceptions.UtilisateurNotFoundException;

public interface UtilisateurServiceInterface {
	
	UtilisateurDTO saveUtilisateur(UtilisateurDTO utilisateurDTO);
	UtilisateurDTO updateUtilisateur(UtilisateurDTO utilisateurDTO);
	void deleteUtilisateur(Long utilisateurId);
	
	List<UtilisateurDTO> utilisateurs();
	UtilisateurDTO getUtilisateur(Long utilisateurId) throws UtilisateurNotFoundException;
	

}
