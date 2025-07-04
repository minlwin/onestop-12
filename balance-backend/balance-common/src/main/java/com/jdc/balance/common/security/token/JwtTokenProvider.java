package com.jdc.balance.common.security.token;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.jdc.balance.common.exception.JwtTokenExpirationException;
import com.jdc.balance.common.exception.JwtTokenInvalidationException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Service
public class JwtTokenProvider {
	
	public enum Type {
		Access, Refresh
	}

	private static final String TYPE = "typ";
	private static final String ROLE = "rol";
	
	@Value("${app.token.issuer}")
	private String issuer;
	@Value("${app.token.audiance}")
	private String audiance;
	@Value("${app.token.access-life}")
	private int accessLife;
	@Value("${app.token.refresh-life}")
	private int refreshLife;

	private SecretKey secretKey = Jwts.SIG.HS512.key().build();

	/**
	 * Generate Access Token
	 * 
	 * @param authentication
	 * @return Access Token
	 */
	public String generateAccessToken(Authentication authentication) {
		return generateToken(Type.Access, authentication);
	}

	/**
	 * Generate Refresh Token
	 * 
	 * @param authentication
	 * @return Refresh Token
	 */
	public String generateRefreshToken(Authentication authentication) {
		return generateToken(Type.Refresh, authentication);
	}

	/**
	 * Parse Access Token
	 * 
	 * @param token
	 * @return Authentication
	 */
	public Authentication parseAccessToken(String token) {
		return parseToken(Type.Access, token);
	}

	/**
	 * Parse Refresh Token
	 * 
	 * @param token
	 * @return Authentication
	 */
	public Authentication parseRefreshToken(String token) {
		return parseToken(Type.Refresh, token);
	}
	
	private String generateToken(Type type, Authentication authentication) {
		
		var issueAt = new Date();
		var expiration = Calendar.getInstance();
		expiration.setTime(issueAt);
		expiration.add(Calendar.MINUTE, type == Type.Access ? accessLife : refreshLife);
		
		return Jwts.builder()
			.issuer(issuer)
			.audience().add(audiance).and()
			.issuedAt(issueAt)
			.expiration(expiration.getTime())
			.claim(TYPE, type)
			.signWith(secretKey)
			.subject(authentication.getName())
			.claim(ROLE, authentication.getAuthorities().stream().map(a -> a.getAuthority())
					.collect(Collectors.joining(",")))
			.compact();
	}

	private Authentication parseToken(Type type, String token) {
		
		try {
			var jwt = Jwts.parser()
					.verifyWith(secretKey)
					.requireAudience(audiance)
					.requireIssuer(issuer)
					.build()
					.parseSignedClaims(token);
				
				var tokenType = jwt.getPayload().get(TYPE, String.class);
				
				if(!type.name().equals(tokenType)) {
					throw new JwtTokenInvalidationException("Invalid usage of token type.");
				}
				
				var username = jwt.getPayload().getSubject();
				var roles = jwt.getPayload().get(ROLE, String.class);
				
				return UsernamePasswordAuthenticationToken
						.authenticated(username, null, 
								Arrays.stream(roles.split(","))
								.map(SimpleGrantedAuthority::new).toList());
		} catch(ExpiredJwtException e) {
			throw new JwtTokenExpirationException("%s token is expired.".formatted(type), e);
		} catch(JwtException e) {
			throw new JwtTokenInvalidationException("Invalid %s token.".formatted(type), e);
		}
	}
}
