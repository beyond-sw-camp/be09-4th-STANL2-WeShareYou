package stanl_2.weshareyou.domain.board.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.weshareyou.domain.board.aggregate.dto.BoardDTO;
import stanl_2.weshareyou.domain.board.aggregate.vo.request.BoardCreateRequestVO;
import stanl_2.weshareyou.domain.board.aggregate.vo.request.BoardDeleteRequestVO;
import stanl_2.weshareyou.domain.board.aggregate.vo.request.BoardUpdateRequestVO;
import stanl_2.weshareyou.domain.board.aggregate.vo.response.BoardCreateResponseVO;
import stanl_2.weshareyou.domain.board.aggregate.vo.response.BoardDeleteResponseVO;
import stanl_2.weshareyou.domain.board.aggregate.vo.response.BoardReadDetailResponseVO;
import stanl_2.weshareyou.domain.board.aggregate.vo.response.BoardUpdateResponseVO;
import stanl_2.weshareyou.domain.board.service.BoardService;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;
import stanl_2.weshareyou.global.common.response.ApiResponse;

import java.io.IOException;

@RestController(value = "boardController")
@RequestMapping("/api/v1/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final ModelMapper modelMapper;
    private final AmazonS3Client amazonS3Client;

    @Autowired
    public BoardController(BoardService boardService, ModelMapper modelMapper, AmazonS3Client amazonS3Client) {
        this.boardService = boardService;
        this.modelMapper = modelMapper;
        this.amazonS3Client = amazonS3Client;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @PostMapping("/img")
    public ApiResponse<?> uploadImg(@RequestParam("file") MultipartFile file){

        log.info("값 확인: {}", file);

        try {
            String fileName=file.getOriginalFilename();
            String fileUrl= "https://" + bucket + "/" +fileName;
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
            return ApiResponse.ok(fileUrl);
        } catch (IOException e) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 내용: 게시글 생성
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
     *         "title": "가이드 해드립니다.",
     *         "content": "안녕",
     *         "imageUrl": "image1",
     *         "tag": "GUIDE",
     *         "memberId": 1
     *     },
     *     "error": null
     * }
     */
    @PostMapping("")
    public ApiResponse<?> createBoard(@RequestBody @Valid BoardCreateRequestVO boardCreateRequestVO){

        BoardDTO boardDTO = modelMapper.map(boardCreateRequestVO, BoardDTO.class);

        BoardDTO boardResponseDTO = boardService.createBoard(boardDTO);

        BoardCreateResponseVO boardCreateResponseVO = modelMapper.map(boardResponseDTO, BoardCreateResponseVO.class);

        return ApiResponse.ok(boardCreateResponseVO);
    }

    /**
     * 내용: 게시글 내용 수정
     * req:
     * {
     *     "id": 4,
     *     "title": "guide",
     *     "imageUrl": "image5",
     *     "memberId": 1
     * }
     * res:
     * {
     *     "success": true,
     *     "result": {
     *         "title": "guide",
     *         "content": "안녕하세요, 가이드 5년차 홍길동입니다. 가이드가 필요하신분 연락주세요",
     *         "imageUrl": "image5",
     *         "tag": "GUIDE",
     *         "memberId": 1
     *     },
     *     "error": null
     * }
     */
    @PutMapping("/update")
    public ApiResponse<?> updateBoard(@RequestBody @Valid BoardUpdateRequestVO boardUpdateRequestVO) {

        BoardDTO boardDTO = modelMapper.map(boardUpdateRequestVO, BoardDTO.class);

        BoardDTO boardResponseDTO = boardService.updateBoard(boardDTO);

        BoardUpdateResponseVO boardUpdateResponseVO = modelMapper.map(boardResponseDTO, BoardUpdateResponseVO.class);

        return ApiResponse.ok(boardUpdateResponseVO);
    }

    /**
     * 내용: 게시글 수정
     * req:
     * {
     *     "id": 4,
     *     "memberId": 1
     * }
     * res:
     *{
     *     "success": true,
     *     "result": {
     *         "id": 4,
     *         "active": false
     *     },
     *     "error": null
     * }
     */
    @DeleteMapping("/delete")
    public ApiResponse<?> deleteBoard(@RequestBody @Valid BoardDeleteRequestVO boardDeleteRequestVO){

        BoardDTO boardDTO = modelMapper.map(boardDeleteRequestVO, BoardDTO.class);

        BoardDTO boardResponseDTO = boardService.deleteBoard(boardDTO);

        BoardDeleteResponseVO boardDeleteResponseVO = modelMapper.map(boardResponseDTO, BoardDeleteResponseVO.class);

        return ApiResponse.ok(boardDeleteResponseVO);
    }

    /**
     * 내용: 게시글 상세조회
     * req: X
     * res: {
     *     "success": true,
     *     "result": {
     *         "imageUrl": "image1",
     *         "content": "A detailed guide to traveling in Paris.",
     *         "likesCount": 0,
     *         "memberProfileUrl": null,
     *         "memberNickname": "userone",
     *         "comment": [
     *             {
     *                 "content": "string",
     *                 "createdAt": "2024-10-11T15:54:42.000+00:00",
     *                 "updatedAt": "2024-10-11T15:54:42.907+00:00",
     *                 "memberNickname": null,
     *                 "boardId": null
     *             } , ...
     *         ]
     *     },
     *     "error": null
     * }
     */

    @GetMapping("/detail/{id}")
    public ApiResponse<?> readDetailBoard(@PathVariable Long id){

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(id);

        BoardDTO boardResponseDTO = boardService.readDetailBoard(boardDTO);

        BoardReadDetailResponseVO boardReadDetailResponseVO =
                modelMapper.map(boardResponseDTO,BoardReadDetailResponseVO.class);

        return ApiResponse.ok(boardReadDetailResponseVO);
    }
}
