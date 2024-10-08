package stanl_2.weshareyou.domain.board.aggregate.vo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.w3c.dom.Text;
import stanl_2.weshareyou.domain.board.aggregate.entity.TAG;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardCreateRequestVO {


    @NotNull(message = "제목을 입력하여 주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해 주세요.")
    @Size(min=500, max=1000, message = "1000자 이내로 입력하여 주세요.")
    private String content;

    @NotNull
    private String imageUrl;

    @NotNull(message = "태그를 선택하여 주세요.")
    private TAG tag;

    @NotNull
    private Long memberId;

}

