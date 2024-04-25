package fr.istic.kanbancard.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.istic.kanbancard.dtos.TagDTO;
import fr.istic.kanbancard.entities.Tag;
import fr.istic.kanbancard.entities.Ticket;
import fr.istic.kanbancard.exceptions.TagNotFoundException;
import fr.istic.kanbancard.exceptions.TicketNotFoundException;
import fr.istic.kanbancard.mappers.KanbanCardMapper;
import fr.istic.kanbancard.repositories.TagRepository;
import fr.istic.kanbancard.repositories.TicketRepository;
import fr.istic.kanbancard.servicesInterface.TagServiceInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class TagService implements TagServiceInterface {

	private TagRepository tagRepository;
    private TicketRepository ticketRepository;
    private KanbanCardMapper mapperService;
    
    
	@Override
	public TagDTO saveTag(String label) {
		log.info("Enregistrement d'un nouveau tag");
        Tag tag = new Tag();
        tag.setLabel(label);
        Tag savedTag = tagRepository.save(tag);
        return mapperService.fromTag(savedTag);
	}
	@Override
	public TagDTO updateTag(Long tagId, String label) throws TagNotFoundException {
		log.info("Mise à jour d'un tag");
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagNotFoundException("Tag non trouvé avec l'ID " + tagId));
        tag.setLabel(label);
        Tag updatedTag = tagRepository.save(tag);
        return mapperService.fromTag(updatedTag);
	}
	@Override
	public void deleteTag(Long tagId) throws TagNotFoundException {
		log.info("Suppression d'un tag");
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagNotFoundException("Tag non trouvé avec l'ID " + tagId));
        tagRepository.delete(tag);
		
	}
	@Override
	public List<TagDTO> tags() {
		log.info("Récupération de tous les tags");
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(mapperService::fromTag)
                .collect(Collectors.toList());
	}
	@Override
	public TagDTO getTag(Long tagId) throws TagNotFoundException {
		log.info("Récupération d'un tag par son ID");
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagNotFoundException("Tag non trouvé avec l'ID " + tagId));
        return mapperService.fromTag(tag);
	}
	@Override
	public List<TagDTO> getTagsByTicket(Long ticketId) throws TicketNotFoundException {
		log.info("Récupération des tags par ticket");
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket non trouvé avec l'ID " + ticketId));
        List<Tag> tags = ticket.getTags();
        return tags.stream()
                .map(mapperService::fromTag)
                .collect(Collectors.toList());
	}
	@Override
	public void addTagToTicket(Long tagId, Long ticketId) throws TagNotFoundException, TicketNotFoundException {
		log.info("Ajout d'un tag à un ticket");
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagNotFoundException("Tag non trouvé avec l'ID " + tagId));
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket non trouvé avec l'ID " + ticketId));
        ticket.getTags().add(tag);
        ticketRepository.save(ticket);
		
	}
	@Override
	public void removeTagFromTicket(Long tagId, Long ticketId) throws TagNotFoundException, TicketNotFoundException {
		log.info("Suppression d'un tag d'un ticket");
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagNotFoundException("Tag non trouvé avec l'ID " + tagId));
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket non trouvé avec l'ID " + ticketId));
        ticket.getTags().remove(tag);
        ticketRepository.save(ticket);
		
	}
    
    
}
