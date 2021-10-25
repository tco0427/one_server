package dgrowth.com.one_server.util;

import dgrowth.com.one_server.data.dto.response.TokenResponse;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil implements Serializable {
    @Value("${spring.jwt.secret}")
    private String JWT_SECRET_KEY;

    @Value("${spring.jwt.subject}")
    private String JWT_SUBJECT;

    @Value("${spring.jwt.claims.user-id}")
    private String USER_ID_CLAIM;

    @Value("${spring.jwt.claims.role}")
    private String USER_ROLE_CLAIM;

    @Value("${spring.jwt.claims.login-id}")
    private String LOGIN_ID_CLAIM;

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;    //30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; //7일

    /**
     * 토큰 발급
     */
    public TokenResponse generateToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        String accessToken = Jwts.builder().setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY,authorities).setSubject(JWT_SUBJECT).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY).compact();

        String refreshToken = Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY).compact();

        return TokenResponse.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {

        Claims claims = Jwts
                .parser()
                .setSigningKey(JWT_SECRET_KEY)
                .parseClaimsJws(accessToken)
                .getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token);
            return true;
        }catch(ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        }catch(UnsupportedJwtException e){
            log.info("지원되지 않는 JWT 토큰입니다.");
        }catch(IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    /**
     * 토큰에 저장된 정보(claim) 얻기
     * @param token String
     * @param claimsResolver Function<
     * @return T
     */
    public <T> T getClaimByToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsByToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 토큰에 저장된 정보(claim) 얻기
     * @param token String
     * @return Claims
     */
    private Claims getAllClaimsByToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * 토큰 만료일 얻기
     * @param token String
     * @return Date
     */
    public Date getExpirationDateByToken(String token){
        return getClaimByToken(token, Claims::getExpiration);
    }

    /**
     * 토큰값에서 유저 아이디를 가져옴
     * @param token String
     * @return Long
     */
    public Long getUserIdByToken(String token){
        Claims claims = getAllClaimsByToken(token);
        return claims.get(USER_ID_CLAIM, Long.class);
    }

    /**
     * 토큰값에서 권한을 가져옴
     * @param token String
     * @return String
     */
    public String getRoleByToken(String token){
        Claims claims = getAllClaimsByToken(token);
        return claims.get(USER_ROLE_CLAIM, String.class);
    }

    /**
     * 토큰값에서 유저 아이 가져옴
     * @param token String
     * @return String
     */
    public String getLoginIdByToken(String token){
        Claims claims = getAllClaimsByToken(token);
        return claims.get(LOGIN_ID_CLAIM, String.class);
    }

    /**
     * 토큰 만료여부 판별
     * @param token String
     * @return boolean
     */
    public Boolean isTokenExpired(String token){
        final Date expirationDate = getExpirationDateByToken(token);
        return expirationDate.before(new Date());
    }
}

