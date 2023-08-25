package dev.jae.security;

import dev.jae.security.models.Token;
import dev.jae.users.models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class TokenGenerator {

    private final JwtEncoder accessTokenEncoder;

    @Qualifier("jwtRefreshTokenEncoder")
    private final JwtEncoder refreshTokenEncoder;

    public TokenGenerator(JwtEncoder accessTokenEncoder, JwtEncoder refreshToken) {
        this.accessTokenEncoder = accessTokenEncoder;
        this.refreshTokenEncoder = refreshToken;
    }

    private String createAccessToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimSet = JwtClaimsSet.builder()
                .issuer("movie-app")
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .subject(user.getId())
                .build();
        return accessTokenEncoder.encode(JwtEncoderParameters.from(claimSet)).getTokenValue();
    }

    private String createRefreshToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Instant now = Instant.now();

        JwtClaimsSet claimSet = JwtClaimsSet.builder()
                .issuer("movie-app")
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.DAYS))
                .subject(user.getId())
                .build();
        return refreshTokenEncoder.encode(JwtEncoderParameters.from(claimSet)).getTokenValue();
    }

    public Token createToken(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof User user)) {
            throw new BadCredentialsException(
                    MessageFormat.format("Principal {0} is not of type User", authentication.getPrincipal().getClass())
            );

        }
        Token token = new Token();
        token.setUserId(user.getId());
        token.setAccessToken(createAccessToken(authentication));

        String refreshToken;

        if (authentication.getCredentials() instanceof Jwt jwt){
            Instant now = Instant.now();
            Instant expiresAt = jwt.getExpiresAt();
            Duration duration = Duration.between(now, expiresAt);

            long daysUntilExpired = duration.toDays();

            if (daysUntilExpired < 7){
                refreshToken = createRefreshToken(authentication);
            }
            else{
                refreshToken = jwt.getTokenValue();
            }
        }
        else{
            refreshToken = createRefreshToken(authentication);
        }
        token.setRefreshToken(refreshToken);
        return token;
    }
}
