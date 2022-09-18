package project.instagram.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.instagram.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>,CommentRepositoryCustom {

    void deleteCommentById(Long commentId);
}
