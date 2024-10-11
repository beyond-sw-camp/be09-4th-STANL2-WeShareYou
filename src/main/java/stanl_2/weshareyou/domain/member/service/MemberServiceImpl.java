package stanl_2.weshareyou.domain.member.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.weshareyou.domain.member.aggregate.dto.MemberDTO;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.member.repository.MemberRepository;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;
import stanl_2.weshareyou.global.security.constants.ApplicationConstants;
import stanl_2.weshareyou.global.security.service.MemberDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service("MemberServiceImpl")
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationConstants applicationConstants;
    private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMAT);
    private static final long JWT_EXPIRATION_TIME = 30000000L;

    @Override
    @Transactional
    public MemberDTO registMember(MemberDTO memberRequestDTO) {

        // 비밀번호 해싱
        String hashPwd = passwordEncoder.encode(memberRequestDTO.getPassword());
        memberRequestDTO.setPassword(hashPwd);

        Member registMember = modelMapper.map(memberRequestDTO, Member.class);
        registMember.setUpdatedAt(LocalDateTime.now()
                .format(FORMATTER));
        registMember.setCreatedAt(LocalDateTime.now()
                .format(FORMATTER));
        registMember.setActive(true);

        Member newMember = memberRepository.save(registMember);

        return modelMapper.map(newMember, MemberDTO.class);
    }

    @Override
    @Transactional
    public Optional<MemberDTO> findMemberDetail(String username) {
        return memberRepository.findByLoginId(username);
    }

    @Override
    @Transactional
    public String loginMember(Authentication authenticationResponse) {

        String jwt = "";
        if (authenticationResponse != null && authenticationResponse.isAuthenticated()) {

            // 비밀키 생성 및 JWT 생성
            String secret = applicationConstants.getJWT_SECRET_DEFAULT_VALUE();
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            MemberDetails memberDetails = (MemberDetails) authenticationResponse.getPrincipal();
            Member member = memberDetails.getMember();  // MemberDetails에서 Member를 얻어옴

            jwt = Jwts.builder()
                    .setIssuer("STANL2")
                    .setSubject("JWT Token")
                    .claim("id", member.getId())
                    .claim("loginId", authenticationResponse.getName())
                    .claim("nationality", member.getNationality())
                    .claim("sex", member.getSex())
                    .claim("point", member.getPoint())
                    .claim("nickname", member.getNickname())
                    .claim("profile", member.getProfileUrl())
                    .claim("introduction", member.getIntroduction())
                    .claim("language", member.getLanguage())
                    .claim("authorities", authenticationResponse.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .setIssuedAt(new java.util.Date())
                    .setExpiration(new java.util.Date((new java.util.Date()).getTime() + JWT_EXPIRATION_TIME)) // 만료시간 8시간
                    .signWith(secretKey)
                    .compact(); // Digital Signature 생성

        }
        return jwt;
    }

    @Override
    @Transactional
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND)
        );

        member.setActive(false);

        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void updatePwd(MemberDTO memberRequestDTO) {

        Member member = memberRepository.findById(memberRequestDTO.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));
        String hashPwd = passwordEncoder.encode(memberRequestDTO.getPassword());

        member.setPassword(hashPwd);
        member.setUpdatedAt(LocalDateTime.now()
                .format(FORMATTER));

        memberRepository.save(member);
    }

    @Override
    public MemberDTO updateProfile(MemberDTO requestMemberDTO) {

        Member member = memberRepository.findById(requestMemberDTO.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        member.setNickname(requestMemberDTO.getNickname());
        member.setProfileUrl(requestMemberDTO.getProfileUrl());
        member.setIntroduction(requestMemberDTO.getIntroduction());
        member.setLanguage(requestMemberDTO.getLanguage());
        member.setUpdatedAt(LocalDateTime.now()
                .format(FORMATTER));
        memberRepository.save(member);

        MemberDTO responseMemberDTO = modelMapper.map(member, MemberDTO.class);

        // 보안상 null
        responseMemberDTO.setId(null);
        responseMemberDTO.setPassword(null);
        responseMemberDTO.setActive(null);

        return responseMemberDTO;
    }


    @Override
    public MemberDTO updateMypage(MemberDTO requestMemberDTO) {
        Member member = memberRepository.findById(requestMemberDTO.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        member.setPhone(requestMemberDTO.getPhone());

        Member updataMember = memberRepository.save(member);

        MemberDTO responseMemberDTO = modelMapper.map(updataMember, MemberDTO.class);

        // 보안상 null
        responseMemberDTO.setId(null);
        responseMemberDTO.setPassword(null);
        responseMemberDTO.setActive(null);

        return responseMemberDTO;
    }

    @Override
    public MemberDTO earnPoint(MemberDTO requestMemberDTO) {

        Member member = memberRepository.findById(requestMemberDTO.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        member.setPoint(member.getPoint() + requestMemberDTO.getPoint());

        memberRepository.save(member);

        MemberDTO responseMemberDTO = modelMapper.map(member, MemberDTO.class);

        // 보안상 null
        responseMemberDTO.setId(null);
        responseMemberDTO.setPassword(null);
        responseMemberDTO.setActive(null);

        return responseMemberDTO;
    }


    @Override
    public MemberDTO findId(MemberDTO requestMemberDTO) {

        Member member = memberRepository.findById(requestMemberDTO.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        MemberDTO responseMemberDTO = modelMapper.map(member, MemberDTO.class);

        // 보안상 null
        responseMemberDTO.setId(null);
        responseMemberDTO.setPassword(null);
        responseMemberDTO.setActive(null);

        return responseMemberDTO;
    }


    @Override
    public MemberDTO findMypage(MemberDTO requestMemberDTO) {

        Member member = memberRepository.findById(requestMemberDTO.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.MEMBER_NOT_FOUND));

        MemberDTO responseMemberDTO = modelMapper.map(member, MemberDTO.class);

        // 보안상 null
        responseMemberDTO.setId(null);
        responseMemberDTO.setPassword(null);
        responseMemberDTO.setActive(null);

        return responseMemberDTO;
    }
}
