package stanl_2.weshareyou.domain.board_image.aggregate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;

import java.util.List;

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

    @ElementCollection
    @Column(name = "BOARD_IMAGE_URL123123")
    private List<String> imageUrl;

//    @Column(name = "BOARD_IMAGE_NAME")
//    private String imageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

}
