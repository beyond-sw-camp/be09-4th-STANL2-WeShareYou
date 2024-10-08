package stanl_2.weshareyou.domain.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.weshareyou.domain.member.aggregate.dto.MemberRequestDTO;
import stanl_2.weshareyou.domain.member.aggregate.dto.MemberResponseDTO;
import stanl_2.weshareyou.domain.member.aggregate.vo.request.RegisterRequestVO;
import stanl_2.weshareyou.domain.member.aggregate.vo.response.RegisterResponseVO;
import stanl_2.weshareyou.domain.member.service.MemberService;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;
import stanl_2.weshareyou.global.common.response.ApiResponse;

@Slf4j
@RequiredArgsConstructor
@RestController("value = memberController")
@RequestMapping("api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;


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

}
