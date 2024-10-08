package stanl_2.weshareyou.domain.member.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="MEMBER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;
}
