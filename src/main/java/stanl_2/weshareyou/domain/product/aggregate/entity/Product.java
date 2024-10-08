package stanl_2.weshareyou.domain.product.aggregate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

@Entity
@Table(name="PRODUCT")
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Product {
    @Id
    @Column(name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "PRODUCT_TITLE")
    private String title;

    @NotNull
    @Column(name = "PRODUCT_CONTENT", columnDefinition = "TEXT")
    private String content;

    @NotNull
    @Column(name = "PRODUCT_IMAGE_URL", columnDefinition = "TEXT")
    private String imageUrl;

    @NotNull
    @Column(name = "PRODUCT_CATEGORY")
    @Enumerated(EnumType.STRING)
    private ProuctCategory category;

    @NotNull
    @Column(name = "PRODUCT_START_AT")
    private String startAt;

    @NotNull
    @Column(name = "PRODUCT_END_AT")
    private String endAt;

    @NotNull
    @Column(name = "PRODUCT_RENTAL")
    private boolean rental;

    @NotNull
    @Column(name = "PRODUCT_CREATED_AT")
    private String createdAt;

    @NotNull
    @Column(name = "PRODUCT_UPDATED_AT")
    private String updatedAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ADMIN_ID", referencedColumnName = "MEMBER_ID")
    private Member adminId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", referencedColumnName = "MEMBER_ID")
    private Member memberId;

}
