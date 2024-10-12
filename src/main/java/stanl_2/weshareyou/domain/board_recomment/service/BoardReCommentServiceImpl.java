package stanl_2.weshareyou.domain.board_recomment.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.weshareyou.domain.board_comment.aggregate.dto.BoardCommentDto;
import stanl_2.weshareyou.domain.board_recomment.aggregate.dto.BoardReCommentDto;
import stanl_2.weshareyou.domain.board_recomment.repository.BoardReCommentRepository;
import stanl_2.weshareyou.domain.board_comment.aggregate.entity.BoardComment;
import stanl_2.weshareyou.domain.board_recomment.aggregate.entity.BoardReComment;
import stanl_2.weshareyou.domain.board_comment.repository.BoardCommentRepository;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.member.repository.MemberRepository;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class BoardReCommentServiceImpl implements BoardReCommentService{

    private final BoardReCommentRepository boardReCommentRepository;
    private final MemberRepository memberRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final ModelMapper modelMapper;
    private Timestamp getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.from(nowKst.toInstant());
    }

    public BoardReCommentServiceImpl(BoardReCommentRepository boardReCommentRepository, MemberRepository memberRepository, BoardCommentRepository boardCommentRepository, ModelMapper modelMapper) {
        this.boardReCommentRepository = boardReCommentRepository;
        this.memberRepository = memberRepository;
        this.boardCommentRepository = boardCommentRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public BoardReCommentDto createBoardReComment(BoardReCommentDto boardReCommentDto) {
        Timestamp currentTimestamp = getCurrentTimestamp();
        Long memberId = boardReCommentDto.getMemberId();
        Long boardId = boardReCommentDto.getBoardCommentId();

        Member member =memberRepository.findById(memberId)
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));
        BoardComment boardComment = boardCommentRepository.findById(boardId)
                .orElseThrow(() -> new CommonException(ErrorCode.BOARD_NOT_FOUND));

        BoardReComment boardReComment = new BoardReComment();
        boardReComment.setContent(boardReCommentDto.getContent());
        boardReComment.setCreatedAt(currentTimestamp);
        boardReComment.setUpdatedAt(currentTimestamp);
        boardReComment.setBoardComment(boardComment);
        boardReComment.setMember(member);
        boardReCommentRepository.save(boardReComment);

        BoardReCommentDto boardReCommentDto1 = modelMapper.map(boardReComment, BoardReCommentDto.class);
        boardReCommentDto1.setBoardCommentId(boardComment.getId());

        return boardReCommentDto1;
    }

    @Transactional
    @Override
    public BoardReCommentDto updateBoardReComment(Long commentBoardId,BoardReCommentDto boardReCommentDto) {
        Timestamp currentTimestamp = getCurrentTimestamp();

        BoardReComment boardrecomment = boardReCommentRepository.findById(commentBoardId)
                .orElseThrow(() -> new CommonException(ErrorCode.BOARD_NOT_FOUND));

        boardrecomment.setContent(boardReCommentDto.getContent());
        boardrecomment.setUpdatedAt(currentTimestamp);
        boardReCommentRepository.save(boardrecomment);

        BoardReCommentDto boardReCommentDto1 = modelMapper.map(boardrecomment, BoardReCommentDto.class);
        boardReCommentDto1.setBoardCommentId(boardrecomment.getId());
        return boardReCommentDto1;
    }

    @Transactional
    @Override
    public void deleteBoardReComment(Long boardReCommentId) {
        BoardReComment boardrecomment = boardReCommentRepository.findById(boardReCommentId)
                .orElseThrow(() -> new CommonException(ErrorCode.BOARD_NOT_FOUND));
        boardReCommentRepository.delete(boardrecomment);
    }

    @Transactional
    @Override
    public List<BoardReCommentDto> readReCommentsByBoardId(Long boardCommentId) {
        List<BoardReComment> boardReComments = boardReCommentRepository.findByBoardCommentId(boardCommentId);
        return boardReComments.stream().map(boardReComment -> {
            BoardReCommentDto boardReCommentDto = modelMapper.map(boardReComment, BoardReCommentDto.class);
            boardReCommentDto.setNickname(boardReComment.getMember().getNickname());
            boardReCommentDto.setBoardCommentId(boardReComment.getBoardComment() != null ? boardReComment.getBoardComment().getId() : null);
            return boardReCommentDto;
        })
        .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<BoardReCommentDto> readReComments() {
        List<BoardReComment> boardReComments = boardReCommentRepository.findAll(); // 모든 댓글 조회
        return boardReComments.stream()
            .map(boardReComment -> {
                BoardReCommentDto boardReCommentDto = modelMapper.map(boardReComment, BoardReCommentDto.class);
                boardReCommentDto.setMemberId(boardReComment.getMember() != null ? boardReComment.getMember().getId() : null);
                boardReCommentDto.setBoardCommentId(boardReComment.getBoardComment() != null ? boardReComment.getBoardComment().getId() : null);
                return boardReCommentDto;
            })
            .collect(Collectors.toList());
    }
}
