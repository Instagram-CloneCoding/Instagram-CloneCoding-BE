package project.instagram.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.instagram.domain.Post;
import project.instagram.exception.customexception.PostNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public Post getPostByPostId(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if(!post.isPresent()) throw new PostNotFoundException("이미 삭제된 게시글입니다.");
        return post.get();
    }
}
