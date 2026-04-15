package com.example.px_service.util;

import com.example.px_service.config.JwtProperties;
import com.example.px_service.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private static final long EXPIRE = 1000 * 60 * 60; // 1小时
    private static final String MODULE_CLAIM_KEY = "module";
    private static final String SITE_ID_KEY = "site_id";
    private static final String CHANNEL_ID_KEY = "channel_id";
    private static final String USERNAME_KEY = "username";
    private final SecretKey key;

    public JwtUtil(JwtProperties jwtProperties) {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getKeySecret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成 token
     *
     * @param user
     * @param module
     * @return
     */
    public String generateToken(User user, @Nullable String module) {
        if (module == null) {
            module = "admin";
        }

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim(MODULE_CLAIM_KEY, module)
                .claim(SITE_ID_KEY, user.getSiteId())
                .claim(CHANNEL_ID_KEY, user.getChannelId())
                .claim(USERNAME_KEY, user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    /**
     * 解析 token
     *
     * @param token
     * @return
     */
    public Long parseToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return Long.parseLong(claims.getSubject());
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return Long.parseLong(claims.getSubject());
    }

    public Long getSiteIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        Number siteId = claims.get(SITE_ID_KEY, Number.class);
        return siteId == null ? null : siteId.longValue();
    }

    public Long getChannelIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        Number channelId = claims.get(CHANNEL_ID_KEY, Number.class);
        return channelId == null ? null : channelId.longValue();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get(USERNAME_KEY, String.class);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
