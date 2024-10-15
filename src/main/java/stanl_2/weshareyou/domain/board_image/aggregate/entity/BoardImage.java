package stanl_2.weshareyou.domain.board_image.aggregate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;

@Entity
@Table(name="BOARD_IMAGE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BOARD_IMAGE_ID")
    private Long id;

    @Column(name="BOARD_IMAGE_FILENAME")
    private String fileName;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    @NotNull
    private Board board;
}
