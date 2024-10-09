package stanl_2.weshareyou.domain.member.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import stanl_2.weshareyou.domain.member.aggregate.dto.MemberDTO;
import stanl_2.weshareyou.domain.member.aggregate.vo.request.EmailAddressRequestVO;
import stanl_2.weshareyou.domain.member.aggregate.vo.request.LoginRequestVO;
import stanl_2.weshareyou.domain.member.aggregate.vo.request.RegisterRequestVO;
import stanl_2.weshareyou.domain.member.aggregate.vo.response.LoginResponseVO;
import stanl_2.weshareyou.domain.member.aggregate.vo.response.RegisterResponseVO;
import stanl_2.weshareyou.domain.member.service.MemberService;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;
import stanl_2.weshareyou.global.common.response.ApiResponse;
import stanl_2.weshareyou.global.security.service.smtp.MailService;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController("value = memberController")
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;


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

    // 이메일 전송
    @GetMapping("/mail")
    public ApiResponse<?> sendEmailCheck(@RequestBody EmailAddressRequestVO emailAddressRequestVO) throws MessagingException {
        mailService.sendEmail(emailAddressRequestVO.getLoginId());
        return ApiResponse.ok("이메일 전송 성공!");
    }

}
