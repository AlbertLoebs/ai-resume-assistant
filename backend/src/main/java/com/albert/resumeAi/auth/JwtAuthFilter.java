package com.albert.resumeAi.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Servlet filter that runs once per request and populates the SecurityContext
 * when a valid JWT is present.
 *
 * Extend OncePerRequestFilter — Spring guarantees exactly one execution per request,
 * even across async dispatches and error forwards.
 *
 * Algorithm (implement in doFilterInternal):
 *
 *   1. Read the Authorization header. If it's absent or doesn't start with "Bearer ",
 *      call filterChain.doFilter(request, response) and return immediately.
 *      (The request is anonymous — Spring Security will block it if the endpoint requires auth.)
 *
 *   2. Extract the token string: header.substring(7).
 *
 *   3. Call jwtService.isTokenValid(token). If false, same as step 1 — pass through.
 *
 *   4. Extract the subject (user UUID string) via jwtService.extractSubject(token).
 *
 *   5. If SecurityContextHolder.getContext().getAuthentication() is already set,
 *      skip (another filter already authenticated this request).
 *
 *   6. Build a UsernamePasswordAuthenticationToken with:
 *        - principal   = the subject string (UUID)
 *        - credentials = null
 *        - authorities = empty list (no roles yet)
 *      Set .setDetails(new WebAuthenticationDetailsSource().buildDetails(request)).
 *
 *   7. Set it on the context:
 *        SecurityContextHolder.getContext().setAuthentication(authToken);
 *
 *   8. Call filterChain.doFilter(request, response) to continue the chain.
 *
 * Things NOT to do:
 *   - Don't hit the database here. The JWT is self-contained; trust the signature.
 *   - Don't return a 401 yourself. Let Spring Security's ExceptionTranslationFilter
 *     handle unauthenticated access — it produces the proper RFC-7235 response.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // TODO: read Authorization header
        // TODO: extract + validate token
        // TODO: build UsernamePasswordAuthenticationToken and set on SecurityContext
        // TODO: continue filter chain

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String userId = jwtService.extractSubject(token);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    List.of()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
