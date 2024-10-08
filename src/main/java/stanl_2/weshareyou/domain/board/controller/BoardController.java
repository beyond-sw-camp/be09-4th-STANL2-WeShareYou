package stanl_2.weshareyou.domain.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.weshareyou.domain.board.aggregate.dto.BoardDTO;
import stanl_2.weshareyou.domain.board.aggregate.vo.request.BoardCreateRequestVO;
import stanl_2.weshareyou.domain.board.aggregate.vo.response.BoardCreateResponseVO;
import stanl_2.weshareyou.domain.board.service.BoardService;

@RestController(value = "boardController")
@RequestMapping("/api/v1/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final ModelMapper modelMapper;

    @Autowired
    public BoardController(BoardService boardService, ModelMapper modelMapper) {
        this.boardService = boardService;
        this.modelMapper = modelMapper;
    }

    /**
     * 내용: 게시글 생성
     * req: title, content, image_url, tag, member_id
     * res: X
     */
    @PostMapping("")
    public ResponseEntity<BoardCreateResponseVO> createBoard(@RequestBody BoardCreateRequestVO boardCreateRequestVO){

        BoardDTO boardDTO = modelMapper.map(boardCreateRequestVO, BoardDTO.class);

        BoardDTO boardResponseDTO = boardService.createBoard(boardDTO);

        BoardCreateResponseVO boardCreateResponseVO = modelMapper.map(boardResponseDTO, BoardCreateResponseVO.class);

        log.info("값 출력: {} ", boardCreateResponseVO);

        return ResponseEntity.ok(boardCreateResponseVO);
    }
}
