package stanl_2.weshareyou.domain.board_like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.weshareyou.domain.board_like.aggregate.entity.BoardLike;
import stanl_2.weshareyou.domain.board_like.aggregate.entity.BoardLikeId;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikeId> {
}
