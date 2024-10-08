package stanl_2.weshareyou.domain.member.service;

import stanl_2.weshareyou.domain.member.aggregate.dto.MemberRequestDTO;
import stanl_2.weshareyou.domain.member.aggregate.dto.MemberResponseDTO;

public interface MemberService {
    MemberResponseDTO registMember(MemberRequestDTO memberRequestDTO);
}
