package project.instagram.comment.service.test;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.instagram.comment.dto.CommentRequestDto;
import project.instagram.comment.repository.CommentRepository;
import project.instagram.comment.service.CommentService;
import project.instagram.domain.Comment;
import project.instagram.domain.Post;
import project.instagram.exception.customexception.CommentNotFoundException;
import project.instagram.exception.customexception.PostNotFoundException;
import project.instagram.post.PostRepository;
import project.instagram.post.PostService;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class RegisterRecommentTest {
    private Comment comment;
    private Post post;
    private CommentRequestDto commentRequestDto;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;


    @BeforeEach
    void setup() {
        post = new Post().builder()
                .content("post1")
                .build();
        comment = new Comment().builder()
                .content("comment1")
                .build();
        post = postRepository.save(post);
        post.getComments().add(comment);
        postRepository.findById(post.getId()).get().getComments().get(0);
        comment = commentRepository.save(comment);
    }

    @AfterEach
    void clear() {
        postRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Nested
    @DisplayName("해당 포스트가 없을때 ")
    class PostNotExists {
        @Test
        @DisplayName("register Fail")
        void PostNotExists() {
            commentRequestDto = new CommentRequestDto().builder()
                    .content("안녕하세요")
                    .build();
            Assertions.assertThrows(PostNotFoundException.class,
                    () -> commentService.registerRecomment(post.getId() + 1, comment.getId(), commentRequestDto));
        }
    }

    @Nested
    @DisplayName("부모 댓글이 삭제되었을 때")
    class ParentCommentNotExists {
        @Test
        @DisplayName("register Fail")
        void parentCommentNotExists() {
            commentRequestDto = new CommentRequestDto().builder()
                    .content("안녕하세요")
                    .build();

            Assertions.assertThrows(CommentNotFoundException.class,
                    () -> commentService.registerRecomment(post.getId(), comment.getId() + 1, commentRequestDto));
        }
    }

    @Nested
    @DisplayName("댓글이 허용범위 이상일 떄")
    class TooLongContent {
        @Test
        @DisplayName("Too Long Content_Fail")
        void TooLongContent() {
        }
    }

    @Nested
    @DisplayName("대댓글 작성 성공")
    class register_Recomment_Success {
        @Test
        @DisplayName("register Success")
        void registerRecomment() {
            commentRequestDto = new CommentRequestDto().builder()
                    .content("안녕하세요")
                    .build();
            commentService.registerRecomment(post.getId(), comment.getId(), commentRequestDto);
            assertEquals(commentRequestDto.getContent(),
                    postService.getPostByPostId(post.getId()).getComments().get(0).getChildren().get(0).getContent());
            assertEquals(comment.getContent(),
                    postService.getPostByPostId(post.getId()).getComments().get(0).getChildren().get(0).getParent().getContent());
        }
    }

}


