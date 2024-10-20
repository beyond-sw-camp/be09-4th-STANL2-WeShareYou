package stanl_2.weshareyou.domain.board_comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.weshareyou.domain.board_comment.aggregate.entity.BoardComment;

import java.util.List;

public interface BoardCommentRepository extends JpaRepository<BoardComment,Long> {
    List<BoardComment> findByBoardId(Long boardId);
}
