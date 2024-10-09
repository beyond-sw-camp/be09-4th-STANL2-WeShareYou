package stanl_2.weshareyou.domain.notice.aggregate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

@Entity
@Table(name="NOTICE")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_ID")
    private Long id;

    @Column(name = "NOTICE_TITLE")
    @NotNull
    private String title;

    @Column(name ="NOTICE_CONTENT", columnDefinition = "TEXT")
    @NotNull
    private String content;

    @Column(name="NOTICE_CREATED_AT")
    @NotNull
    private String createdAt;

    @Column(name="NOTICE_UPDATED_AT")
    @NotNull
    private String updatedAt;

    @Column(name="NOTICE_ACTIVE")
    @NotNull
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "ADMIN_ID")
    @NotNull
    private Member adminId;
}
