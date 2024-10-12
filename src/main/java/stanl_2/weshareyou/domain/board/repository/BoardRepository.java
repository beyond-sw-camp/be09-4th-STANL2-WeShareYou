package stanl_2.weshareyou.domain.board.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;
import stanl_2.weshareyou.domain.board.aggregate.entity.TAG;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Slice<Board> findByTagOrderByCreatedAtDesc(TAG tag, Pageable pageable);

    Slice<Board> findByTagAndIdLessThanOrderByCreatedAtDesc(TAG tag, Long cursorId, Pageable pageable);
}
