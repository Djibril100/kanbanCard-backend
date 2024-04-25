package fr.istic.kanbancard.servicesInterface;

import java.util.List;

import fr.istic.kanbancard.dtos.SectionDTO;
import fr.istic.kanbancard.exceptions.BoardNotFoundException;
import fr.istic.kanbancard.exceptions.SectionNotFoundException;

public interface SectionServiceInterface {
	
	// Section operations
    SectionDTO saveSection(Long boardId, String nom) throws BoardNotFoundException;
    SectionDTO updateSection(Long boardId, String nom, SectionDTO sectionDTO)throws BoardNotFoundException, SectionNotFoundException;
    void deleteSection(Long sectionId);
    
    List<SectionDTO> sections();
    SectionDTO getSection(Long sectionId) throws SectionNotFoundException;
    List<SectionDTO> getSectionsByBoard(Long boardId) throws BoardNotFoundException;

}
