package fr.istic.kanbancard.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class Section {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	
	@ManyToOne
	private Board board;
	@OneToMany(mappedBy = "section")
	private List<Ticket> tickets;
	
	@OneToMany(mappedBy = "fromSection")
	private List<DeplacementTicket> ticketSortants;

	@OneToMany(mappedBy = "toSection")
	private List<DeplacementTicket> ticketEntrants;

}
