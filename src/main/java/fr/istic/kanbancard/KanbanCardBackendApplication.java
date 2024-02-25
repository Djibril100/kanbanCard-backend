package fr.istic.kanbancard;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.istic.kanbancard.entities.Board;
import fr.istic.kanbancard.entities.BugTicket;
import fr.istic.kanbancard.entities.Commentaire;
import fr.istic.kanbancard.entities.FeatureRequestTicket;
import fr.istic.kanbancard.entities.Section;
import fr.istic.kanbancard.entities.Tag;
import fr.istic.kanbancard.entities.Ticket;
import fr.istic.kanbancard.entities.Utilisateur;
import fr.istic.kanbancard.repositories.BoardRepository;
import fr.istic.kanbancard.repositories.CommentaireRepository;
import fr.istic.kanbancard.repositories.DeplacementTicketRepository;
import fr.istic.kanbancard.repositories.SectionRepository;
import fr.istic.kanbancard.repositories.TagRepository;
import fr.istic.kanbancard.repositories.TicketRepository;
import fr.istic.kanbancard.repositories.UtilisateurRepository;

@SpringBootApplication
public class KanbanCardBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(KanbanCardBackendApplication.class, args);
	}
	
	//la methode startFixture permet de peupler ma Base de données kanbanCard_db
	@Bean
	CommandLineRunner startFixture(BoardRepository boardRep,
								   CommentaireRepository commentaireRep,
								   DeplacementTicketRepository deplacementTicketRep,
								   SectionRepository sectionRep,
								   TagRepository tagRep,
								   TicketRepository ticketRep,
								   UtilisateurRepository utilisateurRep
								   ) {
		return args -> {
			
			String[] prenoms = {"Djibril", "Souleymane", "Fatoumata", "Mariam", "Ibrahim", "Mohamed", "Aly"};
			String[] sections = {"A faire", "En cours", "KO", "Testing", "Termine"};
			String[] couleurs = {"#FF0000", "#FFA500", "#FFFF00", "#008000"};
			
			//Creation de 7 utilisateurs
			Stream.of("BARRY", "DIALLO", "SOUMAH", "CAMARA", "BANGOURA", "BAH", "SYLLA").forEach(nom->{
				Utilisateur user = new Utilisateur();
				user.setNom(nom);
				String prenom = prenoms[new Random().nextInt(prenoms.length)];
				user.setPrenom(prenom);
				user.setEmail(prenom+"."+nom+"@gmail.com");
				utilisateurRep.save(user);
				
			});
			
			//Creation de 3 board Kanban (projet)
			Stream.of("Digital Pharma", "Digital School", "Digital kanbanCard").forEach(nom ->{
				Board board = new Board();
				board.setNom(nom);
				board.setDateCreation(new Date());
				boardRep.save(board);
			});
			//Affecter des sections aux board en parcourant mon tableau de string sections
			boardRep.findAll().forEach(board -> {
				for(int i=0; i<sections.length; i++) {
					Section section = new Section();
					String nom = sections[i];
					section.setNom(nom);
					section.setBoard(board);
					sectionRep.save(section);
				}
				
			});
			//Mise en place des tags avec des couleurs
			Stream.of("Urgent", "Nouveau", "Moyen Terme", "Long Terme").forEach(label -> {
			    Tag tag = new Tag();
			    tag.setLabel(label);
			    String couleur = couleurs[new Random().nextInt(couleurs.length)];
			    tag.setCouleur(couleur);
			    tagRep.save(tag);
			});
			//Recuperation de tous les tags
			List<Tag> tags = tagRep.findAll();
			//Recuperation de tous les utilisateurs
			List<Utilisateur> users = utilisateurRep.findAll();
			
			//Creation de 5 tickets par section pour tous les Boards
			sectionRep.findAll().forEach(section -> {
				for(int i=0; i<5; i++) {
					
					Ticket ticket = new Ticket();
					ticket.setTitre("Ticket "+i);
					ticket.setDateCreation(new Date());
					Calendar cal = Calendar.getInstance();
					// Ajout de 3 jours à new Date()
			        cal.add(Calendar.DATE, 3);
			        ticket.setDateEcheance(cal.getTime());
			        ticket.setSection(section);
			        
			        // Permet d'associer des tags aux tickets
			        Collections.shuffle(tags);
			        ticket.setTags(tags.subList(0, 1 + new Random().nextInt(tags.size() - 1)));
			        // Associer des tickets aux users
			       
			        Collections.shuffle(users);
			        // Assigner des tickets aux collaborateurs censés de travailler dessus
			        List<Utilisateur> utilisateurs = users.subList(0, 1 + new Random().nextInt(users.size() - 1));
			        ticket.setUtilisateurs(utilisateurs);
			        ticketRep.save(ticket);
			        
			       
				}
			});
			//Instanciation des commentaires pour tous les tickets
			ticketRep.findAll().forEach(ticket ->{
				Commentaire commentaire = new Commentaire();
				commentaire.setContenu("Ceci est un commentaire de base pour le "+ticket.getTitre());
				commentaire.setDateCreation(new Date());
				commentaire.setTicket(ticket);
				commentaire.setUtilisateur(users.get(new Random().nextInt(users.size())));
	            commentaireRep.save(commentaire);
			});
			
			
		};
	}

}
