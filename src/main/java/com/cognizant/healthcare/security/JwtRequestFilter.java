package com.cognizant.healthcare.security;
 
import java.io.IOException;
 
 
import java.util.List;
//import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
 
import com.cognizant.healthcare.util.JwtUtil;
 
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 
@Component
 
public class JwtRequestFilter extends OncePerRequestFilter {
 
	@Autowired
 
	private JwtUtil jwtUtil;
 
	@Autowired
 
	private UserDetailsService userDetailsService;
 
//	@Override
//
//	protected void doFilterInternal(HttpServletRequest request,
//
//			HttpServletResponse response,
//
//			FilterChain filterChain) throws ServletException, IOException {
//
//		String authHeader = request.getHeader("Authorization");
//
//		if (authHeader != null && authHeader.startsWith("Bearer ")) {
//
//			String token = authHeader.substring(7);
//
//			String name = jwtUtil.extractUsername(token);
//
//			if (name != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//				UserDetails userDetails =
//						userDetailsService.loadUserByUsername(name);
//
//				if (jwtUtil.validateToken(token)) {
//					String role = jwtUtil.extractRole(token);
//					List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
//
//					/*
//					 * List<GrantedAuthority> authorities = jwtUtil.extractRoles(token).stream()
//					 *
//					 * .map(SimpleGrantedAuthority::new)
//					 *
//					 * .collect(Collectors.toList());
//					 */
//
//					UsernamePasswordAuthenticationToken authToken =
//
//							new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
//
//					SecurityContextHolder.getContext().setAuthentication(authToken);
//
//				}
//
//			}
//
//		}
//
//		filterChain.doFilter(request, response);
 
	//}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {
 
	    // Skip JWT validation for public endpoints (like /api/auth/**)
	    String path = request.getRequestURI();
	    if (path.startsWith("/api/auth/")) {
	        filterChain.doFilter(request, response);
	        return;
	    }
 
	    String authHeader = request.getHeader("Authorization");
 
	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        String token = authHeader.substring(7);
	        String name = jwtUtil.extractUsername(token);
 
	        if (name != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails userDetails = userDetailsService.loadUserByUsername(name);
 
	            if (jwtUtil.validateToken(token)) {
	                String role = jwtUtil.extractRole(token);
	                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
 
	                UsernamePasswordAuthenticationToken authToken =
	                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
 
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
	        }
	    }
 
	    filterChain.doFilter(request, response);
	}
 
}
 
 