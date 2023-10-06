package com.bookingService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenService {

    private final String secretKey="jwtTokenKey";

    public String generateToken(UserDetails userDetails){
        HashMap<String,Object> claims=new HashMap<>();
       return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String,Object> claims, String subject){

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000*30*60))
                .signWith(SignatureAlgorithm.HS512,secretKey).compact();

    }



    //Retrieve any information from token we need secret key
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public <T> T getClaimsFromToken(String token, Function<Claims,T> claimsResolver){
       final Claims claims = getAllClaimsFromToken(token);
       return claimsResolver.apply(claims);
    }

    //Retrieve User name from token

    public String getUserNameFromToken(String token){
        return getClaimsFromToken(token,Claims::getSubject);
    }

    //Retrieve Expiration Date from Token

    public Date getExpirationDateFromToken(String token){
        return getClaimsFromToken(token,Claims::getExpiration);
    }

    //Check if the Token Expired

    public boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
       return expiration.before(new Date());
    }


    //validate token

    public boolean validateToken(String token,UserDetails userDetails){
        String userName = getUserNameFromToken(token);

        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
