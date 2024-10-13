package stanl_2.weshareyou.domain.board.aggregate.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.weshareyou.domain.board.aggregate.entity.TAG;
import stanl_2.weshareyou.domain.board_comment.aggregate.dto.BoardCommentDto;


import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private TAG tag;
    private Integer commentCount;
    private Integer likesCount;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Boolean active;
    private MultipartFile file;

    private Long memberId;
    private String memberProfileUrl;
    private String memberNickname;

    private List<BoardCommentDto> comment;

}
