package stanl_2.weshareyou.domain.board_like.service;


import stanl_2.weshareyou.domain.board_like.aggregate.dto.BoardLikeDto;

import stanl_2.weshareyou.domain.member.aggregate.entity.Member;

import java.util.List;


public interface BoardLikeService {
    BoardLikeDto BoardLike(BoardLikeDto boardLikeDto);
//    List<Member> BoardLikeList(Long boardId);

    List<Long> BoardLikeList(Long boardId);

}
