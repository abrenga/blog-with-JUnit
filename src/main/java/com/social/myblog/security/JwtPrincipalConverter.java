package com.social.myblog.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class JwtPrincipalConverter {
    public UserPrincipal convertPrincipal(DecodedJWT jwt) {
        return UserPrincipal.builder()
                .userId(Integer.parseInt(jwt.getSubject()))
                .username(jwt.getClaim("username").asString())
                .authorities(extractAuthoritiesFromClaim(jwt))
                .build();
    }

    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt) {
        var claims = jwt.getClaim("authorities");
        if(claims == null|| claims.isMissing()) return List.of();
        return claims.asList(SimpleGrantedAuthority.class);
    }
}
