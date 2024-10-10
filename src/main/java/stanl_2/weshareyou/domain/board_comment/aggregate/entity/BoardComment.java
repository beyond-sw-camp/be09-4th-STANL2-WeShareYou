package stanl_2.weshareyou.domain.board_comment.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="BOARDCOMMENT")
@RequiredArgsConstructor
@Getter
@Setter
public class BoardComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BOARD_COMMENT_ID")
    private Long id;

    @Column(name = "BOARD_CONTENT")
    @Lob
    private String content;

    @Column(name = "BOARD_CREATED_AT",columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "BOARD_UPDATED_AT")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

}
