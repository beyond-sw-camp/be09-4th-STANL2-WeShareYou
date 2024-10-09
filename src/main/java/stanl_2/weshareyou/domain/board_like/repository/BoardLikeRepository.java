package stanl_2.weshareyou.domain.board_like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;
import stanl_2.weshareyou.domain.board_like.aggregate.entity.BoardLike;
import stanl_2.weshareyou.domain.board_like.aggregate.entity.BoardLikeId;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

import java.util.List;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikeId> {

//    @Query("SELECT bl.member FROM BoardLike bl WHERE bl.board = :board")
//    List<Member> findMembersByBoard(@Param("board") Board board);
    @Query("SELECT bl.member.id FROM BoardLike bl WHERE bl.board = :board")
    List<Long> findMemberIdsByBoard(@Param("board") Board board);

}
