package stanl_2.weshareyou.domain.product.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductCreateResponseVO {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String imageUrl;

    @NotNull
    private String category;

    @NotNull
    private String startAt;

    @NotNull
    private String endAt;

    @NotNull
    private Long adminId;
}
