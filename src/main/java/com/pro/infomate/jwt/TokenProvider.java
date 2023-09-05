package com.pro.infomate.jwt;

import com.pro.infomate.exception.TokenException;
import com.pro.infomate.member.dto.TokenDTO;
import com.pro.infomate.member.entity.AuthList;
import com.pro.infomate.member.entity.Member;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private static final String BEARER_TYPE = "Bearer";

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;

    private final Key key;

    private final UserDetailsService userDetailsService;
    public TokenProvider(@Value("${jwt.secret}") String secretKey, @Lazy UserDetailsService userDetailsService) {

        byte[] keyBytest = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytest);
        this.userDetailsService = userDetailsService;
    }


    public TokenDTO generateTokenDTO(Member member){
        log.info("[TokenProvider] generateTokenDTO Start ================== = = = = = = == = = = =");
        List<String> roles = new ArrayList<>();
        for (AuthList authList : member.getAuthList()){
            roles.add(authList.getAuthority().getAuthorityName());
        }

        log.info("[TokenProvider] authorities  {}", roles);

        Claims claims = Jwts.claims().setSubject(member.getMemberId());

        claims.put(AUTHORITIES_KEY, roles);

        long now = System.currentTimeMillis();

        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        log.info("[TokenProvider] generateTokenDTO End = == = = = = = == = = = = == = = == = = == = ");

        return new TokenDTO(BEARER_TYPE, member.getMemberName(), member.getDepartment().getDeptName(), member.getRank().getRankName(), accessToken, accessTokenExpiresIn.getTime());
    }

    public String getUserId(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Authentication getAuthentication(String token){

        log.info("[TokenProvider] getAuthentication Start ========");

        Claims claims = parseClaims(token);

        if(claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
        log.info("[TokenProvider] = = == == = == = = === = = = =", userDetails.getAuthorities());

        log.info("[TokenProvider] getAuthentication End = = = = = = = == = = = = == = ");
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            log.info("[TokenProvider] 잘못된 JWT 서명입니다.");
            throw new TokenException("잘못된 JWT 서명입니다.");
        }catch(ExpiredJwtException e){
            log.info("[TokenProvider] 만료된 JWT 토큰입니다.");
            throw new TokenException("만료된 JWT 토큰입니다.");
        }catch(UnsupportedJwtException e){
            log.info("[TokenProvider] 지원되지 않는 JWT 토큰입니다.");
            throw new TokenException("지원되지 않는 JWT 토큰입니다.");
        }catch(IllegalArgumentException e){
            log.info("[TokenProvider] JWT 토큰이 잘못되었습니다.");
            throw new TokenException("JWT 토큰이 잘못되었습니다.");
        }
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }


}
