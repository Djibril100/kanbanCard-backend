package fr.istic.kanbancard.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.istic.kanbancard.entities.Section;

public interface SectionRepository extends JpaRepository<Section, Long> {
	
	List<Section> findByBoardId(Long boardId);

}
