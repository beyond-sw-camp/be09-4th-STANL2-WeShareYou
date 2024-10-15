package stanl_2.weshareyou.domain.board_image.aggregate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;

import java.util.ArrayList;

@Entity
@Table(name="BOARD_IMAGE")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class BoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BOARD_IMAGE_ID")
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @NotNull
    private Board board;

    public void setBoard(Board board) {
        this.board = board;

        // board의 imageList가 null이 아닌지 확인하고 추가
        if (board.getImageList() == null) {
            board.setImageList(new ArrayList<>());
        }

        if (!board.getImageList().contains(this)) {
            board.getImageList().add(this);
        }
    }
}
