package stanl_2.weshareyou.domain.board.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.weshareyou.domain.board.aggregate.dto.BoardDTO;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;
import stanl_2.weshareyou.domain.board.repository.BoardRepository;
import stanl_2.weshareyou.domain.board_comment.aggregate.dto.BoardCommentDto;
import stanl_2.weshareyou.domain.board_comment.aggregate.entity.BoardComment;
import stanl_2.weshareyou.domain.board_comment.repository.BoardCommentRepository;
import stanl_2.weshareyou.domain.board_image.aggregate.entity.BoardImage;
import stanl_2.weshareyou.domain.board_image.repository.BoardImageRepository;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.member.repository.MemberRepository;
import stanl_2.weshareyou.domain.s3.S3uploader;
import stanl_2.weshareyou.global.common.dto.CursorDTO;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("boardServiceImpl")
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final S3uploader s3uploader;
    private final BoardImageRepository boardImageRepository;
    private Timestamp getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.from(nowKst.toInstant());
    }

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, ModelMapper modelMapper,
                            MemberRepository memberRepository, BoardCommentRepository boardCommentRepository,
                            S3uploader s3uploader, BoardImageRepository boardImageRepository) {
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
        this.memberRepository = memberRepository;
        this.boardCommentRepository = boardCommentRepository;
        this.s3uploader = s3uploader;
        this.boardImageRepository = boardImageRepository;
    }

    @Override
    @Transactional
    public BoardDTO createBoard(BoardDTO boardDTO) {
        Board board =new Board();
        List<String> images =new ArrayList<>();
        Timestamp currentTimestamp = getCurrentTimestamp();
        Long memberId = boardDTO.getMemberId();
        List<MultipartFile> files = boardDTO.getFile();


        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setTag(boardDTO.getTag());
        board.setCommentCount(0);
        board.setLikesCount(0);
        board.setCreatedAt(currentTimestamp);
        board.setUpdatedAt(currentTimestamp);
        board.setActive(true);
        board.setMember(member);
        boardRepository.save(board);

        List<String> imageList = s3uploader.uploadImg(boardDTO.getFile());
        BoardImage boardImage = new BoardImage();
        if(files != null){
            for (String imageUrl : imageList) {
                images.add(imageUrl);
                boardImage.setBoard(board);  // board와 관계 설정
            }
        }
        boardImage.setImageUrl(images);
        boardImageRepository.save(boardImage);  // DB에 저장
        BoardDTO boardResponseDTO = modelMapper.map(board, BoardDTO.class);
        boardResponseDTO.setMemberId(member.getId());
        boardResponseDTO.setImageUrl(images);
        return boardResponseDTO;
    }

    @Override
    @Transactional
    public BoardDTO updateBoard(BoardDTO boardDTO) {

        Timestamp currentTimestamp = getCurrentTimestamp();
        Board board = boardRepository.findById(boardDTO.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.BOARD_NOT_FOUND));

        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
//        board.setImageUrl(boardDTO.getImageUrl());
        board.setTag(boardDTO.getTag());
        board.setUpdatedAt(currentTimestamp);

        boardRepository.save(board);

        BoardDTO boardResponseDTO = modelMapper.map(board, BoardDTO.class);

        return boardResponseDTO;
    }

    @Override
    @Transactional
    public BoardDTO deleteBoard(BoardDTO boardDTO) {

        Timestamp currentTimestamp = getCurrentTimestamp();

        Board board = boardRepository.findById(boardDTO.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.BOARD_NOT_FOUND));

        board.setActive(false);
        board.setUpdatedAt(currentTimestamp);

        boardRepository.save(board);

        BoardDTO boardResponseDTO = modelMapper.map(board, BoardDTO.class);

        return boardResponseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public BoardDTO readDetailBoard(BoardDTO boardDTO) {

        Board board = boardRepository.findById(boardDTO.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.BOARD_NOT_FOUND));

        List<BoardComment> boardComments = boardCommentRepository.findByBoardId(board.getId());

        List<BoardCommentDto> boardCommentDTOs = boardComments.stream()
                .map(comment -> modelMapper.map(comment, BoardCommentDto.class))
                .collect(Collectors.toList());

        BoardDTO boardResponseDTO = new BoardDTO();
//        boardResponseDTO.setImageUrl(board.getImageUrl());
        boardResponseDTO.setContent(board.getContent());
        boardResponseDTO.setLikesCount(board.getLikesCount());
        boardResponseDTO.setMemberProfileUrl(board.getMember().getProfileUrl());
        boardResponseDTO.setMemberNickname(board.getMember().getNickname());
        boardResponseDTO.setComment(boardCommentDTOs);

        return boardResponseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public CursorDTO readBoard(CursorDTO cursorDTO) {

        Pageable pageable = PageRequest.of(0, cursorDTO.getSize());
        Slice<Board> boardList;

        if(cursorDTO.getCursorId() == null){
            boardList = boardRepository.findByTagOrderByCreatedAtDesc(cursorDTO.getTag(), pageable);
        } else {
            boardList = boardRepository.findByTagAndIdLessThanOrderByCreatedAtDesc
                    (cursorDTO.getTag(), cursorDTO.getCursorId(), pageable);
        }

        Long lastBoardId = boardList.getContent().isEmpty() ? null :
                boardList.getContent().get(boardList.getNumberOfElements() - 1).getId();

        List<BoardDTO> boardDTOList = boardList.getContent().stream()
                .map(board -> {
                    BoardDTO boardDTO = new BoardDTO();
                    boardDTO.setMemberProfileUrl(board.getMember().getProfileUrl());
                    boardDTO.setMemberNickname(board.getMember().getNickname());
//                    boardDTO.setImageUrl(board.getImageUrl());
                    boardDTO.setTitle(board.getTitle());
                    boardDTO.setLikesCount(board.getLikesCount());
                    boardDTO.setCommentCount(board.getCommentCount());
                    return boardDTO;
                })
                .collect(Collectors.toList());

        CursorDTO cursorResponseDTO = new CursorDTO();
        cursorResponseDTO.setCursorId(lastBoardId);
        cursorResponseDTO.setHasNext(boardList.hasNext());
        cursorResponseDTO.setTag(cursorDTO.getTag());
        cursorResponseDTO.setComment(boardDTOList);

        return cursorResponseDTO;
    }
}
