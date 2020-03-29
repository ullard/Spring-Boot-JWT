package com.demo.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.demo.jwt.config.JwtTokenUtil;
import com.demo.jwt.service.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;

/** Executed for any incoming requests and validate JWT from the request and sets it in the context to indicate that logged in user is authenticated. */
@Component
public class JwtRequestFilter extends OncePerRequestFilter
{

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
	{
		Cookie token = WebUtils.getCookie(request, "token");

		String username = null;

		if (token != null)
		{
			try
			{
				username = jwtTokenUtil.getUsernameFromToken(token.getValue());
			} catch (IllegalArgumentException ex)
			{
				System.out.println("Unable to get JWT Token"); // i18n
			} catch (ExpiredJwtException ex)
			{
				System.out.println("JWT Token has expired"); // i18n
			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
			{
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

				// if token is valid configure Spring Security to manually set authentication
				if (jwtTokenUtil.validateToken(token.getValue(), userDetails))
				{
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}

		filterChain.doFilter(request, response);
	}

}
