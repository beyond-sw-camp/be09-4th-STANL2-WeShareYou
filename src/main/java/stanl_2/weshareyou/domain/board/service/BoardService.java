package stanl_2.weshareyou.domain.board.service;

import stanl_2.weshareyou.domain.board.aggregate.dto.BoardRequestDTO;
import stanl_2.weshareyou.domain.board.aggregate.dto.BoardResponseDTO;
import stanl_2.weshareyou.domain.board.aggregate.vo.response.BoardCreateResponseVO;

public interface BoardService {


    BoardResponseDTO createBoard(BoardRequestDTO boardRequestDTO);
}