package org.example.rentapplicationbe.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.rentapplicationbe.model.Entity.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    public String generateToken(Account user){
        long expirationTime = System.currentTimeMillis() + 86400000L * 10L;
        Map<String,Object> claims = new HashMap<>();
        String se = "73608685541105edccb3ea757e3e6487d30f71977c74db789b5df252d85d598b";
        byte[] bytes = Decoders.BASE64.decode(se);
        claims.put("nameAccount",user.getUsername());
        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getUsername())
                    .setExpiration(new Date(expirationTime))
                    .signWith(Keys.hmacShaKeyFor(bytes), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        } catch (Exception e){
            throw new InvalidParameterException("khong the tao ra  jwt token bi loi r: " + e.getMessage());

        }

    }

    public Key getSignKey() {
        String se = "73608685541105edccb3ea757e3e6487d30f71977c74db789b5df252d85d598b";
      byte[] bytes = Decoders.BASE64.decode(se);

        return Keys.hmacShaKeyFor(bytes);
    }
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public  <T> T extractClaim(String token, Function<Claims,T> claimsTResolver){
     final Claims claims =  this.extractAllClaims(token);
       return claimsTResolver.apply(claims);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);

    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }



}
