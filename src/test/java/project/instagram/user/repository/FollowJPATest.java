package project.instagram.user.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.instagram.domain.Follow;
import project.instagram.domain.User;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FollowJPATest {

    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void before() {
        user1 = new User("나");
        user2 = new User("너");
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @AfterEach
    void after() {
        followRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("팔로우")
    class followTest {

        @DisplayName("팔로우하기")
        @Test
        void follow() {
            Optional<Follow> existsFollower = followRepository.findByFromUserAndToUser(user1, user2);
            if (!existsFollower.isPresent()) {
                Follow follow = Follow.builder()
                        .fromUser(user1)
                        .toUser(user2)
                        .build();
                followRepository.save(follow);
            }

            Optional<Follow> findFollower = followRepository.findByFromUserAndToUser(user1, user2);

            assertThat(findFollower.isPresent()).isTrue();
        }

        @DisplayName("언팔로우하기")
        @Test
        void unfollow() {
            insertFollowData();

            Optional<Follow> existsFollower = followRepository.findByFromUserAndToUser(user1, user2);
            if (!existsFollower.isPresent()) {
                Follow follow = Follow.builder()
                        .fromUser(user1)
                        .toUser(user2)
                        .build();
                followRepository.save(follow);
            } else {
                followRepository.delete(existsFollower.get());
            }

            Optional<Follow> findFollower = followRepository.findByFromUserAndToUser(user1, user2);

            assertThat(findFollower.isPresent()).isFalse();
        }

        private void insertFollowData() {
            Follow follow = Follow.builder()
                    .fromUser(user1)
                    .toUser(user2)
                    .build();
            followRepository.save(follow);
        }
    }
}
