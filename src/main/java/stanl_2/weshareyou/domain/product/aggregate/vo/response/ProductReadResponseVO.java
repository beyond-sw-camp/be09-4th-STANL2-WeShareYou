package stanl_2.weshareyou.domain.product.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductReadResponseVO {

    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    private String imageUrl;

    @NotNull
    private String category;

    @NotNull
    private String startAt;

    @NotNull
    private String endAt;

    @NotNull
    private Boolean rental;

    @NotNull
    private String createdAt;

    @NotNull
    private String updatedAt;

    @NotNull
    private Long adminId;

    private Long memberId;
}
