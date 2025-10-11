package com.server.spring.security.jwt.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.server.spring.security.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

    /**
     * The signing key for JWT tokens, injected from application properties.
     * */
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    /**
     * The expiration time for JWT tokens in milliseconds, injected from application properties.
     * */
    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /** (non-Javadoc)
     * @see com.server.spring.security.jwt.service.JwtService#generateToken(org.springframework.security.core.userdetails.UserDetails)
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /** (non-Javadoc)
     * @see com.server.spring.security.jwt.service.JwtService#isTokenValid(java.lang.String, org.springframework.security.core.userdetails.UserDetails)
     */
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Extracts a specific claim from the JWT token using the provided claims resolver function.
     *
     * @param <T> The type of the claim to be extracted.
     * @param token The JWT token from which to extract the claim.
     * @param claimsResolvers A function that takes Claims and returns the desired claim.
     * @return The extracted claim of type T.
     * */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Generates a JWT token with the specified extra claims and user details.
     *
     * @param extraClaims A map of additional claims to include in the token.
     * @param userDetails The user details containing the username for the token subject.
     * @return The generated JWT token as a String.
     * */
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token The JWT token to check for expiration.
     * @return true if the token is expired, false otherwise.
     * */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token The JWT token from which to extract the expiration date.
     * @return The expiration date of the token.
     * */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token from which to extract claims.
     * @return The Claims object containing all claims from the token.
     * */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the signing key used for JWT token signing and verification.
     *
     * @return The signing Key object.
     * */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
