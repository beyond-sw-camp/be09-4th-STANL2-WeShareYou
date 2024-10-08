package stanl_2.weshareyou.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.weshareyou.domain.member.aggregate.dto.MemberRequestDTO;
import stanl_2.weshareyou.domain.member.aggregate.dto.MemberResponseDTO;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service("MemberServiceImpl")
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
    private final Environment env;
    private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMAT);



    /**
     * @param memberRequestDTO
     * @return
     */
    @Override
    @Transactional
    public MemberResponseDTO registMember(MemberRequestDTO memberRequestDTO) {

        String hashPwd = passwordEncoder.encode(memberRequestDTO.getPassword());
        memberRequestDTO.setPassword(hashPwd);
        log.info(hashPwd);
        Member registMember = modelMapper.map(memberRequestDTO, Member.class);
        log.info(registMember.toString());
        registMember.setUpdatedAt(LocalDateTime.now()
                .format(FORMATTER));
        registMember.setCreatedAt(LocalDateTime.now()
                .format(FORMATTER));

        Member newMember = memberRepository.save(registMember);

        return modelMapper.map(newMember, MemberResponseDTO.class);
    }
}
