package com.himanshu.payflow.auth.security;

import com.himanshu.payflow.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = authHeader.substring(7);
        String email = jwtService.extractUsername(jwt);

//        Spring Security stores the authenticated user in SecurityContextHolder.getContext()
//        If authentication already exists, we don't want to authenticate the user again.
//        Imagine one filter has already authenticated the request.
//        Then this check prevents duplicate work.
//        This is standard practice in Spring Security filters.
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            if(jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new  UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
//            'null' for the credentials because the password has already been verified when the user logged in. From now on, the JWT itself is the proof of authentication.

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                This stores additional information such as:
//                Client IP
//                Session ID (if applicable)

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                It tells Spring Security:
//                "This request has already been authenticated. Here is the authenticated user."
//                From this point onward, every controller and every security rule sees the request as coming from a logged-in user.
            }
        }

        filterChain.doFilter(request, response);
    }
}
