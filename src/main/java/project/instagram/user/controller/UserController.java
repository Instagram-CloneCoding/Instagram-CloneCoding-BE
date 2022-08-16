package project.instagram.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.instagram.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/follow")
    public String follow(@PathVariable("toUserId") Long toUserId) {
        return userService.follow(toUserId);
    }
}
