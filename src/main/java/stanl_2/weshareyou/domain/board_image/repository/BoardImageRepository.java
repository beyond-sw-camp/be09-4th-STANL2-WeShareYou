package stanl_2.weshareyou.domain.board_image.repository;


import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import stanl_2.weshareyou.domain.board_image.aggregate.entity.BoardImage;

import java.util.Collection;
import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage,Long> {

    List<BoardImage> findAllByBoardId(Long id);
}
