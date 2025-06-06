package com.dash.leap.global.auth.jwt.service;

import com.dash.leap.global.auth.jwt.exception.UnauthorizedException;
import com.dash.leap.global.auth.user.CustomUserDetails;
import com.dash.leap.global.auth.user.CustomUserService;
import com.dash.leap.global.exception.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserService customUserService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();

        // 관리자 웹의 정적 리소스 및 React 라우팅은 무시
        if (uri.startsWith("/leap/admin") &&
                (request.getMethod().equals("GET") || request.getMethod().equals("HEAD")) &&
                (uri.matches(".*\\.(js|css|png|jpg|jpeg|json|ico|svg|woff2?)$") || !uri.contains("."))) {

            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = extractToken(request);

            if (token != null) {
                jwtTokenProvider.validateToken(token);
                Claims claims = jwtTokenProvider.getPayload(token);

                Long userId = claims.get("id", Long.class);
                String role = claims.get("role", String.class);

                CustomUserDetails userDetails = customUserService.loadUserById(userId);

                // 권한 추가
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");

            ExceptionResponse body = new ExceptionResponse(HttpStatus.UNAUTHORIZED.toString(), e.getMessage());

            response.getWriter().write(objectMapper.writeValueAsString(body));
        }
    }

    private String extractToken(HttpServletRequest request) {

        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
