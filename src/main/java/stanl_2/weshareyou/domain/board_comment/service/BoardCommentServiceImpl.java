package stanl_2.weshareyou.domain.board_comment.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.weshareyou.domain.board.aggregate.dto.BoardDTO;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;
import stanl_2.weshareyou.domain.board.repository.BoardRepository;
import stanl_2.weshareyou.domain.board_comment.aggregate.dto.BoardCommentDto;
import stanl_2.weshareyou.domain.board_comment.aggregate.entity.BoardComment;
import stanl_2.weshareyou.domain.board_comment.repository.BoardCommentRepository;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.member.repository.MemberRepository;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@Service
@Slf4j
public class BoardCommentServiceImpl implements BoardCommentService{
    private final BoardCommentRepository boardCommentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    private Timestamp getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.from(nowKst.toInstant());
    }

    @Autowired
    public BoardCommentServiceImpl(BoardCommentRepository boardCommentRepository,MemberRepository memberRepository, BoardRepository boardRepository, ModelMapper modelMapper) {
        this.boardCommentRepository = boardCommentRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public BoardCommentDto createBoardComment(BoardCommentDto boardCommentDto) {
        Timestamp currentTimestamp = getCurrentTimestamp();
        Long memberId = boardCommentDto.getMemberId();
        Long boardId = boardCommentDto.getBoardId();

        Member member =memberRepository.findById(memberId)
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CommonException(ErrorCode.BOARD_NOT_FOUND));

        BoardComment boardComment = new BoardComment();
        boardComment.setContent(boardCommentDto.getContent());
        boardComment.setCreatedAt(currentTimestamp);
        boardComment.setUpdatedAt(currentTimestamp);
        boardComment.setBoard(board);
        boardComment.setMember(member);
        boardCommentRepository.save(boardComment);

        BoardCommentDto boardCommentDto1 = modelMapper.map(boardComment, BoardCommentDto.class);
        boardCommentDto1.setBoardId(board.getId());

        return boardCommentDto1;
    }

    @Transactional
    @Override
    public BoardCommentDto updateBoardComment(Long boardId,BoardCommentDto boardCommentDto) {
        Timestamp currentTimestamp = getCurrentTimestamp();

        BoardComment boardcomment = boardCommentRepository.findById(boardId)
                .orElseThrow(() -> new CommonException(ErrorCode.BOARD_NOT_FOUND));

        boardcomment.setContent(boardCommentDto.getContent());
        boardcomment.setUpdatedAt(currentTimestamp);
        boardCommentRepository.save(boardcomment);

        BoardCommentDto boardCommentDto1 = modelMapper.map(boardcomment, BoardCommentDto.class);
        boardCommentDto1.setBoardId(boardcomment.getId());
        return boardCommentDto1;
    }
}
