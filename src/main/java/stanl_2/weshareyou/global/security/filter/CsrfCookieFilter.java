package stanl_2.weshareyou.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CsrfCookieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        // Render the token value to a cookie by causing the deferred token to be loaded
        // lazy된 토큰을 로드하여 토큰 값을 쿠키로 렌터링 합니다.
//        csrfToken.getToken();

        if (csrfToken != null) {
            // CSRF 토큰을 쿠키로 설정
            Cookie cookie = new Cookie("XSRF-TOKEN", csrfToken.getToken());
            cookie.setPath("/"); // 쿠키 경로 설정
            cookie.setHttpOnly(false); // JS에서 접근할 수 있도록 HttpOnly 설정 false
            cookie.setSecure(request.isSecure()); // 요청이 secure(HTTPS)인 경우만 쿠키를 secure로 설정
            response.addCookie(cookie); // 응답에 쿠키 추가
        }

        filterChain.doFilter(request, response);
    }
}
