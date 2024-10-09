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
public class BoardLikeServiceImpl implements BoardLikeService{
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
        Long memberId = boardLikeDto.getMemberId();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CommonException(ErrorCode.BOARD_NOT_FOUND));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));
        System.out.println("============================");
        boolean existingLike
                = boardLikeRepository.findById(new BoardLikeId(memberId,boardId)).isPresent();

        if(existingLike){
            throw new CommonException(ErrorCode.ALREADY_LIKED);
        }
        else{
            BoardLike newBoardLike = new BoardLike();
            newBoardLike.setMember(member);
            newBoardLike.setBoardId(board);
            board.setLikesCount(board.getLikesCount()+1);
            boardLikeRepository.save(newBoardLike);

            return boardLikeDto;
        }

    }


    @Transactional
    @Override
    public List<BoardLike> BoardLikeList(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        // Member 객체로 좋아요 목록 조회
        List<BoardLike> likeList = boardLikeRepository.findByMember(member);
        if (likeList.isEmpty()) {
            throw new CommonException(ErrorCode.NO_LIKES_FOUND);
        }
        return likeList;
    }
}
