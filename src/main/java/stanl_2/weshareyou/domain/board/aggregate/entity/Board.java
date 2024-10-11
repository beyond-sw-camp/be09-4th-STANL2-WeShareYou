package stanl_2.weshareyou.domain.board.aggregate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.board_like.aggregate.entity.BoardLike;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "BOARD")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    @Column(name = "BOARD_TITLE")
    @NotNull
    private String title;

    @Column(name = "BOARD_CONTENT", columnDefinition = "TEXT")
    @NotNull
    private String content;

    @Column(name = "BOARD_IMAGE_URL", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "BOARD_TAG")
    @Enumerated(EnumType.STRING)
    @NotNull
    private TAG tag;

    @Column(name = "BOARD_COMMENT_COUNT")
    @NotNull
    private Integer commentCount = 0;

    @Column(name = "BOARD_LIKES_COUNT")
    @NotNull
    private Integer likesCount = 0;

    @Column(name = "BOARD_CREATED_AT")
    @NotNull
    private String createdAt;

    @Column(name = "BOARD_UPDATED_AT")
    @NotNull
    private String updatedAt;

    @Column(name = "BOARD_ACTIVE")
    @NotNull
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @NotNull
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BoardLike> boardLikes = new ArrayList<>();
}