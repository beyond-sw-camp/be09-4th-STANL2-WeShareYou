package stanl_2.weshareyou.domain.board_image.aggregate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;

@Entity
@Table(name="BOARD_IMAGE")
@NoArgsConstructor
@Getter
@Setter
public class BoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BOARD_IMAGE_ID")
    private Long id;

    @Column(name = "BOARD_IMAGE_URL")
    private String imageUrl;

//    @Column(name = "BOARD_IMAGE_NAME")
//    private String imageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Board board;

    public BoardImage(String imageUrl, Board board) {
        this.imageUrl = imageUrl;
        this.board = board;
    }
}
