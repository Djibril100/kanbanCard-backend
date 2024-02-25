package fr.istic.kanbancard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.istic.kanbancard.entities.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
