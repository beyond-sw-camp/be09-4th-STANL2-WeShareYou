package stanl_2.weshareyou.domain.board.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.weshareyou.domain.board.aggregate.dto.BoardRequestDTO;
import stanl_2.weshareyou.domain.board.aggregate.dto.BoardResponseDTO;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;
import stanl_2.weshareyou.domain.board.aggregate.vo.response.BoardCreateResponseVO;
import stanl_2.weshareyou.domain.board.repository.BoardRepository;

@Slf4j
@Service("boardServiceImpl")
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, ModelMapper modelMapper) {
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public BoardResponseDTO createBoard(BoardRequestDTO boardRequestDTO) {

        Board board = modelMapper.map(boardRequestDTO,Board.class);

        boardRepository.save(board);

        BoardResponseDTO boardResponseDTO = modelMapper.map(board,BoardResponseDTO.class);

        return boardResponseDTO;
    }
}
