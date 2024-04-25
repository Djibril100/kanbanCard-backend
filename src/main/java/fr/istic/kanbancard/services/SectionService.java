package fr.istic.kanbancard.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.istic.kanbancard.dtos.SectionDTO;
import fr.istic.kanbancard.entities.Board;
import fr.istic.kanbancard.entities.Section;
import fr.istic.kanbancard.exceptions.BoardNotFoundException;
import fr.istic.kanbancard.exceptions.SectionNotFoundException;
import fr.istic.kanbancard.mappers.KanbanCardMapper;
import fr.istic.kanbancard.repositories.BoardRepository;
import fr.istic.kanbancard.repositories.SectionRepository;
import fr.istic.kanbancard.servicesInterface.SectionServiceInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class SectionService implements SectionServiceInterface {

	private BoardRepository boardRepository;
	private SectionRepository sectionRepository;
	private KanbanCardMapper mapperService;

	@Override
	public SectionDTO saveSection(Long boardId, String nom) throws BoardNotFoundException {
		log.info("Enregistrement d'une nouvelle Section");

		Board board = boardRepository.findById(boardId).orElse(null);
		if (board == null)
			throw new BoardNotFoundException("Ce Id " + boardId + " du Board n'existe pas !");

		Section section = new Section();
		section.setNom(nom);
		section.setBoard(board);
		Section savedSection = sectionRepository.save(section);
		return this.mapperService.fromSection(savedSection);
	}

	@Override
	public SectionDTO updateSection(Long boardId, String nom, SectionDTO sectionDTO) throws BoardNotFoundException, SectionNotFoundException {
		log.info("Mise a jour d'une Section");

		Board board = boardRepository.findById(boardId).orElse(null);
		if (board == null)
			throw new BoardNotFoundException("Ce Id " + boardId + " du Board n'existe pas !");

		Section section = sectionRepository.findById(sectionDTO.getId())
	            .orElseThrow(() -> new SectionNotFoundException("La section avec l'ID " + sectionDTO.getId() + " n'a pas été trouvée"));
		section.setNom(nom);
		section.setBoard(board);
		Section updatedSection = sectionRepository.save(section);
		SectionDTO updatedSectionDTO = this.mapperService.fromSection(updatedSection);
		return updatedSectionDTO;
	}

	@Override
	public void deleteSection(Long sectionId) {
		log.info("Suppression d'une Section");
		this.sectionRepository.deleteById(sectionId);
	}

	@Override
	public List<SectionDTO> sections() {
		log.info("Recuperation de toutes les Sections");
		List<Section> sections = sectionRepository.findAll();
		return sections.stream().map(mapperService::fromSection).collect(Collectors.toList());
	}

	@Override
	public SectionDTO getSection(Long sectionId) throws SectionNotFoundException {
		log.info("Récupération d'une Section connaissant son id = " + sectionId);
		Section section = sectionRepository.findById(sectionId)
				.orElseThrow(() -> new SectionNotFoundException("Section non trouvée !"));
		return this.mapperService.fromSection(section);
	}

	@Override
	public List<SectionDTO> getSectionsByBoard(Long boardId) throws BoardNotFoundException {
		log.info("Recuperation de toutes les Sections du board Id = " + boardId);
		List<Section> sections = sectionRepository.findByBoardId(boardId);
		return sections.stream().map(mapperService::fromSection).collect(Collectors.toList());
	}

}
