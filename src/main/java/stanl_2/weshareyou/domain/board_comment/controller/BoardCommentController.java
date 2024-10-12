package stanl_2.weshareyou.domain.board_comment.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.request.BoardCommentCreateRequestVO;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.request.BoardCommentUpdateRequestVO;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.response.BoardCommentCreateResponseVO;

import stanl_2.weshareyou.domain.board_comment.aggregate.dto.BoardCommentDto;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.response.BoardCommentReadAllResponseVO;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.response.BoardCommentReadResponseVO;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.response.BoardCommentUpdateResponseVO;
import stanl_2.weshareyou.domain.board_comment.service.BoardCommentService;
import stanl_2.weshareyou.global.common.response.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController(value = "boardCommentController")
@RequestMapping("/api/v1/board-comment")
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
    public ApiResponse<?> createBoardComment(@RequestAttribute("nickname") String nickname,
                                             @RequestAttribute("id") Long id,
                                             @RequestBody BoardCommentCreateRequestVO boardCommentCreateRequestVO){
        BoardCommentDto boardCommentDto = modelMappper.map(boardCommentCreateRequestVO, BoardCommentDto.class);
        boardCommentDto.setMemberId(id);
        boardCommentDto.setNickname(nickname);
        boardCommentService.createBoardComment(boardCommentDto);
        BoardCommentCreateResponseVO boardCommentCreateResponseVO =modelMappper.map(boardCommentDto,BoardCommentCreateResponseVO.class);
        return ApiResponse.ok(boardCommentCreateResponseVO);
    }
//    @PostMapping("")
//    public ApiResponse<?> createBoardComment(@RequestBody BoardCommentCreateRequestVO boardCommentCreateRequestVO){
//
//        BoardCommentDto boardCommentDto = modelMappper.map(boardCommentCreateRequestVO, BoardCommentDto.class);
//
//        boardCommentService.createBoardComment(boardCommentDto);
//
//        BoardCommentCreateResponseVO boardCommentCreateResponseVO =modelMappper.map(boardCommentDto,BoardCommentCreateResponseVO.class);
//
//        return ApiResponse.ok(boardCommentCreateResponseVO);
//    }

    @PutMapping("/{boardId}")
    public ApiResponse<?> updateBoardComment(@PathVariable("boardId") Long boardId ,@RequestBody BoardCommentUpdateRequestVO boardCommentUpdateRequestVO){

        BoardCommentDto boardCommentDto = modelMappper.map(boardCommentUpdateRequestVO, BoardCommentDto.class);
        boardCommentService.updateBoardComment(boardId,boardCommentDto);
        BoardCommentUpdateResponseVO boardCommentUpdateResponseVO =modelMappper.map(boardCommentDto,BoardCommentUpdateResponseVO.class);

        return ApiResponse.ok(boardCommentUpdateResponseVO);
    }

    @DeleteMapping("/{boardId}")
    public ApiResponse<?> updateBoardComment(@PathVariable("boardId") Long boardId){
        boardCommentService.deleteBoardComment(boardId);
        return ApiResponse.ok("삭제 성공");
    }

    @GetMapping("/{boardId}")
    public ApiResponse<?> readCommentsByBoardId(@PathVariable("boardId") Long boardId) {
        List<BoardCommentDto> comments = boardCommentService.readCommentsByBoardId(boardId);
        List<BoardCommentReadResponseVO> boardCommentReadResponseVOs = comments.stream()
                .map(comment -> modelMappper.map(comment, BoardCommentReadResponseVO.class))
                .collect(Collectors.toList());
        return ApiResponse.ok(boardCommentReadResponseVOs);
    }


    @GetMapping
    public ApiResponse<?> getAllBoardComments() {
        List<BoardCommentDto> boardCommentDtos = boardCommentService.readComments();
        List<BoardCommentReadAllResponseVO> boardCommentReadResponseVOs = boardCommentDtos.stream()
                .map(boardCommentDto -> modelMappper.map(boardCommentDto, BoardCommentReadAllResponseVO.class))
                .collect(Collectors.toList());
        return ApiResponse.ok(boardCommentReadResponseVOs);
    }
}
