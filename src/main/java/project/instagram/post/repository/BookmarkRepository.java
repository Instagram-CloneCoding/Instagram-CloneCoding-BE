package project.instagram.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.instagram.domain.Bookmark;
import project.instagram.domain.Post;
import project.instagram.domain.User;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    Optional<Bookmark> findByUserAndPost(User user, Post post);
}
