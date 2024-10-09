package stanl_2.weshareyou.domain.member.service;

import org.springframework.security.core.Authentication;
import stanl_2.weshareyou.domain.member.aggregate.dto.MemberDTO;

import java.util.Optional;

public interface MemberService {
    MemberDTO registMember(MemberDTO memberRequestDTO);

    Optional<MemberDTO> findMemberDetail(String username);

    String loginMember(Authentication authenticationResponse);
}
