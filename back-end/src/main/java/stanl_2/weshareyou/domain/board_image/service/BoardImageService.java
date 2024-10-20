package stanl_2.weshareyou.domain.board_image.service;

import stanl_2.weshareyou.domain.board.aggregate.entity.Board;

import java.util.List;

public interface BoardImageService {

    void updateImages(List<Long> deletedFileIds);

    void deleteImages(Board board);
}
