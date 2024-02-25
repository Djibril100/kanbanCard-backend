package fr.istic.kanbancard.entities;

import fr.istic.kanbancard.enums.BugStatus;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@DiscriminatorValue("Bugs Tickets")
public class BugTicket extends Ticket {
	
	private String bugTrackingUrl;
	@Enumerated(EnumType.STRING)
	private BugStatus status;
	
}
