package stanl_2.weshareyou.domain.board_comment.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stanl_2.weshareyou.domain.board.aggregate.vo.response.BoardCreateResponseVO;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.request.BoardCommentCreateRequestVO;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.request.BoardCommentUpdateRequestVO;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.response.BoardCommentCreateResponseVO;

import stanl_2.weshareyou.domain.board_comment.aggregate.dto.BoardCommentDto;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.response.BoardCommentUpdateResponseVO;
import stanl_2.weshareyou.domain.board_comment.service.BoardCommentService;
import stanl_2.weshareyou.global.common.response.ApiResponse;

@RestController(value = "boardCommentController")
@RequestMapping("/api/v1/board_comment")
@Slf4j
public class BoardCommentController {
    private final BoardCommentService boardCommentService;
    private final ModelMapper modelMappper;

    @Autowired
    public BoardCommentController(BoardCommentService boardCommentService, ModelMapper modelMappper) {
        this.boardCommentService = boardCommentService;
        this.modelMappper = modelMappper;
    }

    @PostMapping("")
    public ApiResponse<?> createBoardComment(@RequestBody BoardCommentCreateRequestVO boardCommentCreateRequestVO){

        BoardCommentDto boardCommentDto = modelMappper.map(boardCommentCreateRequestVO, BoardCommentDto.class);

        boardCommentService.createBoardComment(boardCommentDto);

        BoardCommentCreateResponseVO boardCommentCreateResponseVO =modelMappper.map(boardCommentDto,BoardCommentCreateResponseVO.class);

        return ApiResponse.ok(boardCommentCreateResponseVO);
    }

    @PutMapping("/{boardId}")
    public ApiResponse<?> updateBoardComment(@PathVariable("boardId") Long boardId ,@RequestBody BoardCommentUpdateRequestVO boardCommentUpdateRequestVO){

        BoardCommentDto boardCommentDto = modelMappper.map(boardCommentUpdateRequestVO, BoardCommentDto.class);
        boardCommentService.updateBoardComment(boardId,boardCommentDto);
        BoardCommentUpdateResponseVO boardCommentUpdateResponseVO =modelMappper.map(boardCommentDto,BoardCommentUpdateResponseVO.class);

        return ApiResponse.ok(boardCommentUpdateResponseVO);
    }


}
