package project.instagram.heartcomment.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import project.instagram.comment.dto.CommentRequestDto;
import project.instagram.comment.service.CommentService;
import project.instagram.comment.service.UserRepository;
import project.instagram.commentHeart.service.HeartCommentService;
import project.instagram.domain.Comment;
import project.instagram.domain.Post;
import project.instagram.domain.User;
import project.instagram.exception.customexception.CommentNotFoundException;
import project.instagram.post.PostRepository;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class RegisterHeartCommentTest {

    @Autowired private CommentService commentService;
    @Autowired private UserRepository userRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private HeartCommentService heartCommentService;
    private Post post;
    private Comment comment;
    private User user;

    @BeforeEach
    void setup(){
        post = new Post().builder()
                .content("post_content1")
                .build();
        post = postRepository.save(post);

        user = new User().builder()
                .username("user_username1")
                .nickname("user_nickname1")
                .build();
        user = userRepository.save(user);

        CommentRequestDto commentRequestDto = new CommentRequestDto().builder()
                .content("comment_content1").build();
        commentService.registerComment(post.getId(),commentRequestDto,user);
        comment = postRepository.findById(post.getId()).get().getComments().get(0);
    }


    @Nested
    @DisplayName("Register Fail")
    class register_fail{
        @DisplayName("CommentNotFound")
        @Test
        void fail_comment_not_found(){
            commentService.deleteComment(comment.getId(),user);
            Assertions.assertThrows(CommentNotFoundException.class,
                    ()-> heartCommentService.registerHeartComment(comment.getId(),user));
        }

        @DisplayName("PostDeleted")
        @Test
        void fail_post_deleted(){
            postRepository.deleteById(post.getId());
            Assertions.assertThrows(CommentNotFoundException.class,
                    ()-> heartCommentService.registerHeartComment(comment.getId(),user));
        }
    }
    @Nested
    @DisplayName("Register Success")
    class register_success{
        @DisplayName("Press HeartComment")
        @Test
        void success_press_heartComment(){
            ResponseEntity result = heartCommentService.registerHeartComment(comment.getId(),user);
            Assertions.assertEquals(result,ResponseEntity.ok(true));
            Assertions.assertEquals(true,heartCommentService.alreadyHeartCommentPressed(comment,user));
        }

        @DisplayName("Unpress HeartComment")
        @Test
        void success_unpress_heartComment(){
            heartCommentService.registerHeartComment(comment.getId(), user);

            ResponseEntity result = heartCommentService.registerHeartComment(comment.getId(),user);
            Assertions.assertEquals(result,ResponseEntity.ok(false));
            Assertions.assertEquals(false,heartCommentService.alreadyHeartCommentPressed(comment,user));
        }
    }
}
