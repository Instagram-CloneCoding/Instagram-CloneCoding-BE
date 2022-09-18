package project.instagram.heartcomment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.instagram.domain.Comment;
import project.instagram.domain.User;

@Service
@RequiredArgsConstructor
public class HeartCommentService {
    private final HeartCommentRepository heartCommentRepository;

    public ResponseEntity registerHeartComment(){
        return ResponseEntity.ok(true);
    }

    public boolean alreadyHeartCommentPressed(Comment comment, User user){
        return heartCommentRepository.existsHeartCommentByCommentAndUser(comment,user);
    }
}
