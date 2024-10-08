package stanl_2.weshareyou.domain.member.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.weshareyou.domain.member.aggregate.dto.reponse.MemberRequestDTO;
import stanl_2.weshareyou.domain.member.aggregate.dto.reponse.reponseMemberDetailDTO;
import stanl_2.weshareyou.domain.member.aggregate.dto.request.MemberResponseDTO;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.member.repository.MemberRepository;
import stanl_2.weshareyou.global.security.constants.ApplicationConstants;

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
    private final Environment env;
    private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMAT);


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


    @Override
    @Transactional
    public Optional<reponseMemberDetailDTO> findMemberDetail(String username) {
        return memberRepository.findByLoginId(username);
    }


    @Override
    @Transactional
    public String loginMember(Authentication authenticationResponse) {

        String jwt = "";
        if(authenticationResponse != null && authenticationResponse.isAuthenticated()){
            if (null != env) {
                String secret = env.getProperty(applicationConstants.getJWT_SECRET_KEY(),
                        applicationConstants.getJWT_SECRET_DEFAULT_VALUE());
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                jwt = Jwts.builder()
                        .setIssuer("STANL2")
                        .setSubject("JWT Token")
                        .claim("username", authenticationResponse.getName())
                        .claim("authorities", authenticationResponse.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .setIssuedAt(new java.util.Date())
                        .setExpiration(new java.util.Date((new java.util.Date()).getTime() + 30000000)) // 만료시간 8시간
                        .signWith(secretKey)
                        .compact(); // Digital Signature 생성
            }
        }
        return jwt;
    }
}
