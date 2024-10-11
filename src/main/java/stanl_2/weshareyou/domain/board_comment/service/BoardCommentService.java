package stanl_2.weshareyou.domain.board_comment.service;

import stanl_2.weshareyou.domain.board_comment.aggregate.dto.BoardCommentDto;

import java.util.List;

public interface BoardCommentService {
    BoardCommentDto createBoardComment(BoardCommentDto boardCommentDto);
    BoardCommentDto updateBoardComment(Long boardId, BoardCommentDto boardCommentDto);
    void deleteBoardComment(Long boardCommentId);
    List<BoardCommentDto> readCommentsByBoardId(Long boardId);

    List<BoardCommentDto> readComments();
}
