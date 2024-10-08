package stanl_2.weshareyou.domain.member.service;

import stanl_2.weshareyou.domain.member.aggregate.dto.reponse.MemberRequestDTO;
import stanl_2.weshareyou.domain.member.aggregate.dto.reponse.reponseMemberDetailDTO;
import stanl_2.weshareyou.domain.member.aggregate.dto.request.MemberResponseDTO;

import java.util.Optional;

public interface MemberService {
    MemberResponseDTO registMember(MemberRequestDTO memberRequestDTO);

    Optional<reponseMemberDetailDTO> findMemberDetail(String username);
}
