package dev.jae.users.controller;

import dev.jae.security.TokenGenerator;
import dev.jae.users.models.NewUser;
import dev.jae.users.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {

    private final UserDetailsManager userDetailsManager;

    private final TokenGenerator tokenGenerator;

    public UserAuthController(UserDetailsManager userDetailsManager, TokenGenerator tokenGenerator) {
        this.userDetailsManager = userDetailsManager;
        this.tokenGenerator = tokenGenerator;
    }


    @PostMapping("/register")
    public ResponseEntity register (@RequestBody NewUser newUser){
        User user = new User(newUser.getUsername(), newUser.getPassword());
        userDetailsManager.createUser(user);

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, newUser.getPassword(), Collections.EMPTY_LIST);

        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

}
