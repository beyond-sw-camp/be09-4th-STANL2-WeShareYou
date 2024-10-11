package stanl_2.weshareyou.domain.board.aggregate.dto;

import lombok.*;
import org.springframework.data.domain.Sort;
import stanl_2.weshareyou.domain.board.aggregate.entity.TAG;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SliceDTO<T> {

//    토큰으로 회원정보 받아오면 사용
//    private final Integer memberId;
    private final List<T> content;  // 페이징된 데이터 리스트
    private final List<Sort.Order> sort;  // 정렬 정보 리스트
    private final int currentPage;   // 현재 페이지 번호
    private final int size;          // 페이지당 데이터 수
    private final boolean first;     // 첫 페이지 여부
    private final boolean last;      // 마지막 페이지 여부
}
