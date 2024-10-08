package stanl_2.weshareyou.domain.board_like.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stanl_2.weshareyou.domain.board_like.aggregate.dto.BoardLikeDto;
import stanl_2.weshareyou.domain.board_like.aggregate.vo.request.BoardLikeRequestVO;
import stanl_2.weshareyou.domain.board_like.aggregate.vo.response.BoardLikeResponseVO;
import stanl_2.weshareyou.domain.board_like.service.BoardLikeService;
import stanl_2.weshareyou.global.common.response.ApiResponse;

@RestController(value = "boardLikeController")
@RequestMapping("api/v1/board_like")
public class BoardLikeController {
    private final BoardLikeService boardLikeService;
    private final ModelMapper modelMapper;

    @Autowired
    public BoardLikeController(BoardLikeService boardLikeService,ModelMapper modelMapper) {
        this.boardLikeService = boardLikeService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("")
    public ApiResponse<?> AddLike(@RequestBody BoardLikeRequestVO boardLikeRequestVO){
        System.out.println(boardLikeRequestVO);
        BoardLikeDto boardLikeDTO = modelMapper.map(boardLikeRequestVO, BoardLikeDto.class);
        BoardLikeDto boardLikeResponse = boardLikeService.addBoardLike(boardLikeDTO);
        System.out.println(boardLikeResponse);

        BoardLikeResponseVO boardLikeResponseVO = modelMapper.map(boardLikeResponse, BoardLikeResponseVO.class);
        return ApiResponse.ok(boardLikeResponseVO);
    }

}
