package stanl_2.weshareyou.domain.board_comment.service;

import stanl_2.weshareyou.domain.board_comment.aggregate.dto.BoardCommentDto;
import stanl_2.weshareyou.domain.board_comment.aggregate.entity.BoardComment;

import java.util.Optional;

public interface BoardCommentService {
    BoardCommentDto createBoardComment(BoardCommentDto boardCommentDto);
    BoardCommentDto updateBoardComment(Long boardId, BoardCommentDto boardCommentDto);
    void deleteBoardComment(Long boardCommentId);

    Optional<BoardComment> readCommentById(Long boardCommentId);

}
