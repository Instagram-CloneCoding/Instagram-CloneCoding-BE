package project.instagram.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.instagram.domain.Follow;
import project.instagram.domain.User;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    //test
    Follow findByFromUser(User fromUser);
    Optional<Follow> findByFromUserAndToUser(User user1, User user2);
}
