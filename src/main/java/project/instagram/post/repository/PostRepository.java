package project.instagram.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.instagram.domain.Post;

public interface PostRepository extends JpaRepository<Post,Long> {
}
