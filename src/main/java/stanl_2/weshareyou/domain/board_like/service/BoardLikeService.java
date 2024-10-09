package stanl_2.weshareyou.domain.board_like.service;


import stanl_2.weshareyou.domain.board_like.aggregate.dto.BoardLikeDto;
import stanl_2.weshareyou.domain.board_like.aggregate.entity.BoardLike;

import java.util.List;


public interface BoardLikeService {

    BoardLikeDto BoardLike(BoardLikeDto boardLikeDto);
//    BoardLikeDto BoardUnLike(BoardLikeDto boardLikeDto);
    List<BoardLike> BoardLikeList(Long memberId);
}
