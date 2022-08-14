package project.instagram.post.service;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import project.instagram.domain.Post;
import project.instagram.domain.User;
import project.instagram.exception.customexception.ImageNotExistException;
import project.instagram.exception.customexception.ImageNotMatchException;
import project.instagram.post.ImageRequestDto;
import project.instagram.post.ImageTagRequestDto;
import project.instagram.post.PostRequestDto;
import project.instagram.post.UserTagRequestDto;
import project.instagram.post.repository.PostRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RegistTest {
    private PostService postService ;
    @Mock
    private PostRepository postRepository;
    private  MockMultipartFile mockMultipartFile;

    @BeforeEach
    void setUp() throws IOException {
        postService = new PostService(postRepository);
//        mockMultipartFile = new MockMultipartFile("imageFile","test.jpg",null,new FileInputStream("src/test/java/project/instagram/test.jpg"));
    }

    @AfterEach
    void close(){
    }


    @DisplayName("게시글 등록, image가 없을 때")
    @Test
    void register_Post_without_Image() throws IOException {
        PostRequestDto postRequestDto = injectData_without_Image();
        Assertions.assertThrows(ImageNotExistException.class,()-> postService.regist(postRequestDto));
    }

    @DisplayName("게시글 등록, 입력파일이 image가 아닐때")
    @Test
    void register_Post_Image_Not_Correct() throws IOException {
        PostRequestDto postRequestDto = injectData("src/test/java/project/instagram/test.exe");
        Assertions.assertThrows(ImageNotMatchException.class,()-> postService.regist(postRequestDto));
    }


    @DisplayName("게시글 등록, 성공")
    @Test
    void register_Post() throws IOException {
        PostRequestDto postRequestDto = injectData("src/test/java/project/instagram/test.jpg");
//        postService.regist(postRequestDto);
        Post post = new Post().builder()
                .user(new User(1L))
                .id(1L)
                .content("안녕하세요")
                .build();
        postRepository.save(post);
    }

    private PostRequestDto injectData(String file) throws IOException {
        UserTagRequestDto userInfo = userTag();
        ImageTagRequestDto imageTag = imageTag(userInfo);
        List<ImageTagRequestDto> imageTagList = new ArrayList<>(Arrays.asList(imageTag));
        ImageRequestDto imageRequestDto = ImageRequestDto.builder()
                .imageFile(new MockMultipartFile("imageFile","test.jpg",null,new FileInputStream("src/test/java/project/instagram/test.jpg")))
                .imageTagList(imageTagList)
                .build();
        List<ImageRequestDto> imageList = new ArrayList<>(Arrays.asList(imageRequestDto));
        PostRequestDto postRequestDto = new PostRequestDto().builder()
                .content("안녕하세요")
                .imageList(imageList)
                .build();
        return postRequestDto;
    }

    private PostRequestDto injectData_without_Image() {
        UserTagRequestDto userInfo = userTag();
        ImageTagRequestDto imageTag = imageTag(userInfo);
        List<ImageTagRequestDto> imageTagList = new ArrayList<>(Arrays.asList(imageTag));
        PostRequestDto postRequestDto = new PostRequestDto().builder()
                .content("안녕하세요")
                .build();
        return postRequestDto;
    }
    private ImageTagRequestDto imageTag(UserTagRequestDto userInfo) {
        ImageTagRequestDto imageTag= ImageTagRequestDto.builder()
                .tagId(1L)
                .positionX(15.1234)
                .positionY(16.1234)
                .userInfo(userInfo)
                .build();
        return imageTag;
    }

    private UserTagRequestDto userTag() {
        UserTagRequestDto userInfo = new UserTagRequestDto().builder()
                .userId(1L)
                .username("minwoo1")
                .build();
        return userInfo;
    }

}
