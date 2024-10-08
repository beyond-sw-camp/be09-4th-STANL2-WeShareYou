package stanl_2.weshareyou.domain.member.controller;

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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import stanl_2.weshareyou.domain.member.aggregate.dto.reponse.MemberRequestDTO;
import stanl_2.weshareyou.domain.member.aggregate.dto.reponse.reponseMemberDetailDTO;
import stanl_2.weshareyou.domain.member.aggregate.dto.request.MemberResponseDTO;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
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
import java.util.stream.Collectors;

import static stanl_2.weshareyou.global.common.exception.ErrorCode.LOGIN_FAILURE;

@Slf4j
@RequiredArgsConstructor
@RestController("value = memberController")
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final Environment env;
    private final ApplicationConstants applicationConstants;


    @PostMapping("/register")
    public ApiResponse<?> registerMember(@RequestBody RegisterRequestVO memberInfo) {
        MemberRequestDTO memberRequestDTO = modelMapper.map(memberInfo, MemberRequestDTO.class);
        MemberResponseDTO memberResponseDTO = memberService.registMember(memberRequestDTO);

        if(memberResponseDTO.getId() <= 0) {
            throw new CommonException(ErrorCode.REGISTER_FAIL);
        }

        RegisterResponseVO registerResponseVO = modelMapper.map(memberResponseDTO, RegisterResponseVO.class);

        return ApiResponse.ok(registerResponseVO);
    }

    @RequestMapping("/userDetail")
    public reponseMemberDetailDTO getUserDetailsAfterLogin(Authentication authentication){
        Optional<reponseMemberDetailDTO> memberDetailDTO = memberService.findMemberDetail(authentication.getName());

        return memberDetailDTO.orElse(null);
    }

    @PostMapping("/login")
    public ApiResponse<?> loginMember(@RequestBody LoginRequestVO loginRequestVO){

        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequestVO.getLoginId(), loginRequestVO.getPassword());

        Authentication authenticationResponse = authenticationManager.authenticate(authentication);

        String jwt = memberService.loginMember(authenticationResponse);
        if("".equals(jwt)){
            throw new CommonException(LOGIN_FAILURE);
        }

        return ApiResponse.ok(new LoginResponseVO(HttpStatus.OK.getReasonPhrase(), jwt));
    }



}
