package com.github.kelvinspatola.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.kelvinspatola.data.vo.v1.security.TokenVO;
import com.github.kelvinspatola.exceptions.InvalidJwtAuthenticationException;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtTokenProvider {
	
	// vai ler essas propriedades do application.yml. o default é para o caso de não ter nada definido no ficheiro
	@Value("${security.jwt.token.secret-key:secret}") 
	private String secretKey = "secret";
	
	@Value("${security.jwt.token.expire-length:3600000}") 
	private long validityInMilliseconds = 3600000; // 1h
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	private Algorithm algorithm = null;
	
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		algorithm = Algorithm.HMAC256(secretKey.getBytes());
	}
	
	public TokenVO createAccessToken(String username, List<String> roles) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);
		
		var accessToken = getAccessToken(username, roles, now, validity);
		var refreshToken = getRefreshToken(username, roles, now);
		
		return new TokenVO(username, true, now, validity, accessToken, refreshToken);
	}
	
	public TokenVO refreshToken(String refreshToken) {
		if (refreshToken.contains("Bearer ")) {
			refreshToken = refreshToken.replaceAll("Bearer ", "");
		}
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = verifier.verify(refreshToken);
		String username = decodedJWT.getSubject();
		List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
		return createAccessToken(username, roles);
	}

	private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
		String issuerUser = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validity)
				.withSubject(username)
				.withIssuer(issuerUser)
				.sign(algorithm)
				.strip();
	}
	
	private String getRefreshToken(String username, List<String> roles, Date now) {
		Date validityRefreshToken = new Date(now.getTime() + (validityInMilliseconds * 3));
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(validityRefreshToken)
				.withSubject(username)
				.sign(algorithm)
				.strip();
	}
	
	public Authentication getAuthentication(String token) {
		DecodedJWT decodedJWT = decodedToken(token);
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
		
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private DecodedJWT decodedToken(String token) {
		var algo = Algorithm.HMAC256(secretKey.getBytes());
		JWTVerifier verifier = JWT.require(algo).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		return decodedJWT;
	}
	
	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.replaceAll("Bearer ", "");
		}
		return null;
	}
	
	public boolean validateToken(String token) {
		try {
			return !decodedToken(token).getExpiresAt().before(new Date());
		} catch (Exception e) {
			throw new InvalidJwtAuthenticationException("Expired or invalid JWT token!");
		}
	}
}
