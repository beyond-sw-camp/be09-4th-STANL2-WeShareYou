package stanl_2.weshareyou.global.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stanl_2.weshareyou.domain.member.aggregate.entity.Member;
import stanl_2.weshareyou.domain.member.repository.MemberRepository;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;

@Service(value = "UserDetailService")
@RequiredArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService, UserDetailService {

    private MemberRepository memberRepository;

    /**
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.warn("여기까진 왔니?2");
       Member member = memberRepository.findByloginId(username).orElseThrow(() ->
               new CommonException(ErrorCode.USERDETAILS_NOT_FOUND));
        log.warn("여기까진 왔니?2");
        /* 필요하면 권한 설정 가능 */

        return null;
    }
}
