package stanl_2.weshareyou.domain.member.service;

import org.springframework.security.core.Authentication;
import stanl_2.weshareyou.domain.member.aggregate.dto.MemberDTO;

import java.util.Optional;

public interface MemberService {
    MemberDTO registMember(MemberDTO memberRequestDTO);

    Optional<MemberDTO> findMemberDetail(String username);

    String loginMember(Authentication authenticationResponse);

    void deleteMember(Long id);

    void updatePwd(MemberDTO memberRequestDTO);

    MemberDTO updateProfile(MemberDTO requestMemberDTO);

    MemberDTO updateMypage(MemberDTO requestMemberDTO);

    MemberDTO earnPoint(MemberDTO requestMemberDTO);

    MemberDTO findId(MemberDTO requestMemberDTO);

    MemberDTO findMypage(MemberDTO requestMemberDTO);

    MemberDTO findPoint(MemberDTO requestMemberDTO);

    Boolean findNickname(String username);
}