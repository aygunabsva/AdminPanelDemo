package com.example.adminpaneldemo.utility;

import com.example.adminpaneldemo.entity.Authority;
import com.example.adminpaneldemo.entity.Users;
import com.example.adminpaneldemo.exception.NotFoundException;
import com.example.adminpaneldemo.repository.UsersRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.naming.AuthenticationException;
import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@PropertySource("classpath:application.properties")
public class JwtUtil {
    private static Key key;
    private final UsersRepository usersRepository;
    @Value("${application.security.jwt.secret-key}")
    private String secret_key;
    @Value("${application.security.jwt.expiration}")
    private long accessTokenValidity;

    public JwtUtil(UsersRepository userRepository) {
        this.usersRepository = userRepository;
    }

    public Key initializeKey() {
        byte[] keyBytes;
        keyBytes = Decoders.BASE64.decode(secret_key);
        key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }

    public String createToken(Users user) {
        key = initializeKey();
        user = usersRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));
        Jwts.claims().setSubject(user.getEmail());
        Authority authorities = user.getAuthority();

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("authorities", authorities.getName());
        claimsMap.put("email", user.getEmail());
        claimsMap.put("user_id", user.getId());

        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
        final JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(tokenValidity)
                .addClaims(claimsMap)
                .signWith(key, SignatureAlgorithm.HS256);
        log.info("Jwt token created for user: {}", user.getEmail());
        return jwtBuilder.compact();
    }

    private Claims parseJwtClaims(String token) {
        return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            log.error("Error due to: {}", ex.getMessage());
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Error due to: {}", ex.getMessage());
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }
    public String resolveToken(HttpServletRequest request) {

        String TOKEN_HEADER = "Authorization";
        String bearerToken = request.getHeader(TOKEN_HEADER);
        String TOKEN_PREFIX = "Bearer ";
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }
    public Integer getUserId(Claims claims) {
        return (Integer) claims.get("user_id");
    }


    public Collection<GrantedAuthority> extractAuthorities(Claims claims) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (claims.containsKey("authorities")) {
            List<String> roles = (List<String>) claims.get("authorities");
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        return authorities;
    }
}
