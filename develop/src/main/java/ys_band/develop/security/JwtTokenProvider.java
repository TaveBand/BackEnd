package ys_band.develop.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider implements InitializingBean{
    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static final String AUTH_KEY = "auth";
    private final String secret;
    private Key key;
    private final long tokenValid;

    /*public JwtTokenProvider(
            @Value("${jwt.secret.key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }*/
    public JwtTokenProvider(
            @Value("${jwt.secret.key}") String secret,
            @Value("${jwt.token-validity}") long tokenValid) {
        this.secret = secret;
        this.tokenValid = tokenValid*1000;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //'Authentication'객체는 사용자의 인증정보 - Principal(사용자 주체, UserDetails 객체나 사용자 이름(String))
    //Credentials(비밀번호 같은 자격증명), Authorities, Authenticated 등의 주요 요소를 포함.
    public String createToken(Authentication authentication){
        String auths = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValid);

        return Jwts.builder()
                //.setClaims(claims)
                .setSubject(authentication.getName())
                .claim(AUTH_KEY,auths)
                .setIssuedAt(new Date())
                .setIssuer("Dail Band")
                .signWith(key,SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.", e);
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 서명입니다.", e);
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.", e);
        }
        return false;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }




    /////////////////////////////////////////////////////////////////////////////////////
    public Authentication getAuthentication(String token) {

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTH_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        //UserDetails 객체 만들어서 Authentication 리턴
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);


    }
}

    /*@Override
    public void afterPropertiesSet() throws Exception {

    }

     */
    /*private  Claims parseClaims(String token){
        try{
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }

        }
    }*/

