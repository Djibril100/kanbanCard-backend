package fr.istic.kanbancard.dtos;

import lombok.Data;

@Data
public class FeatureTicketDTO extends TicketDTO {
	
	private String featureRelease;
}
