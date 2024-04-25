package fr.istic.kanbancard.servicesInterface;

import java.util.List;

import fr.istic.kanbancard.dtos.BoardDTO;
import fr.istic.kanbancard.exceptions.BoardNotFoundException;

public interface BoardServiceInterface {
	
	// Board operations
    BoardDTO saveBoard(BoardDTO boardDTO);
    BoardDTO updateBoard(BoardDTO board);
    void deleteBoard(Long boardId);
    
    List<BoardDTO> boards();
    BoardDTO getBoard(Long boardId) throws BoardNotFoundException;
}
