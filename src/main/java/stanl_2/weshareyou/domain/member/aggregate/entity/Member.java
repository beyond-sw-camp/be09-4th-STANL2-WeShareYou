package stanl_2.weshareyou.domain.member.aggregate.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "MEMBER")
public class Member {
    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
