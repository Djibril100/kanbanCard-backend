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

import fr.istic.kanbancard.dtos.TagDTO;
import fr.istic.kanbancard.exceptions.TagNotFoundException;
import fr.istic.kanbancard.exceptions.TicketNotFoundException;
import fr.istic.kanbancard.services.TagService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class TagRestAPIController {
	
	private TagService tagService;

    // Enregistrer un nouveau tag
    @PostMapping("/tags")
    public TagDTO saveTag(@RequestBody String label) {
        return tagService.saveTag(label);
    }

    // Mettre à jour un tag existant
    @PutMapping("/tags/{id}")
    public TagDTO updateTag(@PathVariable(name = "id") Long tagId,
                            @RequestBody String label) throws TagNotFoundException {
        return tagService.updateTag(tagId, label);
    }

    // Supprimer un tag
    @DeleteMapping("/tags/{id}")
    public void deleteTag(@PathVariable(name = "id") Long tagId) throws TagNotFoundException {
        tagService.deleteTag(tagId);
    }

    // Récupérer tous les tags
    @GetMapping("/tags")
    public List<TagDTO> tags() {
        return tagService.tags();
    }

    // Récupérer un tag par son ID
    @GetMapping("/tags/{id}")
    public TagDTO getTag(@PathVariable(name = "id") Long tagId) throws TagNotFoundException {
        return tagService.getTag(tagId);
    }

    // Récupérer les tags d'un ticket par son ID
    @GetMapping("/tickets/{id}/tags")
    public List<TagDTO> getTagsByTicket(@PathVariable(name = "id") Long ticketId) throws TicketNotFoundException {
        return tagService.getTagsByTicket(ticketId);
    }

    // Ajouter un tag à un ticket
    @PostMapping("/tickets/{ticketId}/tags/{tagId}")
    public void addTagToTicket(@PathVariable(name = "tagId") Long tagId,
                               @PathVariable(name = "ticketId") Long ticketId) throws TagNotFoundException, TicketNotFoundException {
        tagService.addTagToTicket(tagId, ticketId);
    }

    // Supprimer un tag d'un ticket
    @DeleteMapping("/tickets/{ticketId}/tags/{tagId}")
    public void removeTagFromTicket(@PathVariable(name = "tagId") Long tagId,
                                    @PathVariable(name = "ticketId") Long ticketId) throws TagNotFoundException, TicketNotFoundException {
        tagService.removeTagFromTicket(tagId, ticketId);
    }

}
