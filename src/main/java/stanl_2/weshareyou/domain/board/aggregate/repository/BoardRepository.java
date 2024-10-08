package stanl_2.weshareyou.domain.board.aggregate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
