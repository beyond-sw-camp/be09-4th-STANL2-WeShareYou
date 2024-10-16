package stanl_2.weshareyou.domain.board_image.service;

import java.util.List;

public interface BoardImageService {

    void deleteImagesByIds(List<Long> deletedFileIds);
}
