package project.instagram.heartcomment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.instagram.comment.service.CommentService;
import project.instagram.domain.Comment;
import project.instagram.domain.HeartComment;
import project.instagram.domain.User;

@Service
public class HeartCommentService {
    private HeartCommentRepository heartCommentRepository;
    private CommentService commentService;

    public HeartCommentService(HeartCommentRepository heartCommentRepository,CommentService commentService){
        this.heartCommentRepository = heartCommentRepository;
        this.commentService = commentService;
    }

    public ResponseEntity registerHeartComment(Long commentId,User user){
        Comment comment = commentService.getCommentByCommentId(commentId);
        if(alreadyHeartCommentPressed(comment,user)){
            heartCommentRepository.deleteHeartCommentByCommentAndUser(comment,user);
            return ResponseEntity.ok(false);
        }
        HeartComment heartComment = new HeartComment().builder()
                .comment(comment)
                .user(user)
                .build();
        heartComment = heartCommentRepository.save(heartComment);
        comment.getHeartComments().add(heartComment);
        return ResponseEntity.ok(true);
    }

    public boolean alreadyHeartCommentPressed(Comment comment, User user){
        return heartCommentRepository.existsHeartCommentByCommentAndUser(comment,user);
    }
}
