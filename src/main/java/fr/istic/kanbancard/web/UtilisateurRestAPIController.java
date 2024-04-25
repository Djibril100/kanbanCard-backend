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

import fr.istic.kanbancard.dtos.UtilisateurDTO;
import fr.istic.kanbancard.exceptions.UtilisateurNotFoundException;
import fr.istic.kanbancard.services.UtilisateurService;

@RestController
@CrossOrigin("*")
public class UtilisateurRestAPIController {
	
	private UtilisateurService service;
	
	//L'injection des dependances ==> @Autowired | @AllArgsConstructor
	public UtilisateurRestAPIController(UtilisateurService service) {
	    this.service = service;
	}
	
	// Endpoint pour enregistrer un nouvel utilisateur
    @PostMapping("/utilisateurs")
    public UtilisateurDTO saveUtilisateur(@RequestBody UtilisateurDTO utilisateurDTO) {
        return service.saveUtilisateur(utilisateurDTO);
    }

    // Endpoint pour mettre à jour un utilisateur existant
    @PutMapping("/utilisateurs/{id}")
    public UtilisateurDTO updateUtilisateur(@PathVariable(name = "id") Long utilisateurId, @RequestBody UtilisateurDTO utilisateurDTO) {
        utilisateurDTO.setId(utilisateurId);
    	return service.updateUtilisateur(utilisateurDTO);
    }

    // Endpoint pour supprimer un utilisateur par son ID
    @DeleteMapping("/utilisateurs/{utilisateurId}")
    public void deleteUtilisateur(@PathVariable Long utilisateurId) {
        service.deleteUtilisateur(utilisateurId);
    }

    // Endpoint pour récupérer tous les utilisateurs
    @GetMapping("/utilisateurs")
    public List<UtilisateurDTO> utilisateurs() {
        return service.utilisateurs();
    }

    // Endpoint pour récupérer un utilisateur par son ID
    @GetMapping("/utilisateurs/{utilisateurId}")
    public UtilisateurDTO getUtilisateur(@PathVariable Long utilisateurId) throws UtilisateurNotFoundException {
        return service.getUtilisateur(utilisateurId);
    }

}
