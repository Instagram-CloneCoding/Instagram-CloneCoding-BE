package project.instagram.post.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.instagram.exception.customexception.ImageNotExistException;
import project.instagram.exception.customexception.ImageNotMatchException;
import project.instagram.post.PostRequestDto;
import project.instagram.post.repository.PostRepository;

import static project.instagram.util.FileExtension.*;

@Service
public class PostService {
    private PostRepository postRepository;
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public ResponseEntity regist(PostRequestDto postRequestDto){
        if(postRequestDto.getImageList()==null) throw new ImageNotExistException("이미지를 넣어주세요");
        String filename = postRequestDto.getImageList().get(0).getImageFile().getOriginalFilename();
        String[] a =filename.split("\\.");
        if(!(a[a.length-1].equals(JPG)||a[a.length-1].equals(PNG)))throw new ImageNotMatchException("이미지 파일이 아닙니다");
        return new ResponseEntity(postRequestDto,HttpStatus.OK);
    }
}
