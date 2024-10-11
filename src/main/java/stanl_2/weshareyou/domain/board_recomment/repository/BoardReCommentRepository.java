package stanl_2.weshareyou.domain.board_recomment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.weshareyou.domain.board_recomment.aggregate.entity.BoardReComment;

public interface BoardReCommentRepository extends JpaRepository<BoardReComment,Long> {
}
