package fr.istic.kanbancard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.istic.kanbancard.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

}
