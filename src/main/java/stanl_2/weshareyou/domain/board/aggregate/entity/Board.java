package stanl_2.weshareyou.domain.board.aggregate.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="board")
public class Board {
    @Id
    @Column(name = "BOARD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BOARD_TITLE",nullable = false)
    private String title;
}
