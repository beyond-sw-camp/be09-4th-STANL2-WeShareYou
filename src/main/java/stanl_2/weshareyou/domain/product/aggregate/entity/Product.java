package stanl_2.weshareyou.domain.product.aggregate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

@Entity
@Table(name="Product")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "IMAGE_URL", nullable = false)
    private String imageUrl;

    @Column(name = "CATEGORY", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProuctCategory category;

    @Column(name = "START_AT", nullable = false)
    private String startAt;

    @Column(name = "END_AT", nullable = false)
    private String endAt;

    @Column(name = "RENTAL", nullable = false)
    private boolean rental;

    @Column(name = "CREATED_AT", nullable = false)
    private String createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private String updatedAt;

    @ManyToOne
    @JoinColumn(name = "ADMIN_ID", referencedColumnName = "ID")
    private Member adminId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", referencedColumnName = "ID")
    private Member memberId;

}
