package stanl_2.weshareyou.global.security.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;
import stanl_2.weshareyou.global.common.exception.CommonException;

import static stanl_2.weshareyou.global.common.exception.ErrorCode.FORBIDDEN_ROLE;

@Component
@Slf4j
public class AuthorizationEvents {
    @EventListener
    public void onFailure(AuthorizationDeniedEvent deniedEvent){
        log.error("권한 없음 유저: {} due to: {}", deniedEvent.getAuthentication().get().getName()
                , deniedEvent.getAuthorizationDecision().toString());
        throw new CommonException(FORBIDDEN_ROLE);
    }
}
