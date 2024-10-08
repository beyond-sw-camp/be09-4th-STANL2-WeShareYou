package stanl_2.weshareyou.domain.board_like.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.weshareyou.domain.board.aggregate.entity.Board;
import stanl_2.weshareyou.domain.board.aggregate.repository.BoardRepository;
import stanl_2.weshareyou.domain.board_like.aggregate.dto.BoardLikeDto;
import stanl_2.weshareyou.domain.board_like.aggregate.entity.BoardLike;
import stanl_2.weshareyou.domain.board_like.aggregate.entity.BoardLikeId;
import stanl_2.weshareyou.domain.board_like.repository.BoardLikeRepository;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;



@Service
public class BoardLikeServiceImpl implements BoardLikeService{
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public BoardLikeServiceImpl(BoardLikeRepository boardLikeRepository, BoardRepository boardRepository) {
        this.boardLikeRepository = boardLikeRepository;
        this.boardRepository = boardRepository;
    }


    @Transactional
    @Override
    public BoardLikeDto addBoardLike(BoardLikeDto boardLikeDto) {
        Long boardId = boardLikeDto.getBoardId();
        Long memberId = boardLikeDto.getMemberId();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CommonException(ErrorCode.BOARD_NOT_FOUND));
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        boolean existingLike
                = boardLikeRepository.findById(new BoardLikeId(boardId, memberId)).isPresent();

        if(existingLike){
            throw new CommonException(ErrorCode.ALREADY_LIKED);
        }
        else{

            BoardLike newBoardLike = new BoardLike();
            newBoardLike.setMemberId(memberId);
            newBoardLike.setBoardId(board);
            board.setLikesCount(board.getLikesCount()+1);
            boardLikeRepository.save(newBoardLike);

            return boardLikeDto;
        }

    }
}
