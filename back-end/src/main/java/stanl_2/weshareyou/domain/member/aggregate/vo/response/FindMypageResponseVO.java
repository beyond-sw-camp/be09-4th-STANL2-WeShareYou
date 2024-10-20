package stanl_2.weshareyou.domain.member.aggregate.vo.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FindMypageResponseVO {

    @NotNull
    private String loginId;

    @NotNull
    private String name;

    @NotNull
    private Integer age;

    @NotNull
    private String nationality;

    @NotNull
    private String sex;

    @NotNull
    private String phone;

    @NotNull
    private Integer point;

    @NotNull
    private String role;

    @NotNull
    private Timestamp createdAt;

    @NotNull
    private Timestamp updatedAt;
}
