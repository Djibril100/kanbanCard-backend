package fr.istic.kanbancard.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.istic.kanbancard.dtos.BugTicketDTO;
import fr.istic.kanbancard.dtos.FeatureTicketDTO;
import fr.istic.kanbancard.dtos.TicketDTO;
import fr.istic.kanbancard.entities.BugTicket;
import fr.istic.kanbancard.entities.FeatureRequestTicket;
import fr.istic.kanbancard.entities.Section;
import fr.istic.kanbancard.entities.Ticket;
import fr.istic.kanbancard.entities.Utilisateur;
import fr.istic.kanbancard.exceptions.SectionNotFoundException;
import fr.istic.kanbancard.exceptions.SectionOrUtilisateurNotFoundException;
import fr.istic.kanbancard.exceptions.TicketNotFoundException;
import fr.istic.kanbancard.exceptions.UtilisateurNotFoundException;
import fr.istic.kanbancard.mappers.KanbanCardMapper;
import fr.istic.kanbancard.repositories.SectionRepository;
import fr.istic.kanbancard.repositories.TicketRepository;
import fr.istic.kanbancard.repositories.UtilisateurRepository;
import fr.istic.kanbancard.servicesInterface.TicketServiceInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TicketService implements TicketServiceInterface {
	
	private TicketRepository ticketRepository;
	private SectionRepository sectionRepository;
	private UtilisateurRepository utilisateurRepository;
	private KanbanCardMapper mapperService;
	
	@Override
	public TicketDTO saveTicket(Long sectionId, Long userId, String titre, String description)
			throws SectionOrUtilisateurNotFoundException {
		log.info("Enregistrement d'un nouveau Ticket");
	    Section section = sectionRepository.findById(sectionId).orElse(null);
	    Utilisateur user = utilisateurRepository.findById(userId).orElse(null);
	    
	    if (section == null || user == null)
	        throw new SectionOrUtilisateurNotFoundException("Section ou Utilisateur non trouves !");
	    
	    Ticket ticket = new Ticket();
	    ticket.setTitre(titre);
	    ticket.setDescription(description);
	    ticket.setDateCreation(new Date());
	    ticket.setDateEcheance(getDateEcheance(ticket.getDateCreation()));
	    
	    ticket.setSection(section);
	    ticket.setUtilisateur(user);
	    
	    Ticket savedTicket = ticketRepository.save(ticket);
	    return this.mapperService.fromTicket(savedTicket);
	}

	@Override
	public TicketDTO updateTicket(Long ticketId, Long sectionId, Long userId, String titre, String description)
			throws SectionOrUtilisateurNotFoundException, TicketNotFoundException {
		log.info("Mise à jour d'un Ticket");
	    Ticket ticket = ticketRepository.findById(ticketId)
	            .orElseThrow(() -> new TicketNotFoundException("Ticket non trouvé !"));
	    
	    Section section = sectionRepository.findById(sectionId).orElse(null);
	    Utilisateur user = utilisateurRepository.findById(userId).orElse(null);
	    
	    if (section == null || user == null)
	        throw new SectionOrUtilisateurNotFoundException("Section ou Utilisateur non trouvés !");
	    
	    ticket.setTitre(titre);
	    ticket.setDescription(description);
	    ticket.setSection(section);
	    ticket.setUtilisateur(user);
	    
	    Ticket updatedTicket = ticketRepository.save(ticket);
	    return this.mapperService.fromTicket(updatedTicket);
	}
	

	@Override
	public BugTicketDTO saveBugTicket(Long sectionId, Long userId, String titre, String description, String bugTrackingUrl)
			throws SectionOrUtilisateurNotFoundException {
		log.info("Enregistrement d'un nouveau BugTicket");
		Section section = sectionRepository.findById(sectionId).orElse(null);
		Utilisateur user = utilisateurRepository.findById(userId).orElse(null);
		
		if(section == null || user == null)
			throw new SectionOrUtilisateurNotFoundException("Section ou Utilisateur non trouves !");
		
		BugTicket bugTicket = new BugTicket();
		bugTicket.setTitre(titre);
		bugTicket.setDescription(description);
		bugTicket.setDateCreation(new Date());
		bugTicket.setDateEcheance(getDateEcheance(bugTicket.getDateCreation()));
	    
		bugTicket.setBugTrackingUrl(bugTrackingUrl);
		bugTicket.setSection(section);
		bugTicket.setUtilisateur(user);
		
		BugTicket savedBugTicket = ticketRepository.save(bugTicket);
		return this.mapperService.fromBugTicket(savedBugTicket);	
	}

	@Override
	public FeatureTicketDTO saveFeatureTicket(Long sectionId, Long userId, String titre, String description, String featureRelease)
			throws SectionOrUtilisateurNotFoundException {
		log.info("Enregistrement d'un nouveau FeatureTicket");
		Section section = sectionRepository.findById(sectionId).orElse(null);
	    Utilisateur user = utilisateurRepository.findById(userId).orElse(null);

	    if (section == null || user == null)
	        throw new SectionOrUtilisateurNotFoundException("Section ou Utilisateur non trouvés !");

	    FeatureRequestTicket featureTicket = new FeatureRequestTicket();
	    featureTicket.setTitre(titre);
	    featureTicket.setDescription(description);
	    featureTicket.setDateCreation(new Date());
	    featureTicket.setDateEcheance(getDateEcheance(featureTicket.getDateCreation()));
	    featureTicket.setSection(section);
	    featureTicket.setUtilisateur(user);
	    featureTicket.setFeatureRelease(featureRelease);

	    FeatureRequestTicket savedFeatureTicket = ticketRepository.save(featureTicket);
	    return this.mapperService.fromFeatureTicket(savedFeatureTicket);
	}

	@Override
	public BugTicketDTO updateBugTicket(Long ticketId, Long sectionId, Long userId, String titre, String description, String bugTrackingUrl)
			throws SectionOrUtilisateurNotFoundException {
		log.info("Mise à jour d'un BugTicket");
	    Section section = sectionRepository.findById(sectionId).orElse(null);
	    Utilisateur user = utilisateurRepository.findById(userId).orElse(null);
	    BugTicket bugTicket = (BugTicket) ticketRepository.findById(ticketId).orElse(null);

	    if (section == null || user == null || bugTicket == null)
	        throw new SectionOrUtilisateurNotFoundException("Section, Utilisateur ou BugTicket non trouvés !");

	    bugTicket.setTitre(titre);
	    bugTicket.setDescription(description);
	    bugTicket.setDateCreation(new Date());
	    bugTicket.setDateEcheance(getDateEcheance(bugTicket.getDateCreation()));
	    bugTicket.setBugTrackingUrl(bugTrackingUrl);
	    bugTicket.setSection(section);
	    bugTicket.setUtilisateur(user);

	    BugTicket savedBugTicket = ticketRepository.save(bugTicket);
	    return mapperService.fromBugTicket(savedBugTicket);
	}

	@Override
	public FeatureTicketDTO updateFeatureTicket(Long ticketId, Long sectionId, Long userId, String titre, String description, String featureRelease)
			throws SectionOrUtilisateurNotFoundException {
		log.info("Mise à jour d'un FeatureTicket");
	    Section section = sectionRepository.findById(sectionId).orElse(null);
	    Utilisateur user = utilisateurRepository.findById(userId).orElse(null);
	    FeatureRequestTicket featureTicket = (FeatureRequestTicket) ticketRepository.findById(ticketId).orElse(null);

	    if (section == null || user == null || featureTicket == null)
	        throw new SectionOrUtilisateurNotFoundException("Section, Utilisateur ou FeatureTicket non trouvés !");

	    featureTicket.setTitre(titre);
	    featureTicket.setDescription(description);
	    featureTicket.setDateCreation(new Date());
	    featureTicket.setDateEcheance(getDateEcheance(featureTicket.getDateCreation()));
	    featureTicket.setSection(section);
	    featureTicket.setUtilisateur(user);
	    featureTicket.setFeatureRelease(featureRelease);

	    FeatureRequestTicket savedFeatureTicket = ticketRepository.save(featureTicket);
	    return mapperService.fromFeatureTicket(savedFeatureTicket);
	}

	@Override
	public void deleteTicket(Long ticketId) {
		ticketRepository.deleteById(ticketId);
	}

	@Override
	public List<TicketDTO> tickets() {
		List<Ticket> tickets = ticketRepository.findAll();

	    List<TicketDTO> ticketDTOs = tickets.stream().map(ticket -> {
	        if (ticket instanceof BugTicket) {
	            BugTicket bugTicket = (BugTicket) ticket;
	            return mapperService.fromBugTicket(bugTicket);
	        } else if (ticket instanceof FeatureRequestTicket) {
	            FeatureRequestTicket featureRequestTicket = (FeatureRequestTicket) ticket;
	            return mapperService.fromFeatureTicket(featureRequestTicket);
	        } else {
	        	return mapperService.fromTicket(ticket);
	        }
	    }).filter(Objects::nonNull)
	      .collect(Collectors.toList());

	    return ticketDTOs;
	}

	@Override
	public TicketDTO getTicket(Long ticketId) throws TicketNotFoundException {
		Ticket ticket = ticketRepository.findById(ticketId)
	            .orElseThrow(() -> new TicketNotFoundException("Ticket non trouvé !"));

	    if (ticket instanceof BugTicket) {
	        BugTicket bugTicket = (BugTicket) ticket;
	        return mapperService.fromBugTicket(bugTicket);
	    } else if (ticket instanceof FeatureRequestTicket) {
	        FeatureRequestTicket featureRequestTicket = (FeatureRequestTicket) ticket;
	        return mapperService.fromFeatureTicket(featureRequestTicket);
	    } else {
	    	return mapperService.fromTicket(ticket);
	    }
	}

	@Override
	public List<TicketDTO> getTicketsByUser(Long userId) throws UtilisateurNotFoundException {
		Utilisateur utilisateur = utilisateurRepository.findById(userId)
	            .orElseThrow(() -> new UtilisateurNotFoundException("Utilisateur non trouvé !"));

	    List<Ticket> tickets = ticketRepository.findByUtilisateur(utilisateur);
	    List<TicketDTO> ticketDTOs = tickets.stream()
	            .map(ticket -> {
	                if (ticket instanceof BugTicket) {
	                    BugTicket bugTicket = (BugTicket) ticket;
	                    return mapperService.fromBugTicket(bugTicket);
	                } else if (ticket instanceof FeatureRequestTicket) {
	                    FeatureRequestTicket featureRequestTicket = (FeatureRequestTicket) ticket;
	                    return mapperService.fromFeatureTicket(featureRequestTicket);
	                } else {
	                    return mapperService.fromTicket(ticket);
	                }
	            })
	            .filter(Objects::nonNull)
	            .collect(Collectors.toList());

	    return ticketDTOs;
	}

	@Override
	public List<TicketDTO> getTicketsBySection(Long sectionId) throws SectionNotFoundException {
		Section section = sectionRepository.findById(sectionId)
	            .orElseThrow(() -> new SectionNotFoundException("Section non trouvée !"));

	    List<Ticket> tickets = ticketRepository.findBySection(section);
	    List<TicketDTO> ticketDTOs = tickets.stream()
	            .map(ticket -> {
	                if (ticket instanceof BugTicket) {
	                    BugTicket bugTicket = (BugTicket) ticket;
	                    return mapperService.fromBugTicket(bugTicket);
	                } else if (ticket instanceof FeatureRequestTicket) {
	                    FeatureRequestTicket featureRequestTicket = (FeatureRequestTicket) ticket;
	                    return mapperService.fromFeatureTicket(featureRequestTicket);
	                } else {
	                    return mapperService.fromTicket(ticket);
	                }
	            })
	            .filter(Objects::nonNull)
	            .collect(Collectors.toList());

	    return ticketDTOs;
	}
	
	// Méthode utilitaire pour calculer la date d'échéance
	//DateEcheance = DateCreation + 1 Mois
	private Date getDateEcheance(Date dateCreation) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(dateCreation);
	    calendar.add(Calendar.MONTH, 1); 
	    return calendar.getTime();
	}

	
	

}
