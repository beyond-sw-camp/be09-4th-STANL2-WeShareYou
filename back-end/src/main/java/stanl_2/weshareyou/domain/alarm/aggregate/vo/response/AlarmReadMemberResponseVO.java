package stanl_2.weshareyou.domain.alarm.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.product.aggregate.entity.ProductCategory;
import stanl_2.weshareyou.domain.product.aggregate.vo.response.ProductSummaryVO;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AlarmReadMemberResponseVO {

    @NotNull
    private Long cursorId;

    @NotNull
    private List<ProductSummaryVO> contents;

    @NotNull
    private boolean hasNext;

}