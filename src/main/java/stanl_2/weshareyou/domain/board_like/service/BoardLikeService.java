package stanl_2.weshareyou.domain.board_like.service;


import stanl_2.weshareyou.domain.board_like.aggregate.dto.BoardLikeDto;


import java.util.List;


public interface BoardLikeService {

    BoardLikeDto BoardLike(BoardLikeDto boardLikeDto);
    BoardLikeDto BoardUnLike(BoardLikeDto boardLikeDto);
    List<Long> BoardLikeList(Long boardId);

}
