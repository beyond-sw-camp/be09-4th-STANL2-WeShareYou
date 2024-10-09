package stanl_2.weshareyou.domain.board_like.service;


import stanl_2.weshareyou.domain.board.aggregate.entity.Board;
import stanl_2.weshareyou.domain.board_like.aggregate.dto.BoardLikeDto;

import java.util.Optional;

public interface BoardLikeService {

    BoardLikeDto BoardLike(BoardLikeDto boardLikeDto);
    BoardLikeDto BoardUnLike(BoardLikeDto boardLikeDto);
}
