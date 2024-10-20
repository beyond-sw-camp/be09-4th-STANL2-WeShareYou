package stanl_2.weshareyou.domain.board_like.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

import java.util.List;

@Entity
@Table(name="BoardLike")
@RequiredArgsConstructor
@IdClass(BoardLikeId.class)
@Getter
@Setter
public class BoardLike {

    @Id
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Id
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board; // Board 객체로 매핑
}