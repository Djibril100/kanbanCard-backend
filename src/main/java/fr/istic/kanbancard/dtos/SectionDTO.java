package fr.istic.kanbancard.dtos;

import lombok.Data;

@Data
public class SectionDTO {
	
	private Long id;
	private String nom;
	private BoardDTO boardDTO;

}
