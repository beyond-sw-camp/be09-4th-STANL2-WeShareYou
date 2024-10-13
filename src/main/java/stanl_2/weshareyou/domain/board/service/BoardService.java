package stanl_2.weshareyou.domain.board.service;

import stanl_2.weshareyou.domain.board.aggregate.dto.BoardDTO;
import stanl_2.weshareyou.global.common.dto.CursorDTO;

public interface BoardService {

    BoardDTO createBoard(BoardDTO boardDTO);

    BoardDTO updateBoard(BoardDTO boardDTO);

    BoardDTO deleteBoard(BoardDTO boardDTO);

    BoardDTO readDetailBoard(BoardDTO boardDTO);

    CursorDTO readBoard(CursorDTO cursorDTO);
}