package fr.istic.kanbancard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.istic.kanbancard.entities.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
