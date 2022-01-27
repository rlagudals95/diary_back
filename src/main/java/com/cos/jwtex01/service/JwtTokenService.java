package com.cos.jwtex01.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.cos.jwtex01.config.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

@Component
@SuppressWarnings("serial")
public class JwtTokenService implements Serializable {

	public String getAdminIdFromToken(String token) {
    	Claims claims =  getClaimFromToken(token);
    	return claims.get("admin_id") != null ? claims.get("admin_id").toString() : null;
    }

    public Long getAdminNoFromToken(String token) {
    	Claims claims =  getClaimFromToken(token);
    	return claims.get("admin_no") != null ? Long.parseLong(claims.get("admin_no").toString()) : 0;
    }

	public String getMemberIdFromToken(String token) {
    	Claims claims =  getClaimFromToken(token);
    	return claims.get("member_id") != null ? claims.get("member_id").toString() : null;
    }

    public Long getMemberNoFromToken(String token) {
    	Claims claims =  getClaimFromToken(token);
    	return claims.get("member_no") != null ? Long.parseLong(claims.get("member_no").toString()) : 0;
    }

    public String getJoinTypeFromToken(String token) {
    	Claims claims =  getClaimFromToken(token);
    	return claims.get("join_type") != null ? claims.get("join_type").toString() : null;
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

	public String getReqHpFromToken(String token) {
    	Claims claims =  getClaimFromToken(token);
    	return claims.get("req_hp") != null ? claims.get("req_hp").toString() : null;
    }

	public String getAuthCodeFromToken(String token) {
    	Claims claims =  getClaimFromToken(token);
    	return claims.get("auth_code") != null ? claims.get("auth_code").toString() : null;
    }
    public Claims getClaimFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims;
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(Constants.SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String doGenerateTokenMember(long member_no, String member_id, String join_type) {

        Claims claims = Jwts.claims();
        claims.put("member_id", member_id);
        claims.put("member_no", member_no);
        claims.put("join_type", join_type);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_SELLER")));

        return Jwts.builder()
        		 .setClaims(claims)
        		 .setIssuedAt(new Date(System.currentTimeMillis()))
        	        .signWith(SignatureAlgorithm.HS512, Constants.SIGNING_KEY)
        	        .setExpiration(new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_VALIDITY_SECONDS*1000))
        	        .compact();

    }

    public String doGenerateTokenAdmin(long admin_no, String admin_id) {

        Claims claims = Jwts.claims();
        claims.put("admin_id", admin_id);
        claims.put("admin_no", admin_no);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_SELLER")));

        return Jwts.builder()
        		 .setClaims(claims)
        		 .setIssuedAt(new Date(System.currentTimeMillis()))
        	        .signWith(SignatureAlgorithm.HS512, Constants.SIGNING_KEY)
        	        .setExpiration(new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_VALIDITY_SECONDS*1000))
        	        .compact();

    }

    public String doGenerateTokenReqHp(String req_hp,String auth_code,String issue_date) {

        Claims claims = Jwts.claims();
        claims.put("req_hp", req_hp);
        claims.put("auth_code", auth_code);
        claims.put("issue_date", issue_date);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_SELLER")));

        return Jwts.builder()
        		 .setClaims(claims)
        		 .setIssuedAt(new Date(System.currentTimeMillis()))
        	        .signWith(SignatureAlgorithm.HS512, Constants.SIGNING_KEY)
        	        .setExpiration(new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_VALIDITY_SECONDS*1000))
        	        .compact();
    }

    public Boolean validateTokenMember(String token) {
        final String sellerId = getMemberIdFromToken(token);
        return (sellerId != null && !isTokenExpired(token));
    }

    public Boolean validateTokenAdmin(String token) {
        final String sellerId = getMemberIdFromToken(token);
        return (sellerId != null && !isTokenExpired(token));
    }

    public Boolean validateTokenReqHp(String token) {
        final String sellerId = getReqHpFromToken(token);
        return (sellerId != null && !isTokenExpired(token));
    }
}
