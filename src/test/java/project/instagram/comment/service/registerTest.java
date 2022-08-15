package project.instagram.comment.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.instagram.comment.dto.CommentRequestDto;
import project.instagram.comment.repository.CommentRepository;
import project.instagram.exception.customexception.PostNotFoundException;
import project.instagram.post.PostRepository;

@SpringBootTest
public class registerTest {
    private CommentService commentService;
    private final Long postId = 1L;
    @Autowired private CommentRepository commentRepository;
    @Autowired private PostRepository postRepository;
    private CommentRequestDto commentRequestDto;
    @Test
    void setup(){
        commentService = new CommentService(commentRepository);
        commentRequestDto = new CommentRequestDto().builder()
                .content("안녕하세요")
                .build();
    }

    @DisplayName("해당 포스트가 없을 때")
    void register_Post_Not_Exist(){
        Assertions.assertThrows(PostNotFoundException.class, () -> commentService.register(postId, commentRequestDto));
    }
}
