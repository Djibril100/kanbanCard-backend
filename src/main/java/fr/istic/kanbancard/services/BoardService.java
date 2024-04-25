package fr.istic.kanbancard.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.istic.kanbancard.dtos.BoardDTO;
import fr.istic.kanbancard.entities.Board;
import fr.istic.kanbancard.exceptions.BoardNotFoundException;
import fr.istic.kanbancard.mappers.KanbanCardMapper;
import fr.istic.kanbancard.repositories.BoardRepository;
import fr.istic.kanbancard.servicesInterface.BoardServiceInterface;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BoardService implements BoardServiceInterface {
	
	private BoardRepository boardRepository;
	private KanbanCardMapper mapperService;

	@Override
	public BoardDTO saveBoard(BoardDTO boardDTO) {
		log.info("Enregistrement d'un nouveau Board");
		Board board = this.mapperService.fromBoardDTO(boardDTO);
		Board savedBoard = this.boardRepository.save(board);
		return this.mapperService.fromBoard(savedBoard);
	}

	@Override
	public BoardDTO updateBoard(BoardDTO boardDTO) {
		log.info("Mise a jour d'un Board");
		Board board = this.mapperService.fromBoardDTO(boardDTO);
		Board updatedBoard = this.boardRepository.save(board);
		
		return this.mapperService.fromBoard(updatedBoard);
	}

	@Override
	public void deleteBoard(Long boardId) {
		 log.info("Suppression d'un Board");
		    if (!boardRepository.existsById(boardId)) {
		        throw new IllegalArgumentException("Board not found");
		    }
		    boardRepository.deleteById(boardId);	
	}

	@Override
	public List<BoardDTO> boards() {
		 log.info("Récupération de tous les Boards");
		  
		    List<Board> boards = boardRepository.findAll();
		    List<BoardDTO> boardDTOs = boards.stream()
		            .map(this.mapperService::fromBoard)
		            .collect(Collectors.toList());
		    return boardDTOs;
	}

	@Override
	public BoardDTO getBoard(Long boardId) throws BoardNotFoundException {
		log.info("Récupération du board avec l'ID : {}", boardId);
	    Board board = boardRepository.findById(boardId)
	            .orElseThrow(() -> new BoardNotFoundException("Board non trouvé !"));
	    return mapperService.fromBoard(board);
	}
	
	/*public List<BoardDTO> boards() {
		 log.info("Récupération de tous les Boards");
		  
		    List<Board> boards = boardRepository.findAll();
		    List<BoardDTO> boardDTOs = boards.stream()
		            .map(board -> this.mapperService.fromBoard(board)).collect(Collectors.toList());
		    return boardDTOs;
	}*/
	
	

}
