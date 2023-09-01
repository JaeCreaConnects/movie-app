package dev.jae.users.controller;

import dev.jae.users.models.User;
import dev.jae.users.models.UserDto;
import dev.jae.users.repo.UserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @GetMapping ("/{id}")
    @PreAuthorize("#user.id == #id")
    public ResponseEntity<UserDto> user(@AuthenticationPrincipal User user, @PathVariable String id){
        return ResponseEntity.ok(UserDto.from(userRepo.findById(user.getId()).orElseThrow()));
    }
}
