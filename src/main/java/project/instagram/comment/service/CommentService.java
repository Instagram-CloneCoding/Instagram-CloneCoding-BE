package project.instagram.comment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.instagram.comment.dto.CommentRequestDto;
import project.instagram.comment.dto.RecommentListResponseDto;
import project.instagram.comment.repository.CommentRepository;
import project.instagram.domain.Comment;
import project.instagram.domain.Post;
import project.instagram.domain.User;
import project.instagram.exception.customexception.CommentNotFoundException;
import project.instagram.exception.customexception.NoContentException;
import project.instagram.exception.customexception.UserNotCorrectException;
import project.instagram.post.PostRepository;
import project.instagram.post.PostService;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private PostService postService;
    @Autowired
    public CommentService(CommentRepository commentRepository,PostRepository postRepository,PostService postService){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @Transactional
    public ResponseEntity registerComment(Long postId, CommentRequestDto commentRequestDto,User user){
        Post post = postService.getPostByPostId(postId);
        if(!isContentExists(commentRequestDto)) throw new NoContentException("댓글을 입력해주세요.");
        Comment comment = new Comment(commentRequestDto,user);
        comment = commentRepository.save(comment);
        post.getComments().add(comment);

        return ResponseEntity.ok(true);
    }

    @Transactional
    public ResponseEntity registerRecomment(Long postId, Long parentCommentId, CommentRequestDto commentRequestDto){
        Post post = postService.getPostByPostId(postId);
        Comment parentComment = getCommentByCommentId(parentCommentId);
        if(!isContentExists(commentRequestDto)) throw new NoContentException("댓글을 입력해주세요.");
        Comment childComment = new Comment(commentRequestDto,parentComment);
        childComment=commentRepository.save(childComment);
        parentComment.getChildren().add(childComment);
        System.out.println(childComment.getId());
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<RecommentListResponseDto> getRecommentList(Long parrentCommentId, int page, User user){
        getCommentByCommentId(parrentCommentId);
        return ResponseEntity.ok(commentRepository.getRecommentList(parrentCommentId,page,user));
    }

    @Transactional
    public ResponseEntity deleteComment(Long commentId,User user){
        Comment comment = getCommentByCommentId(commentId);
        if(!comment.getUser().equals(user)) throw new UserNotCorrectException("해당 댓글의 작성자가 아닙니다");
        commentRepository.deleteCommentById(commentId);
        return ResponseEntity.ok(true);
    }

    public Comment getCommentByCommentId(Long parentCommentId) {
        Optional<Comment> comment = commentRepository.findById(parentCommentId);
        if(!comment.isPresent()) throw new CommentNotFoundException();
        return comment.get();
    }


    private boolean isContentExists(CommentRequestDto commentRequestDto) {
        return !(commentRequestDto.getContent() == null || commentRequestDto.getContent().equals(""));
    }

    
}