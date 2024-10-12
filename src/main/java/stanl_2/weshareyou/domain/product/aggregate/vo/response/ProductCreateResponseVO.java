package stanl_2.weshareyou.domain.product.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductCreateResponseVO {

    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String imageUrl;

    @NotNull
    private String category;

    @NotNull
    private Timestamp startAt;

    @NotNull
    private Timestamp endAt;

    @NotNull
    private Long adminId;
}
