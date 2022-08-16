package project.instagram.post.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.instagram.domain.Bookmark;
import project.instagram.domain.Post;
import project.instagram.domain.User;
import project.instagram.user.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookmarkJPATest {
    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    private User user;
    private Post post;

    @BeforeEach
    void before() {
        user = new User("나");
        post = new Post("가나다");
        userRepository.save(user);
        postRepository.save(post);
    }

    @AfterEach
    void after() {
        bookmarkRepository.deleteAll();
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Nested
    @DisplayName("북마크")
    class bookmarkTest {

        @DisplayName("북마크하기")
        @Test
        void bookmark() {
            Optional<Bookmark> existsBookmark = bookmarkRepository.findByUserAndPost(user, post);
            if (!existsBookmark.isPresent()) {
                Bookmark bookmark = Bookmark.builder()
                        .user(user)
                        .post(post)
                        .build();
                bookmarkRepository.save(bookmark);
            }

            Optional<Bookmark> findBookmark = bookmarkRepository.findByUserAndPost(user, post);

            assertThat(findBookmark.isPresent()).isTrue();
        }

        @DisplayName("북마크 취소하기")
        @Test
        void bookmarkCancel() {
            insertBookmarkData();

            Optional<Bookmark> existsBookmark = bookmarkRepository.findByUserAndPost(user, post);
            if (!existsBookmark.isPresent()) {
                Bookmark bookmark = Bookmark.builder()
                        .user(user)
                        .post(post)
                        .build();
                bookmarkRepository.save(bookmark);
            } else {
                bookmarkRepository.delete(existsBookmark.get());
            }

            Optional<Bookmark> findBookmark = bookmarkRepository.findByUserAndPost(user, post);

            assertThat(findBookmark.isPresent()).isFalse();
        }

        private void insertBookmarkData() {
            Bookmark bookmark = Bookmark.builder()
                    .user(user)
                    .post(post)
                    .build();
            bookmarkRepository.save(bookmark);
        }
    }
}