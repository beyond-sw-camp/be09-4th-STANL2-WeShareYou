package stanl_2.weshareyou.domain.board_like.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;

@Entity
@Table(name="boardLike")
@RequiredArgsConstructor
@IdClass(BoardLikeId.class)
@Getter
@Setter
public class BoardLike {

    @Id
    @Column(name ="MEMBER_ID")
    private Long memberId;

//    @Id
//    @ManyToOne
//    @JoinColumn(name = "MEMBER_ID", referencedColumnName = "MEMBER_ID")
//    private Member member; // Member 객체로 매핑

    @Id
    @ManyToOne
    @JoinColumn(name = "BOARD_ID", referencedColumnName = "BOARD_ID")
    private Board boardId; // Board 객체로 매핑


//
//    @ManyToOne
//    @JoinColumn(name = "MEMBER_ID", referencedColumnName = "Member_ID") // 수정
//    private Member member;
}