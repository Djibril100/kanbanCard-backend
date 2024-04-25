package fr.istic.kanbancard.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.istic.kanbancard.dtos.UtilisateurDTO;
import fr.istic.kanbancard.entities.Utilisateur;
import fr.istic.kanbancard.exceptions.UtilisateurNotFoundException;
import fr.istic.kanbancard.mappers.KanbanCardMapper;
import fr.istic.kanbancard.repositories.UtilisateurRepository;
import fr.istic.kanbancard.servicesInterface.UtilisateurServiceInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UtilisateurService implements UtilisateurServiceInterface {
	
	private UtilisateurRepository utilisateurRepository;
	private KanbanCardMapper mapperService;
	
	
	@Override
	public UtilisateurDTO saveUtilisateur(UtilisateurDTO utilisateurDTO) {
		log.info("Enregistrement d'un nouvel utilisateur");
		Utilisateur utilisateur = this.mapperService.fromUtilisateurDTO(utilisateurDTO);
		Utilisateur savedUtilisateur = this.utilisateurRepository.save(utilisateur);
		return this.mapperService.fromUtilisateur(savedUtilisateur);
	}

	@Override
	public UtilisateurDTO updateUtilisateur(UtilisateurDTO utilisateurDTO) {
		log.info("Mise à jour d'un utilisateur");
		Utilisateur utilisateur = this.mapperService.fromUtilisateurDTO(utilisateurDTO);
		Utilisateur updatedUtilisateur = this.utilisateurRepository.save(utilisateur);
		return this.mapperService.fromUtilisateur(updatedUtilisateur);
	}

	@Override
	public void deleteUtilisateur(Long utilisateurId) {
		log.info("Suppression d'un utilisateur");
		if (!utilisateurRepository.existsById(utilisateurId)) {
			throw new IllegalArgumentException("Utilisateur not found");
		}
		utilisateurRepository.deleteById(utilisateurId);
	}

	@Override
	public List<UtilisateurDTO> utilisateurs() {
		log.info("Récupération de tous les utilisateurs");
		List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
		List<UtilisateurDTO> utilisateurDTOs = utilisateurs.stream()
				.map(this.mapperService::fromUtilisateur)
				.collect(Collectors.toList());
		return utilisateurDTOs;
	}

	@Override
	public UtilisateurDTO getUtilisateur(Long utilisateurId) throws UtilisateurNotFoundException {
		log.info("Récupération de l'utilisateur avec l'ID : {}", utilisateurId);
		Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
				.orElseThrow(() -> new UtilisateurNotFoundException("Utilisateur not found"));
		return this.mapperService.fromUtilisateur(utilisateur);
	}
	
	

}
