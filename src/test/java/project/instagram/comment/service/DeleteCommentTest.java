package project.instagram.comment.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import project.instagram.comment.dto.CommentRequestDto;
import project.instagram.domain.Comment;
import project.instagram.domain.Post;
import project.instagram.domain.User;
import project.instagram.exception.customexception.CommentNotFoundException;
import project.instagram.exception.customexception.UserNotCorrectException;
import project.instagram.post.PostRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class DeleteCommentTest {
    private Post post1;
    private Comment comment1;
    private User user1;
    private Comment childComment;
    private CommentRequestDto commentRequestDto;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;



    @BeforeEach
    void setup() {
        user1 = new User().builder()
                .username("username1")
                .nickname("nickname1")
                .build();
        post1 = new Post().builder()
                .content("post1")
                .build();
        comment1 =Comment.builder()
                .content("comment1")
                .user(user1)
                .build();
        user1 = userRepository.save(user1);
        post1 = postRepository.save(post1);
        post1.getComments().add(comment1);
        postRepository.findById(post1.getId()).get().getComments().get(0);
        comment1 = commentRepository.save(comment1);
        commentRequestDto = new CommentRequestDto().builder()
                .content("안녕하세요")
                .build();

        for(int i=0; i<5; i++){
            commentRequestDto = new CommentRequestDto().builder()
                    .content("안녕하세요" +i)
                    .build();
            childComment = new Comment(commentRequestDto);
            childComment = commentRepository.save(childComment);

            commentService.registerRecomment(post1.getId(), comment1.getId(), commentRequestDto);

        }
    }

    @Nested
    @DisplayName("Delete_Fail")
    class DeleteFail{
        @Test
        @DisplayName("User_Not_Correct")
        void fail_user_not_correct(){
            Assertions.assertThrows(UserNotCorrectException.class, ()->commentService.deleteComment());
        }

        @Test
        @DisplayName("Comment_Not_Found")
        void fail_comment_not_found(){
            Assertions.assertThrows(CommentNotFoundException.class, ()->commentService.deleteComment());
        }
    }

    @Nested
    @DisplayName("Delete_Success")
    class DeleteSuccess{
        @Test
        @DisplayName("Delete_Success")
        void delete_success(){
            ResponseEntity result = commentService.deleteComment();

            assertEquals(result.getStatusCode(),ResponseEntity.ok());
            Assertions.assertThrows(CommentNotFoundException.class,
                    ()->commentService.getCommentByCommentId(comment1.getId()));
            Assertions.assertThrows(CommentNotFoundException.class,
                    ()->commentService.getCommentByCommentId(childComment.getId()));
        }
    }
}
