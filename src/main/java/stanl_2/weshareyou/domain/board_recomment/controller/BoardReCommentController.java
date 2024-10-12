package stanl_2.weshareyou.domain.board_recomment.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import stanl_2.weshareyou.domain.alarm.service.AlarmService;
import stanl_2.weshareyou.domain.board_recomment.aggregate.dto.BoardReCommentDto;
import stanl_2.weshareyou.domain.board_recomment.aggregate.vo.request.BoardReCommentCreateRequestVO;
import stanl_2.weshareyou.domain.board_recomment.aggregate.vo.request.BoardReCommentUpdateRequestVO;
import stanl_2.weshareyou.domain.board_recomment.aggregate.vo.response.BoardReCommentCreateResponseVO;
import stanl_2.weshareyou.domain.board_recomment.aggregate.vo.response.BoardReCommentReadAllResponseVO;
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
    private final AlarmService alarmService;
    private final ModelMapper modelMappper;

    public BoardReCommentController(BoardReCommentService boardReCommentService, AlarmService alarmService, ModelMapper modelMappper) {
        this.boardReCommentService = boardReCommentService;
        this.alarmService = alarmService;
        this.modelMappper = modelMappper;
    }
    @PostMapping("")
    public ApiResponse<?> createBoardReComment(@RequestAttribute("nickname") String nickname,
                                               @RequestAttribute("id") Long id,
                                               @RequestBody BoardReCommentCreateRequestVO boardReCommentCreateRequestVO){

        BoardReCommentDto boardReCommentDto = modelMappper.map(boardReCommentCreateRequestVO, BoardReCommentDto.class);
        boardReCommentDto.setMemberId(id);
        boardReCommentDto.setNickname(nickname);
        boardReCommentService.createBoardReComment(boardReCommentDto);
        alarmService.sendRecommentAlarm(boardReCommentDto);
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
        List<BoardReCommentReadAllResponseVO> boardReCommentReadAllResponseVOs  =boardReCommentDtos.stream()
                .map(boardReCommentDto ->modelMappper.map(boardReCommentDto,BoardReCommentReadAllResponseVO.class))
                .collect(Collectors.toList());
        return ApiResponse.ok(boardReCommentReadAllResponseVOs);
    }
}
