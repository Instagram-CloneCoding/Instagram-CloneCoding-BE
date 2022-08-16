package project.instagram.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.instagram.user.repository.FollowRepository;
import project.instagram.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public String follow(Long toUserId) {

        return null;
    }
}
