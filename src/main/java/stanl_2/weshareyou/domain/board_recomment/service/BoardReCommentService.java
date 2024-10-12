package stanl_2.weshareyou.domain.board_recomment.service;

import stanl_2.weshareyou.domain.board_recomment.aggregate.dto.BoardReCommentDto;

import java.util.List;

public interface BoardReCommentService {
    BoardReCommentDto createBoardReComment(BoardReCommentDto boardReCommentDto);
    BoardReCommentDto updateBoardReComment(Long boardId, BoardReCommentDto boardReCommentDto);
    void deleteBoardReComment(Long boardCommentId);
    List<BoardReCommentDto> readReCommentsByBoardId(Long boardCommentId);
    List<BoardReCommentDto> readReComments();

}
