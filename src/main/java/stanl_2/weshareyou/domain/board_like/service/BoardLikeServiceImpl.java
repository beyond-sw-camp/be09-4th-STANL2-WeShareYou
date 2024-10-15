package stanl_2.weshareyou.domain.board_like.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;
import stanl_2.weshareyou.domain.board.repository.BoardRepository;
import stanl_2.weshareyou.domain.board_like.aggregate.dto.BoardLikeDto;
import stanl_2.weshareyou.domain.board_like.aggregate.entity.BoardLike;
import stanl_2.weshareyou.domain.board_like.aggregate.entity.BoardLikeId;
import stanl_2.weshareyou.domain.board_like.repository.BoardLikeRepository;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.member.repository.MemberRepository;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;

import java.util.List;


@Service
public class BoardLikeServiceImpl implements BoardLikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public BoardLikeServiceImpl(BoardLikeRepository boardLikeRepository, BoardRepository boardRepository, MemberRepository memberRepository) {
        this.boardLikeRepository = boardLikeRepository;
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public BoardLikeDto BoardLike(BoardLikeDto boardLikeDto) {
        Long boardId = boardLikeDto.getBoardId();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CommonException(ErrorCode.BOARD_NOT_FOUND));
        Long memberId = boardLikeDto.getMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));
        boolean existingLike
                = boardLikeRepository.findById(new BoardLikeId(memberId, boardId)).isPresent();

        if (existingLike) {
            throw new CommonException(ErrorCode.ALREADY_LIKED);
        } else {
            BoardLike newBoardLike = new BoardLike();
            newBoardLike.setMember(member);
            newBoardLike.setBoard(board);
            board.setLikesCount(board.getLikesCount() + 1);
            boardLikeRepository.save(newBoardLike);
            return boardLikeDto;
        }
    }

    @Transactional
    @Override
    public BoardLikeDto BoardUnLike(BoardLikeDto boardUnLikeDto) {
        Long boardId = boardUnLikeDto.getBoardId();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CommonException(ErrorCode.BOARD_NOT_FOUND));
        Long memberId = boardUnLikeDto.getMemberId();
        BoardLike existingLike =
                boardLikeRepository.findById(new BoardLikeId(memberId,boardId))
                        .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LIKE));
        boardLikeRepository.delete(existingLike);
        board.setLikesCount(board.getLikesCount()-1);

        return boardUnLikeDto;
    }

//  Member전체 반환
//    @Transactional
//    @Override
//    public List<Member> BoardLikeList(Long boardId) {
//        Board board = boardRepository.findById(boardId)
//                .orElseThrow(() -> new CommonException(ErrorCode.BOARD_NOT_FOUND));
//        List<Member> members = boardLikeRepository.findMembersByBoard(board);
//        return members;
//    }


    //Id만 반환
    @Transactional
    @Override
    public List<Long> BoardLikeList(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CommonException(ErrorCode.BOARD_NOT_FOUND));
        List<Long> memberIds = boardLikeRepository.findMemberIdsByBoard(board);

        return memberIds;
    }
}
