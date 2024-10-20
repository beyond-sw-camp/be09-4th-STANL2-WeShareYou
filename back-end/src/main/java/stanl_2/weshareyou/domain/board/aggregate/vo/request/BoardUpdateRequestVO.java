package stanl_2.weshareyou.domain.board.aggregate.vo.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import stanl_2.weshareyou.domain.board.aggregate.entity.TAG;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardUpdateRequestVO {

    @NotNull
    private Long id;

    @NotNull(message = "수정하실 게시글의 제목을 입력하여 주세요.")
    private String title;

    @Size(min=1, max=1000, message = "1000자 이내로 입력하여 주세요.")
    private String content;

    @NotNull(message = "태그를 선택하여 주세요.")
    private TAG tag;
}
