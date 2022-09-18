package project.instagram.heartcomment.service;

import org.springframework.data.jpa.repository.JpaRepository;
import project.instagram.domain.Comment;
import project.instagram.domain.HeartComment;
import project.instagram.domain.User;

public interface HeartCommentRepository extends JpaRepository<HeartComment,Long> {
    boolean existsHeartCommentByCommentAndUser(Comment comment, User user);
}
