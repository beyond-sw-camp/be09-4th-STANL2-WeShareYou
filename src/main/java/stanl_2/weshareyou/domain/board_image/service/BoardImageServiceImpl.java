package stanl_2.weshareyou.domain.board_image.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;
import stanl_2.weshareyou.domain.board_image.aggregate.entity.BoardImage;
import stanl_2.weshareyou.domain.board_image.repository.BoardImageRepository;
import stanl_2.weshareyou.domain.s3.S3uploader;

import java.util.List;

@Service("boardImageServiceImpl")
@Slf4j
public class BoardImageServiceImpl implements BoardImageService{

    private final BoardImageRepository boardImageRepository;
    private final S3uploader s3uploader;

    @Autowired
    public BoardImageServiceImpl(BoardImageRepository boardImageRepository, S3uploader s3uploader) {
        this.boardImageRepository = boardImageRepository;
        this.s3uploader = s3uploader;
    }

    @Override
    @Transactional
    public void updateImages(List<Long> deletedFileIds) {

        List<BoardImage> imagesToDelete = boardImageRepository.findAllById(deletedFileIds);
        for (BoardImage image : imagesToDelete) {
            boardImageRepository.deleteAllByName(image.getName());
            s3uploader.deleteImg(image.getName());
        }

    }

    @Override
    @Transactional
    public void deleteImages(Board board) {

        List<BoardImage> imagesToBoardDelete = boardImageRepository.findAllByBoardId(board.getId());

        for(BoardImage image : imagesToBoardDelete) {
            s3uploader.deleteImg(image.getName());
        }

        boardImageRepository.deleteAllByBoardId(board.getId());
    }

}
