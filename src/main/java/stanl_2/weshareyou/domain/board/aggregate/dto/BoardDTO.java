package stanl_2.weshareyou.domain.board.aggregate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stanl_2.weshareyou.domain.board.aggregate.entity.TAG;
import stanl_2.weshareyou.domain.board.aggregate.vo.BoardLikeVO;
import stanl_2.weshareyou.domain.board.aggregate.vo.CommentVO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long id;
    private String title;
    private String context;
    private String imageUrl;
    private TAG tag;
    private Integer commentCount;
    private Integer likesCount;
    private String createdAt;
    private String updatedAt;
    private Boolean active;
    private Long memberId;

    private CommentVO comment;
    private BoardLikeVO boardLike;

}
