package fr.istic.kanbancard.dtos;


import fr.istic.kanbancard.enums.BugStatus;
import lombok.Data;

@Data
public class BugTicketDTO extends TicketDTO {
	
	private String bugTrackingUrl;
}
