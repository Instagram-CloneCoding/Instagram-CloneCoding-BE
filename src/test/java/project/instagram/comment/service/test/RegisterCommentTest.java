package project.instagram.comment.service.test;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import project.instagram.comment.dto.CommentRequestDto;
import project.instagram.comment.repository.CommentRepository;
import project.instagram.comment.service.CommentService;
import project.instagram.domain.Post;
import project.instagram.exception.customexception.NoContentException;
import project.instagram.exception.customexception.PostNotFoundException;
import project.instagram.post.PostRepository;
import project.instagram.post.PostService;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class RegisterCommentTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    private CommentRequestDto commentRequestDto;
    private Post post;
    private Long postId = 1L;

    @BeforeEach
    void setup() {
        commentService = new CommentService(commentRepository, postRepository,postService);
    }


    @Nested
    @DisplayName("해당 포스트가 없을 때")
    class PostNotExists {
        @Test
        @DisplayName("register fail")
        @Transactional
        void register_Post_Not_Exist() {
            commentRequestDto = new CommentRequestDto().builder()
                    .content("안녕하세요")
                    .build();
            Assertions.assertThrows(PostNotFoundException.class, () -> commentService.registerComment(postId, commentRequestDto));
        }
    }

    @Nested
    @DisplayName("해당 포스트가 있을 때")
    class PostExist {
        @BeforeEach
        void setup() {
            post = postRepository.save(new Post());
        }

        @AfterEach
        void clear() {
            postRepository.deleteAll();
        }

        @DisplayName("No content")
        @Test
//        @Transactional
        void register_Content_Not_Exist() {

            commentRequestDto = new CommentRequestDto().builder()
                    .build();
            Assertions.assertThrows(NoContentException.class,
                    () -> commentService.registerComment(post.getId(), commentRequestDto));
        }

        @DisplayName("register Success")
        @Test
        void register_Success() {

            //given
            commentRequestDto = new CommentRequestDto().builder()
                    .content("안녕하세요")
                    .build();
            //when
            commentService.registerComment(post.getId(),commentRequestDto);

            //then
            Post foundPost = postService.getPostByPostId(post.getId());
            assertEquals(ResponseEntity.ok(true),
                    commentService.registerComment(post.getId(), commentRequestDto));
            assertEquals(foundPost.getComments().get(0).getContent(), commentRequestDto.getContent());
        }


        @DisplayName("Too Long Content")
        @Test
        void register_Too_Long_Content(){
            commentRequestDto = new CommentRequestDto().builder()
                    .content("안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요" +
                            "안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요")
                    .build();
            commentService.registerComment(post.getId(),commentRequestDto);
        }
    }

}
