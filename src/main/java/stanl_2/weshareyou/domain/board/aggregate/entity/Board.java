package stanl_2.weshareyou.domain.board.aggregate.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
