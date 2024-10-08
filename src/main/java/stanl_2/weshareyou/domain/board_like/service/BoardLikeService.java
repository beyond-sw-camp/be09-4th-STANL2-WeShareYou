package stanl_2.weshareyou.domain.board_like.service;


import stanl_2.weshareyou.domain.board_like.aggregate.dto.BoardLikeDto;

public interface BoardLikeService {
    BoardLikeDto addBoardLike(BoardLikeDto boardLikeDto);

}
