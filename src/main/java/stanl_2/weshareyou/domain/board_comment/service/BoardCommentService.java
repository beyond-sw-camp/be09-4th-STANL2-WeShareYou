package stanl_2.weshareyou.domain.board_comment.service;

import stanl_2.weshareyou.domain.board_comment.aggregate.dto.BoardCommentDto;

public interface BoardCommentService {
    BoardCommentDto createBoardComment(BoardCommentDto boardCommentDto);
    BoardCommentDto updateBoardComment(Long boardId, BoardCommentDto boardCommentDto);

    void deleterBoardComment(Long boardId);
}
