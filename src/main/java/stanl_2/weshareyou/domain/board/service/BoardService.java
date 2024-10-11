package stanl_2.weshareyou.domain.board.service;

import stanl_2.weshareyou.domain.board.aggregate.dto.BoardDTO;

public interface BoardService {

    BoardDTO createBoard(BoardDTO boardDTO);

    BoardDTO updateBoard(BoardDTO boardDTO);

    BoardDTO deleteBoard(BoardDTO boardDTO);

    BoardDTO readDetailBoard(BoardDTO boardDTO);
}