package stanl_2.weshareyou.domain.member.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import stanl_2.weshareyou.domain.member.aggregate.dto.MemberDTO;
import stanl_2.weshareyou.domain.member.aggregate.vo.request.LoginRequestVO;
import stanl_2.weshareyou.domain.member.aggregate.vo.request.RegisterRequestVO;
import stanl_2.weshareyou.domain.member.aggregate.vo.response.LoginResponseVO;
import stanl_2.weshareyou.domain.member.aggregate.vo.response.RegisterResponseVO;
import stanl_2.weshareyou.domain.member.service.MemberService;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;
import stanl_2.weshareyou.global.common.response.ApiResponse;
import stanl_2.weshareyou.global.security.constants.ApplicationConstants;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController("value = memberController")
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final ApplicationConstants applicationConstants;

    @GetMapping("/health")
    public ApiResponse<?> healthCheck(@RequestHeader("Authorization") String token) {
        // "Bearer " 접두사를 제거하여 순수한 토큰만 추출
        String jwtToken = token.substring(7);
        log.info("jwtToken: {}", jwtToken);
        // 시크릿 키로 JWT를 파싱하여 클레임을 추출
        SecretKey secretKey = Keys.hmacShaKeyFor(applicationConstants.getJWT_SECRET_DEFAULT_VALUE().getBytes(StandardCharsets.UTF_8));
        log.info("secretKey: {}", secretKey);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
        log.info("claims ID: {}", claims.getId());
        log.info("claims SUBJECT: {}", claims.getSubject());
        log.info("claims ISSUER: {}", claims.getIssuer());
        log.info("claims AUDIENCE: {}", claims.getAudience());
//        log.info("claims AUDIENCE: {}", claims.);



        // nickname 클레임에서 값 추출
        String nickname = claims.get("nickname", String.class);

        return ApiResponse.ok(nickname);
    }

    @PostMapping("/register")
    public ApiResponse<?> registerMember(@RequestBody RegisterRequestVO memberInfo) {
        MemberDTO memberRequestDTO = modelMapper.map(memberInfo, MemberDTO.class);
        MemberDTO memberResponseDTO = memberService.registMember(memberRequestDTO);

        if(memberResponseDTO.getId() <= 0) {
            throw new CommonException(ErrorCode.REGISTER_FAIL);
        }

        RegisterResponseVO registerResponseVO = modelMapper.map(memberResponseDTO, RegisterResponseVO.class);

        return ApiResponse.ok(registerResponseVO);
    }

    @RequestMapping("/userDetail")
    public MemberDTO getUserDetailsAfterLogin(Authentication authentication){
        Optional<MemberDTO> memberDetailDTO = memberService.findMemberDetail(authentication.getName());

        return memberDetailDTO.orElse(null);
    }

    @PostMapping("/login")
    public ApiResponse<?> loginMember(@RequestBody LoginRequestVO loginRequestVO){

        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequestVO.getLoginId(), loginRequestVO.getPassword());

        Authentication authenticationResponse = authenticationManager.authenticate(authentication);

        String jwt = memberService.loginMember(authenticationResponse);
        if("".equals(jwt)){
            throw new CommonException(ErrorCode.LOGIN_FAILURE);
        }

        return ApiResponse.ok(new LoginResponseVO(HttpStatus.OK.getReasonPhrase(), jwt));
    }



}
