package project.instagram.comment.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.instagram.comment.dto.CommentRequestDto;
import project.instagram.domain.Comment;
import project.instagram.domain.Post;
import project.instagram.domain.User;
import project.instagram.exception.customexception.NoContentException;
import project.instagram.post.PostRepository;

import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
public class GetRecommentListTest {
    private Post post1;
    private Comment comment1;
    private User user1;
    private CommentRequestDto commentRequestDto;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private JPAQueryFactory queryFactory;


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

        commentService.registerRecomment(post1.getId(), comment1.getId(), commentRequestDto);
        commentService.registerRecomment(post1.getId(), comment1.getId(), commentRequestDto);
        commentService.registerRecomment(post1.getId(), comment1.getId(), commentRequestDto);
        commentService.registerRecomment(post1.getId(), comment1.getId(), commentRequestDto);
        commentService.registerRecomment(post1.getId(), comment1.getId(), commentRequestDto);
    }

    @Nested
    @DisplayName("해당 댓글이 이미 삭제된 경우")
    class ParrentCommentNotExist {

        @Test
        @DisplayName("ParrentComment_Not_Exist")
        void parrentComment_Not_Exist() {
            Assertions.assertThrows(NoContentException.class,
                    ()-> commentService.getRecommentList(comment1.getId()+1, 0, user1));
        }
    }
    @Nested
    @DisplayName("조회 성공")
    class SuccessGet {
        @Test
        void get_success() {
            ResponseEntity<RecommentListResponseDto> result = commentService.getRecommentList(comment1.getId(), 0, user1);

            assertEquals(result.getStatusCode(), HttpStatus.OK);
            assertEquals(result.getBody().getCommentList().get(0).getContent(),
                    commentRequestDto.getContent());
        }

    }
}
