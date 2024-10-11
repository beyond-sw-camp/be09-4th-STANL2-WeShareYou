package stanl_2.weshareyou.domain.board_comment.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stanl_2.weshareyou.domain.alarm.service.AlarmService;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.request.BoardCommentCreateRequestVO;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.request.BoardCommentUpdateRequestVO;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.response.BoardCommentCreateResponseVO;

import stanl_2.weshareyou.domain.board_comment.aggregate.dto.BoardCommentDto;
import stanl_2.weshareyou.domain.board_comment.aggregate.vo.response.BoardCommentUpdateResponseVO;
import stanl_2.weshareyou.domain.board_comment.service.BoardCommentService;
import stanl_2.weshareyou.global.common.response.ApiResponse;

import java.util.List;

@RestController(value = "boardCommentController")
@RequestMapping("/api/v1/board-comment")
@Slf4j
public class BoardCommentController {
    private final BoardCommentService boardCommentService;
    private final AlarmService alarmService;
    private final ModelMapper modelMappper;

    @Autowired
    public BoardCommentController(BoardCommentService boardCommentService, AlarmService alarmService, ModelMapper modelMappper) {
        this.boardCommentService = boardCommentService;
        this.alarmService = alarmService;
        this.modelMappper = modelMappper;
    }

//    @PostMapping("")
//    public ApiResponse<?> createBoardComment(@RequestAttribute("nickname") String nickname,@RequestBody BoardCommentCreateRequestVO boardCommentCreateRequestVO){
//        System.out.println("1.============================================");
//        log.info("nickname : {}", nickname);
//        BoardCommentDto boardCommentDto = modelMappper.map(boardCommentCreateRequestVO, BoardCommentDto.class);
//        boardCommentDto.setMemberNickname(nickname);
//        boardCommentService.createBoardComment(boardCommentDto);
//        log.info("nickname : "+ nickname);
//        BoardCommentCreateResponseVO boardCommentCreateResponseVO =modelMappper.map(boardCommentDto,BoardCommentCreateResponseVO.class);
////      log.info(boardCommentCreateResponseVO);
//        return ApiResponse.ok(boardCommentCreateResponseVO);
//    }
    @PostMapping("")
    public ApiResponse<?> createBoardComment(@RequestBody BoardCommentCreateRequestVO boardCommentCreateRequestVO){

        BoardCommentDto boardCommentDto = modelMappper.map(boardCommentCreateRequestVO, BoardCommentDto.class);

        boardCommentService.createBoardComment(boardCommentDto);

        alarmService.sendCommentAlarm(boardCommentDto);
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

    @DeleteMapping("/{boardId}")
    public ApiResponse<?> updateBoardComment(@PathVariable("boardId") Long boardId){
        boardCommentService.deleteBoardComment(boardId);
        return ApiResponse.ok("삭제 성공");
    }

    @GetMapping("/{boardId}")
    public ApiResponse<?> readCommentsByBoardId(@PathVariable("boardId") Long boardId) {
        List<BoardCommentDto> comments = boardCommentService.readCommentsByBoardId(boardId);
        return ApiResponse.ok(comments);
    }

    @GetMapping
    public ApiResponse<?> getAllBoardComments() {
        List<BoardCommentDto> boardCommentDtos = boardCommentService.readComments();
        return ApiResponse.ok(boardCommentDtos);
    }
}
