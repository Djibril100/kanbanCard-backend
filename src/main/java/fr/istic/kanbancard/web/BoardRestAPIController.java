package fr.istic.kanbancard.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.istic.kanbancard.dtos.BoardDTO;
import fr.istic.kanbancard.exceptions.BoardNotFoundException;
import fr.istic.kanbancard.services.BoardService;

@RestController
@CrossOrigin("*")
public class BoardRestAPIController {
	
	@Autowired
	private BoardService service;

    // Endpoint pour récupérer tous les boards
    @GetMapping("/boards")
    public List<BoardDTO> boards() {
        return service.boards();
    }

    // Endpoint pour récupérer un board par son ID
    @GetMapping("/boards/{id}")
    public BoardDTO getBoard(@PathVariable(name = "id") Long boardId) throws BoardNotFoundException {
        return service.getBoard(boardId);
    }

    // Endpoint pour créer un nouveau board
    @PostMapping("/boards")
    public BoardDTO saveBoard(@RequestBody BoardDTO boardDTO) {
        return service.saveBoard(boardDTO);
    }

    // Endpoint pour mettre à jour un board existant
    @PutMapping("/boards/{id}")
    public BoardDTO updateBoard(@PathVariable(name = "id") Long boardId, @RequestBody BoardDTO boardDTO) {
        boardDTO.setId(boardId);
        return service.updateBoard(boardDTO);
    }

    // Endpoint pour supprimer un board
    @DeleteMapping("/boards/{id}")
    public void deleteBoard(@PathVariable(name = "id") Long boardId) {
        service.deleteBoard(boardId);
    }

}
