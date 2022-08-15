package project.instagram.post;

import org.springframework.data.jpa.repository.JpaRepository;
import project.instagram.domain.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage,Long> {
}
