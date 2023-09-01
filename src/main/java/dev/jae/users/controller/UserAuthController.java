package dev.jae.users.controller;

import dev.jae.security.TokenGenerator;
import dev.jae.security.models.Token;
import dev.jae.users.models.ExistingUser;
import dev.jae.users.models.NewUser;
import dev.jae.users.models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class UserAuthController {

    private final UserDetailsManager userDetailsManager;

    private final TokenGenerator tokenGenerator;

    private final DaoAuthenticationProvider daoAuthenticationProvider;


    private final JwtAuthenticationProvider refreshTokenProvider;

    public UserAuthController(
            @Qualifier("jwtRefreshTokenAuthProvider") JwtAuthenticationProvider refreshTokenProvider,
            UserDetailsManager userDetailsManager,
            TokenGenerator tokenGenerator,
            DaoAuthenticationProvider daoAuthenticationProvider) {
        this.userDetailsManager = userDetailsManager;
        this.tokenGenerator = tokenGenerator;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.refreshTokenProvider = refreshTokenProvider;
    }


    @PostMapping("/register")
    public ResponseEntity<Token> register(@RequestBody NewUser newUser) {
        User user = new User(newUser.getUsername(), newUser.getPassword());
        userDetailsManager.createUser(user);

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, newUser.getPassword(), Collections.EMPTY_LIST);

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody ExistingUser existingUser) {
        Authentication authentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken
                .unauthenticated(existingUser.getUsername(), existingUser.getPassword()));
        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/token")
    public ResponseEntity<Token> token(@RequestBody Token token) {
        Authentication authentication = refreshTokenProvider.authenticate(new BearerTokenAuthenticationToken(token.getRefreshToken()));
        //TODO: Check if present in db and not revoked, active, etc
        Jwt jwt = (Jwt) authentication.getCredentials();

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));

    }

}
