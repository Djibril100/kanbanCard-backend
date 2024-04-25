package fr.istic.kanbancard;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.istic.kanbancard.dtos.BoardDTO;
import fr.istic.kanbancard.dtos.SectionDTO;
import fr.istic.kanbancard.dtos.TagDTO;
import fr.istic.kanbancard.dtos.UtilisateurDTO;
import fr.istic.kanbancard.exceptions.BoardNotFoundException;
import fr.istic.kanbancard.exceptions.SectionOrUtilisateurNotFoundException;
import fr.istic.kanbancard.exceptions.TagNotFoundException;
import fr.istic.kanbancard.exceptions.TicketNotFoundException;
import fr.istic.kanbancard.exceptions.UtilisateurOrTicketNotFoundException;
import fr.istic.kanbancard.servicesInterface.BoardServiceInterface;
import fr.istic.kanbancard.servicesInterface.CommentaireServiceInterface;
import fr.istic.kanbancard.servicesInterface.SectionServiceInterface;
import fr.istic.kanbancard.servicesInterface.TagServiceInterface;
import fr.istic.kanbancard.servicesInterface.TicketServiceInterface;
import fr.istic.kanbancard.servicesInterface.UtilisateurServiceInterface;

@SpringBootApplication
public class KanbanCardBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(KanbanCardBackendApplication.class, args);
	}
	
	//la methode startFixture permet de peupler la Base de données kanbanCard_db
	@Bean
	CommandLineRunner startFixture(BoardServiceInterface boardServiceInt,
								   SectionServiceInterface sectionServiceInt,
								   CommentaireServiceInterface commentaireServiceInt,
								   UtilisateurServiceInterface utilisateurServiceInt,
								   TicketServiceInterface ticketServiceInt,
								   TagServiceInterface tagServiceInt
								   ) {
		return args -> {
			
			String[] prenoms = {"Djibril", "Souleymane", "Fatoumata", "Mariam", "Ibrahim", "Mohamed", "Aly"};
			String[] sections = {"A faire", "En cours", "KO", "Testing", "Termine"};
			// Liste des libellés de tags prédéfinis
	        String[] labels = {"Très Urgent", "Urgent", "Moyen Urgent", "Normal", "Classique"};
			// Liste de contenus de commentaires prédéfinis
			String[] comments = {
			    "Travail impressionnant !",
			    "Besoin de plus de détails ici.",
			    "J'attends une mise à jour.",
			    "Excellent travail, merci !",
			    "Je ne suis pas sûr de comprendre cette partie."
			};
			
			//Creation de 7 utilisateurs
			Stream.of("BARRY", "DIALLO", "SOUMAH", "CAMARA", "BANGOURA", "BAH", "SYLLA").forEach(nom->{
				UtilisateurDTO userDTO = new UtilisateurDTO();
				userDTO.setNom(nom);
				String prenom = prenoms[new Random().nextInt(prenoms.length)];
				userDTO.setPrenom(prenom);
				userDTO.setEmail(prenom+"."+nom+"@gmail.com");
				utilisateurServiceInt.saveUtilisateur(userDTO);
				
			});
			
			//Creation de 3 board Kanban (projet)
			Stream.of("Digital Pharma", "Digital School", "Digital kanbanCard").forEach(nom ->{
				BoardDTO boardDTO = new BoardDTO();
				boardDTO.setNom(nom);
				boardServiceInt.saveBoard(boardDTO);
			});
			//Affecter des sections aux board en parcourant mon tableau de string sections
			boardServiceInt.boards().forEach(board -> {
				for(int i=0; i<sections.length; i++) {
					String nom = sections[i];
					try {
						sectionServiceInt.saveSection(board.getId(), nom);
					} catch (BoardNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			//Affecter des tickets(ticket par defaut) aux sections et aux utilisateurs
			List<UtilisateurDTO> utilisateurs = utilisateurServiceInt.utilisateurs();
			List<SectionDTO> sectionsDTO = sectionServiceInt.sections();
			sectionServiceInt.sections().forEach(sectionDTO ->{
				for(UtilisateurDTO userDTO: utilisateurs) {
					try {
						ticketServiceInt.saveTicket(sectionDTO.getId(), userDTO.getId(), "Ticket " + UUID.randomUUID().toString().substring(0, 8));
					} catch (SectionOrUtilisateurNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			// Création de 10 BugTicket
			for (int i = 0; i < 10; i++) {
			    SectionDTO sectionDTO = sectionsDTO.get(new Random().nextInt(sectionsDTO.size()));
			    UtilisateurDTO utilisateurDTO = utilisateurs.get(new Random().nextInt(utilisateurs.size()));

			    try {
			        ticketServiceInt.saveBugTicket(sectionDTO.getId(), utilisateurDTO.getId(), "Bug Ticket " + UUID.randomUUID().toString().substring(0, 8), "https://example.com/bugtracking/" + UUID.randomUUID().toString().substring(0, 8));
			    } catch (SectionOrUtilisateurNotFoundException e) {
			        e.printStackTrace();
			    }
			}

			// Création de 10 FeatureTicket
			for (int i = 0; i < 10; i++) {
			    SectionDTO sectionDTO = sectionsDTO.get(new Random().nextInt(sectionsDTO.size()));
			    UtilisateurDTO utilisateurDTO = utilisateurs.get(new Random().nextInt(utilisateurs.size()));

			    try {
			        ticketServiceInt.saveFeatureTicket(sectionDTO.getId(), utilisateurDTO.getId(), "Feature Ticket " + UUID.randomUUID().toString().substring(0, 8), "Release " + UUID.randomUUID().toString().substring(0, 8));
			    } catch (SectionOrUtilisateurNotFoundException e) {
			        e.printStackTrace();
			    }
			}
			
			//Affecter des commentaires aux tickets et aux utilisateurs
			ticketServiceInt.tickets().forEach(ticketDTO ->{
				// chaque ticket aura jusqu'à 3 commentaires
				for (int i = 0; i < 3; i++) { 
			        UtilisateurDTO utilisateurDTO = utilisateurs.get(new Random().nextInt(utilisateurs.size()));
			        String contenu = comments[new Random().nextInt(comments.length)];
			        try {
						commentaireServiceInt.saveCommentaire(utilisateurDTO.getId(), ticketDTO.getId(), contenu);
					} catch (UtilisateurOrTicketNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			});
			
			// Création des tags et association de tags aux tickets
	        ticketServiceInt.tickets().forEach(ticketDTO -> {
	            // Génération d'un nombre aléatoire de tags à associer à chaque ticket (jusqu'à 3 tags)
	            int numberOfTags = new Random().nextInt(3) + 1;
	            for (int i = 0; i < numberOfTags; i++) {
	                String label = labels[new Random().nextInt(labels.length)];
	                try {
	             
	                    TagDTO tagDTO = tagServiceInt.saveTag(label);
	                    // Association un tag au ticket
	                    tagServiceInt.addTagToTicket(tagDTO.getId(), ticketDTO.getId());
	                } catch (TagNotFoundException | TicketNotFoundException e) {
	                    e.printStackTrace();
	                }
	            }
	        });
			
			
			
		};
	}

}
