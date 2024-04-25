package fr.istic.kanbancard.servicesInterface;

import java.util.List;

import fr.istic.kanbancard.dtos.TagDTO;
import fr.istic.kanbancard.exceptions.TagNotFoundException;
import fr.istic.kanbancard.exceptions.TicketNotFoundException;

public interface TagServiceInterface {
	
	
    TagDTO saveTag(String label);
    TagDTO updateTag(Long tagId, String label) throws TagNotFoundException;
    void deleteTag(Long tagId) throws TagNotFoundException;
    List<TagDTO> tags();
    TagDTO getTag(Long tagId) throws TagNotFoundException;
    List<TagDTO> getTagsByTicket(Long ticketId) throws TicketNotFoundException;
    void addTagToTicket(Long tagId, Long ticketId) throws TagNotFoundException, TicketNotFoundException;
    void removeTagFromTicket(Long tagId, Long ticketId) throws TagNotFoundException, TicketNotFoundException;

}
