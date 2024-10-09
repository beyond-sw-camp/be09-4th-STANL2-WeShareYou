package stanl_2.weshareyou.domain.board_like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.weshareyou.domain.board_like.aggregate.entity.BoardLike;
import stanl_2.weshareyou.domain.board_like.aggregate.entity.BoardLikeId;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

import java.util.List;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikeId> {

    List<BoardLike> findByMember(Member member);
}
