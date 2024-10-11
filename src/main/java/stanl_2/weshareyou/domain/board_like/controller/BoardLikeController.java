package stanl_2.weshareyou.domain.board_like.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stanl_2.weshareyou.domain.alarm.service.AlarmService;
import stanl_2.weshareyou.domain.board_like.aggregate.dto.BoardLikeDto;
import stanl_2.weshareyou.domain.board_like.aggregate.entity.BoardLike;
import stanl_2.weshareyou.domain.board_like.aggregate.vo.request.BoardLikeRequestVO;
import stanl_2.weshareyou.domain.board_like.aggregate.vo.response.BoardLikeResponseVO;
import stanl_2.weshareyou.domain.board_like.service.BoardLikeService;
import stanl_2.weshareyou.domain.member.aggregate.dto.MemberDTO;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.global.common.response.ApiResponse;

import java.util.List;

@RestController(value = "boardLikeController")
@RequestMapping("api/v1/board_like")
@Slf4j
public class BoardLikeController {
    private final BoardLikeService boardLikeService;
    private final AlarmService alarmService;
    private final ModelMapper modelMapper;

    @Autowired
    public BoardLikeController(BoardLikeService boardLikeService, AlarmService alarmService, ModelMapper modelMapper) {
        this.boardLikeService = boardLikeService;
        this.alarmService = alarmService;
        this.modelMapper = modelMapper;
    }

    //좋아요
    @PostMapping
    public ApiResponse<?> like(@RequestBody BoardLikeRequestVO boardLikeRequestVO){
        BoardLikeDto boardLikeDTO = modelMapper.map(boardLikeRequestVO, BoardLikeDto.class);
        log.info("boardLikeDTO: {}", boardLikeDTO);
        BoardLikeDto boardLikeResponse = boardLikeService.BoardLike(boardLikeDTO);
        alarmService.sendLikeAlarm(boardLikeResponse);
        BoardLikeResponseVO boardLikeResponseVO = modelMapper.map(boardLikeResponse, BoardLikeResponseVO.class);
        return ApiResponse.ok(boardLikeResponseVO);
    }

    //좋아요 취소
    @DeleteMapping
    public ApiResponse<?> unlike(@RequestBody BoardLikeRequestVO boardLikeRequestVO){
        BoardLikeDto boardLikeDTO = modelMapper.map(boardLikeRequestVO, BoardLikeDto.class);
        BoardLikeDto boardLikeResponse = boardLikeService.BoardUnLike(boardLikeDTO);
        BoardLikeResponseVO boardLikeResponseVO = modelMapper.map(boardLikeResponse, BoardLikeResponseVO.class);
        return ApiResponse.ok(boardLikeResponseVO);
    }


//    @GetMapping("/boardlikelist/{boardId}")
//    public ApiResponse<?> BoardlikeList(@PathVariable Long boardId) {
//        List<Member> likeList = boardLikeService.BoardLikeList(boardId);
//        return ApiResponse.ok(likeList);
//    }

    @GetMapping("/boardlikelist/{boardId}")
    public ApiResponse<?> BoardlikeList(@PathVariable Long boardId) {
        List<Long> likeList = boardLikeService.BoardLikeList(boardId);
        return ApiResponse.ok(likeList);
    }
}
