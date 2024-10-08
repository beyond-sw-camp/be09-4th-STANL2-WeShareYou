package stanl_2.weshareyou.domain.board.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.weshareyou.domain.board.aggregate.dto.BoardDTO;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;
import stanl_2.weshareyou.domain.board.repository.BoardRepository;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service("boardServiceImpl")
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMAT);

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, ModelMapper modelMapper) {
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public BoardDTO createBoard(BoardDTO boardDTO) {
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Board board = modelMapper.map(boardDTO,Board.class);
        board.setCommentCount(0);
        board.setLikesCount(0);
        board.setCreatedAt(LocalDateTime.now().format(FORMATTER));
        board.setUpdatedAt(LocalDateTime.now().format(FORMATTER));
        board.setActive(true);

        boardRepository.save(board);

        BoardDTO boardResonseDTO = modelMapper.map(board, BoardDTO.class);

        return boardResonseDTO;
    }
}
