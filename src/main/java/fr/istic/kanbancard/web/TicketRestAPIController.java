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

import fr.istic.kanbancard.dtos.BugTicketDTO;
import fr.istic.kanbancard.dtos.FeatureTicketDTO;
import fr.istic.kanbancard.dtos.TicketDTO;
import fr.istic.kanbancard.exceptions.SectionNotFoundException;
import fr.istic.kanbancard.exceptions.SectionOrUtilisateurNotFoundException;
import fr.istic.kanbancard.exceptions.TicketNotFoundException;
import fr.istic.kanbancard.exceptions.UtilisateurNotFoundException;
import fr.istic.kanbancard.services.TicketService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class TicketRestAPIController {
	
	private TicketService service;
	
	// Enregistrer un nouveau ticket
    @PostMapping("/tickets")
    public TicketDTO saveTicket(@RequestBody TicketDTO ticketDTO) 
                                throws SectionOrUtilisateurNotFoundException {
        Long sectionId = ticketDTO.getSectionDTO().getId();
        Long userId = ticketDTO.getUtilisateurDTO().getId();
        String titre = ticketDTO.getTitre();
        return service.saveTicket(sectionId, userId, titre);
    }

    // Mettre à jour un ticket existant
    @PutMapping("/tickets/{id}")
    public TicketDTO updateTicket(@PathVariable(name = "id") Long ticketId,
                                  @RequestBody TicketDTO ticketDTO) 
                                  throws SectionOrUtilisateurNotFoundException, TicketNotFoundException {
        Long sectionId = ticketDTO.getSectionDTO().getId();
        Long userId = ticketDTO.getUtilisateurDTO().getId();
        String titre = ticketDTO.getTitre();
        ticketDTO.setId(ticketId);
        return service.updateTicket(ticketId, sectionId, userId, titre);
    }

    // Enregistrer un nouveau bug ticket
    @PostMapping("/tickets/bugs")
    public BugTicketDTO saveBugTicket(@RequestBody BugTicketDTO bugTicketDTO) 
                                      throws SectionOrUtilisateurNotFoundException {
        Long sectionId = bugTicketDTO.getSectionDTO().getId();
        Long userId = bugTicketDTO.getUtilisateurDTO().getId();
        String titre = bugTicketDTO.getTitre();
        String bugTrackingUrl = bugTicketDTO.getBugTrackingUrl();
        return service.saveBugTicket(sectionId, userId, titre, bugTrackingUrl);
    }

    // Enregistrer un nouveau feature ticket
    @PostMapping("/tickets/features")
    public FeatureTicketDTO saveFeatureTicket(@RequestBody FeatureTicketDTO featureTicketDTO) 
                                              throws SectionOrUtilisateurNotFoundException {
        Long sectionId = featureTicketDTO.getSectionDTO().getId();
        Long userId = featureTicketDTO.getUtilisateurDTO().getId();
        String titre = featureTicketDTO.getTitre();
        String featureRelease = featureTicketDTO.getFeatureRelease();
        return service.saveFeatureTicket(sectionId, userId, titre, featureRelease);
    }

    // Mettre à jour un bug ticket existant
    @PutMapping("/tickets/bugs/{id}")
    public BugTicketDTO updateBugTicket(@PathVariable(name = "id") Long ticketId,
                                        @RequestBody BugTicketDTO bugTicketDTO) 
                                        throws SectionOrUtilisateurNotFoundException {
        Long sectionId = bugTicketDTO.getSectionDTO().getId();
        Long userId = bugTicketDTO.getUtilisateurDTO().getId();
        String titre = bugTicketDTO.getTitre();
        String bugTrackingUrl = bugTicketDTO.getBugTrackingUrl();
        return service.updateBugTicket(ticketId, sectionId, userId, titre, bugTrackingUrl);
    }

    // Mettre à jour un feature ticket existant
    @PutMapping("/tickets/features/{id}")
    public FeatureTicketDTO updateFeatureTicket(@PathVariable(name = "id") Long ticketId,
                                                @RequestBody FeatureTicketDTO featureTicketDTO) 
                                                throws SectionOrUtilisateurNotFoundException {
        Long sectionId = featureTicketDTO.getSectionDTO().getId();
        Long userId = featureTicketDTO.getUtilisateurDTO().getId();
        String titre = featureTicketDTO.getTitre();
        String featureRelease = featureTicketDTO.getFeatureRelease();
        return service.updateFeatureTicket(ticketId, sectionId, userId, titre, featureRelease);
    }

    // Supprimer un ticket
    @DeleteMapping("/tickets/{id}")
    public void deleteTicket(@PathVariable(name = "id") Long ticketId) {
        service.deleteTicket(ticketId);
    }

    // Récupérer tous les tickets
    @GetMapping("/tickets")
    public List<TicketDTO> tickets() {
        return service.tickets();
    }

    // Récupérer un ticket par son ID
    @GetMapping("/tickets/{id}")
    public TicketDTO getTicket(@PathVariable(name = "id") Long ticketId) 
                               throws TicketNotFoundException {
        return service.getTicket(ticketId);
    }

    // Récupérer tous les tickets d'un utilisateur par son ID
    @GetMapping("/utilisateurs/{id}/tickets")
    public List<TicketDTO> getTicketsByUser(@PathVariable(name = "id") Long userId) 
                                             throws UtilisateurNotFoundException {
        return service.getTicketsByUser(userId);
    }

    // Récupérer tous les tickets d'une section par son ID
    @GetMapping("/sections/{id}/tickets")
    public List<TicketDTO> getTicketsBySection(@PathVariable(name = "id") Long sectionId) 
                                                throws SectionNotFoundException {
        return service.getTicketsBySection(sectionId);
    }
	
	

}
