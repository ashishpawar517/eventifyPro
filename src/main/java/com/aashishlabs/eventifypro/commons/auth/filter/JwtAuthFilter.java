package com.aashishlabs.eventifypro.commons.auth.filter;

import com.aashishlabs.eventifypro.commons.auth.service.JwtService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  public static final String AUTHORIZATION = "Authorization";
  public static final String BEARER_ = "Bearer ";
  public static final int BEGIN_INDEX_OF_AUTH_HEADER = 7;

  private final JwtService jwtService;

  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(@Nonnull HttpServletRequest request,
      @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain)
      throws ServletException, IOException {

    final String authHeader = request.getHeader(AUTHORIZATION);
    final String jwt;
    final String username;
    if (authHeader == null || !authHeader.startsWith(BEARER_)) {
      filterChain.doFilter(request, response);
      return;
    }
    jwt = authHeader.substring(BEGIN_INDEX_OF_AUTH_HEADER);
    username = jwtService.extractUsername(jwt);
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      if (jwtService.isTokenValid(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
      }
    }
  }
}
