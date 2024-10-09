package stanl_2.weshareyou.global.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import stanl_2.weshareyou.global.common.exception.CommonException;
import stanl_2.weshareyou.global.common.exception.ErrorCode;
import stanl_2.weshareyou.global.security.constants.ApplicationConstants;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    private final ApplicationConstants applicationConstants;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(applicationConstants.getJWT_HEADER());
        if(null != jwt) {
            try {
                Environment env = getEnvironment();
                if (null != env) {
                    String secret = env.getProperty(applicationConstants.getJWT_SECRET_KEY(),
                            applicationConstants.getJWT_SECRET_DEFAULT_VALUE());
                    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                    if(null !=secretKey) {
                        // JWT 토큰 검증
                        Claims claims = Jwts.parserBuilder()
                                .setSigningKey(secretKey)
                                .build()
                                .parseClaimsJws(jwt)        // 권한 정보
                                .getBody();                 // 사용자 관련 정보

                        String username = String.valueOf(claims.get("username"));
                        String authorities = String.valueOf(claims.get("authorities"));
                        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
                                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                        // SecurityContextHolder에 저장
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }

            } catch (Exception exception) {
                throw new CommonException(ErrorCode.INVALID_TOKEN_ERROR);
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/v1/member/userDetail");
    }
}
