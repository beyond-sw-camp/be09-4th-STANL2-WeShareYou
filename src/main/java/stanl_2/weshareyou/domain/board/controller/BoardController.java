package stanl_2.weshareyou.domain.board.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.weshareyou.domain.board.aggregate.dto.BoardDTO;
import stanl_2.weshareyou.domain.board.aggregate.vo.request.BoardCreateRequestVO;
import stanl_2.weshareyou.domain.board.aggregate.vo.request.BoardUpdateRequestVO;
import stanl_2.weshareyou.domain.board.aggregate.vo.response.BoardCreateResponseVO;
import stanl_2.weshareyou.domain.board.aggregate.vo.response.BoardUpdateResponseVO;
import stanl_2.weshareyou.domain.board.service.BoardService;
import stanl_2.weshareyou.global.common.response.ApiResponse;

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
     * 내용: 게시글 생성2
     * req:
     * {
     *     "title": "가이드 해드립니다.",
     *     "content": "안녕하세요, 가이드 5년차 홍길동입니다. 가이드가 필요하신분 연락주세요",
     *     "imageUrl": "image1",
     *     "tag": "GUIDE",
     *     "memberId": 1
     * }
     * res:
     * {
     *     "success": true,
     *     "result": {
     *         "title": "가이드 해드립니다25.",
     *         "content": "안녕",
     *         "imageUrl": "image1",
     *         "tag": "GUIDE",
     *         "memberId": 1
     *     },
     *     "error": null
     * }
     */
    @PostMapping("")
    public ApiResponse<?> createBoard(@Validated @RequestBody BoardCreateRequestVO boardCreateRequestVO){

        BoardDTO boardDTO = modelMapper.map(boardCreateRequestVO, BoardDTO.class);

        BoardDTO boardResponseDTO = boardService.createBoard(boardDTO);

        BoardCreateResponseVO boardCreateResponseVO = modelMapper.map(boardResponseDTO, BoardCreateResponseVO.class);

        return ApiResponse.ok(boardCreateResponseVO);
    }

    /**
     * 내용: 게시글 내용 수정
     * req:
     * res:
     */
    @PostMapping("/update")
    public ApiResponse<?> updateBoard(@RequestBody BoardUpdateRequestVO boardUpdateRequestVO) {

        BoardDTO boardDTO = modelMapper.map(boardUpdateRequestVO, BoardDTO.class);

        BoardDTO boardResponseDTO = boardService.updateBoard(boardDTO);

        BoardUpdateResponseVO boardUpdateResponseVO = modelMapper.map(boardResponseDTO, BoardUpdateResponseVO.class);

        return ApiResponse.ok(boardUpdateResponseVO);
    }
}
