package com.sean.auth.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/11/6 9:52
 */
@Slf4j
@Component
public class JwtTokenUtil {

    private transient Clock clock = DefaultClock.INSTANCE;

    @Value("${jwt.secret:secret}")
    private String secret;

    @Value("${jwt.expiration:1}")
    private Long expiration;

    @Value("${jwt.header:token}")
    private String tokenHeader;

//    @Autowired
//    private RedisManager redisManager;

    private ObjectMapper mapper = new ObjectMapper();

    public JwtUserDetails getJwtUserFromToken(String token) throws Exception {
        String subject = getClaimFromToken(token, Claims::getSubject);
        Map<String, Object> subjectMap = mapper.readValue(subject, Map.class);

        // 在token中存储了用户ID 用户名  用户状态
        JwtUserDetails jwtUser = new JwtUserDetails();
        jwtUser.setUserId(Long.valueOf(subjectMap.get("userId").toString()));
        jwtUser.setUsername((String) subjectMap.get("username"));
        jwtUser.setState((Integer) subjectMap.get("state"));

        return jwtUser;
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(clock.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    // 登陆校验成功后调用这个接口生成token下发
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        try {
            String subject = mapper.writeValueAsString(userDetails);
            log.info("generateToken subject:{}", subject);
            String token = doGenerateToken(claims, subject);
//            redisManager.set(CacheAdminConstant.USER_AUTHORITY_NOT_EXPIRED + token, "1", (int) (expiration / 1000));
            return token;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot format json", e);
        }
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) throws Exception {
        JwtUserDetails user = (JwtUserDetails) userDetails;
        final JwtUserDetails jwtUser = getJwtUserFromToken(token);
        return (
                jwtUser.getUsername().equals(user.getUsername())
                        && !isTokenExpired(token));
    }

    private Date calculateExpirationDate(Date createdDate) {
        //过期时间1天
        return new Date(createdDate.getTime() + 1000 * 60 * 60 * 24);
    }

}
