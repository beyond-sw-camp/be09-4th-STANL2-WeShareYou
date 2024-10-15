package stanl_2.weshareyou.domain.board_image.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.weshareyou.domain.board_image.aggregate.entity.BoardImage;

public interface BoardImageRepository extends JpaRepository<BoardImage,Long> {

}
