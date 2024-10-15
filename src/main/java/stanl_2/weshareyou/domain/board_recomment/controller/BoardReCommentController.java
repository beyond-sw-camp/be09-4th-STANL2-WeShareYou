package stanl_2.weshareyou.domain.board_recomment.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import stanl_2.weshareyou.domain.board_comment.aggregate.dto.BoardCommentDto;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.request.BoardCommentCreateRequestVO;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.request.BoardCommentUpdateRequestVO;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.response.BoardCommentCreateResponseVO;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.response.BoardCommentReadResponseVO;
import stanl_2.weshareyou.domain.board_comment.service.BoardCommentService;
import stanl_2.weshareyou.domain.board_recomment.aggregate.dto.BoardReCommentDto;
import stanl_2.weshareyou.domain.board_recomment.aggregate.vo.request.BoardReCommentCreateRequestVO;
import stanl_2.weshareyou.domain.board_recomment.aggregate.vo.request.BoardReCommentUpdateRequestVO;
import stanl_2.weshareyou.domain.board_recomment.aggregate.vo.response.BoardReCommentCreateResponseVO;
import stanl_2.weshareyou.domain.board_recomment.aggregate.vo.response.BoardReCommentReadResponseVO;
import stanl_2.weshareyou.domain.board_recomment.aggregate.vo.response.BoardReCommentUpdateResponseVO;
import stanl_2.weshareyou.domain.board_recomment.service.BoardReCommentService;
import stanl_2.weshareyou.global.common.response.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController(value = "boardCommentReController")
@RequestMapping("/api/v1/board-recomment")
@Slf4j
public class BoardReCommentController {
    private final BoardReCommentService boardReCommentService;
    private final ModelMapper modelMappper;

    public BoardReCommentController(BoardReCommentService boardReCommentService, ModelMapper modelMappper) {
        this.boardReCommentService = boardReCommentService;
        this.modelMappper = modelMappper;
    }
    @PostMapping("")
    public ApiResponse<?> createBoardReComment(@RequestBody BoardReCommentCreateRequestVO boardReCommentCreateRequestVO){

        BoardReCommentDto boardReCommentDto = modelMappper.map(boardReCommentCreateRequestVO, BoardReCommentDto.class);

        boardReCommentService.createBoardReComment(boardReCommentDto);

        BoardReCommentCreateResponseVO boardReCommentCreateResponseVO =modelMappper.map(boardReCommentDto,BoardReCommentCreateResponseVO.class);
        System.out.println(boardReCommentCreateResponseVO);
        return ApiResponse.ok(boardReCommentCreateResponseVO);
    }

    @PutMapping("/{boardCommentId}")
    public ApiResponse<?> updateBoardReComment(@PathVariable("boardCommentId")Long boardCommentId ,@RequestBody BoardReCommentUpdateRequestVO boardReCommentUpdateRequestVO){

        BoardReCommentDto boardReCommentDto = modelMappper.map(boardReCommentUpdateRequestVO, BoardReCommentDto.class);
        boardReCommentService.updateBoardReComment(boardCommentId,boardReCommentDto);
        BoardReCommentUpdateResponseVO boardReCommentUpdateResponseVO =modelMappper.map(boardReCommentDto,BoardReCommentUpdateResponseVO.class);

        return ApiResponse.ok(boardReCommentUpdateResponseVO);
    }
    @DeleteMapping("/{boardCommentId}")
    public ApiResponse<?> deleteBoardReComment(@PathVariable("boardCommentId") Long boardCommentId){
        boardReCommentService.deleteBoardReComment(boardCommentId);
        return ApiResponse.ok("삭제 성공");
    }

    @GetMapping("/{boardCommentId}")
    public ApiResponse<?> readReCommentsByBoardId(@PathVariable("boardCommentId") Long boardCommentId) {
        List<BoardReCommentDto> comments = boardReCommentService.readReCommentsByBoardId(boardCommentId);
        List<BoardReCommentReadResponseVO> boardReCommentReadResponseVOs = comments.stream()
                .map(comment -> modelMappper.map(comment, BoardReCommentReadResponseVO.class))
                .collect(Collectors.toList());
        return ApiResponse.ok(boardReCommentReadResponseVOs);
    }

    @GetMapping
    public ApiResponse<?> getAllBoardReComments() {
        List<BoardReCommentDto> boardReCommentDtos = boardReCommentService.readReComments();
        return ApiResponse.ok(boardReCommentDtos);
    }
}
