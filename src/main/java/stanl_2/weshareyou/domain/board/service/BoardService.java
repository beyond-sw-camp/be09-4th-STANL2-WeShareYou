package stanl_2.weshareyou.domain.board.service;

import org.springframework.data.domain.Pageable;
import stanl_2.weshareyou.domain.board.aggregate.dto.BoardDTO;
import stanl_2.weshareyou.domain.board.aggregate.dto.SliceDTO;
import stanl_2.weshareyou.domain.board.aggregate.entity.TAG;

public interface BoardService {

    BoardDTO createBoard(BoardDTO boardDTO);

    BoardDTO updateBoard(BoardDTO boardDTO);

    BoardDTO deleteBoard(BoardDTO boardDTO);

    SliceDTO readBoard(TAG tag, Pageable pageable);
}