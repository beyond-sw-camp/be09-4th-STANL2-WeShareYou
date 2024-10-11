package stanl_2.weshareyou.domain.board.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import stanl_2.weshareyou.domain.board.aggregate.dto.BoardDTO;
import stanl_2.weshareyou.domain.board.aggregate.dto.SliceDTO;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;
import stanl_2.weshareyou.domain.board.aggregate.entity.TAG;
import stanl_2.weshareyou.domain.board.repository.BoardRepository;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.member.repository.MemberRepository;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service("boardServiceImpl")
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMAT);
    private final MemberRepository memberRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, ModelMapper modelMapper, MemberRepository memberRepository) {
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
        this.memberRepository = memberRepository;
    }


    @Override
    @Transactional
    public BoardDTO createBoard(BoardDTO boardDTO) {

        Long memberId = boardDTO.getMemberId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setImageUrl(boardDTO.getImageUrl());
        board.setTag(boardDTO.getTag());
        board.setCommentCount(0);
        board.setLikesCount(0);
        board.setCreatedAt(LocalDateTime.now().format(FORMATTER));
        board.setUpdatedAt(LocalDateTime.now().format(FORMATTER));
        board.setActive(true);
        board.setMember(member);

        boardRepository.save(board);

        BoardDTO boardResponseDTO = modelMapper.map(board, BoardDTO.class);
        boardResponseDTO.setMemberId(member.getId());

        return boardResponseDTO;
    }

    @Override
    @Transactional
    public BoardDTO updateBoard(BoardDTO boardDTO) {

        Board board = boardRepository.findById(boardDTO.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.BOARD_NOT_FOUND));

        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setImageUrl(boardDTO.getImageUrl());
        board.setTag(boardDTO.getTag());
        board.setUpdatedAt(LocalDateTime.now().format(FORMATTER));

        boardRepository.save(board);

        BoardDTO boardResponseDTO = modelMapper.map(board, BoardDTO.class);

        return boardResponseDTO;
    }

    @Override
    @Transactional
    public BoardDTO deleteBoard(BoardDTO boardDTO) {

        Board board = boardRepository.findById(boardDTO.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.BOARD_NOT_FOUND));

        board.setActive(false);

        boardRepository.save(board);

        BoardDTO boardResponseDTO = modelMapper.map(board, BoardDTO.class);

        return boardResponseDTO;
    }

    /*
            게시글 전체 조회에 띄울 요소
            1. 게시글 작성자 프로필 (member - profileUrl)
            2. 게시글 작성자 닉네임 (member - nickname)
            3. 게시글 이미지 (board - imageUrl)
            4. 게시글 내용 (board - content)
            5. 게시글 좋아요 갯수 (board - likesCount)
            6. 게시글 댓글 갯수 (board - commentCount)
    */

}
