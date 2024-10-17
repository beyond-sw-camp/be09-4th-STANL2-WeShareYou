package stanl_2.weshareyou.domain.board_image.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.weshareyou.domain.board_image.aggregate.entity.BoardImage;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage,Long> {

    List<BoardImage> findAllByBoardId(Long boardId);

    @Modifying
    @Transactional
    @Query("DELETE FROM BoardImage b WHERE b.name = :name")
    void deleteAllByName(String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM BoardImage b WHERE b.board.id = :boardId")
    void deleteAllByBoardId(@Param("boardId") Long boardId);
}
