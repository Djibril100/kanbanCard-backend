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

import fr.istic.kanbancard.dtos.SectionDTO;
import fr.istic.kanbancard.exceptions.BoardNotFoundException;
import fr.istic.kanbancard.exceptions.SectionNotFoundException;
import fr.istic.kanbancard.services.SectionService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class SectionRestAPIController {
	
	private SectionService sectionService;

	// Enregistrer une nouvelle section
    @PostMapping("/sections")
    public SectionDTO saveSection(@RequestBody SectionDTO sectionDTO) throws BoardNotFoundException {
        return sectionService.saveSection(sectionDTO.getBoardDTO().getId(), sectionDTO.getNom());
    }

    // Mettre à jour une section existante
    @PutMapping("/sections/{id}")
    public SectionDTO updateSection(@PathVariable(name = "id") Long sectionId, 
    								@RequestBody SectionDTO sectionDTO) throws BoardNotFoundException, SectionNotFoundException {
        sectionDTO.setId(sectionId);
        return sectionService.updateSection(sectionDTO.getBoardDTO().getId(), sectionDTO.getNom(), sectionDTO);
    }

    // Supprimer une section
    @DeleteMapping("/sections/{id}")
    public void deleteSection(@PathVariable(name = "id") Long sectionId) {
        sectionService.deleteSection(sectionId);
    }

    // Récupérer toutes les sections
    @GetMapping("/sections")
    public List<SectionDTO> sections() {
        return sectionService.sections();
    }

    // Récupérer une section par son ID
    @GetMapping("/sections/{id}")
    public SectionDTO getSection(@PathVariable(name = "id") Long sectionId) throws SectionNotFoundException {
        return sectionService.getSection(sectionId);
    }

    // Récupérer toutes les sections d'un board par son ID
    @GetMapping("/boards/{id}/sections")
    public List<SectionDTO> getSectionsByBoard(@PathVariable(name = "id") Long boardId) throws BoardNotFoundException {
        return sectionService.getSectionsByBoard(boardId);
    }

}
