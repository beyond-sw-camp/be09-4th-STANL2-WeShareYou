package stanl_2.weshareyou.domain.member.service;

import org.springframework.security.core.Authentication;
import stanl_2.weshareyou.domain.member.aggregate.dto.reponse.MemberRequestDTO;
import stanl_2.weshareyou.domain.member.aggregate.dto.reponse.reponseMemberDetailDTO;
import stanl_2.weshareyou.domain.member.aggregate.dto.request.MemberResponseDTO;

import java.util.Optional;

public interface MemberService {
    MemberResponseDTO registMember(MemberRequestDTO memberRequestDTO);

    Optional<reponseMemberDetailDTO> findMemberDetail(String username);

    String loginMember(Authentication authenticationResponse);
}
