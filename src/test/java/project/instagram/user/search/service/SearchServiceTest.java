package project.instagram.user.search.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import project.instagram.domain.User;
import project.instagram.user.repository.UserRepository;
import project.instagram.user.search.dto.SearchResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class SearchServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SearchService searchService;

    @BeforeEach
    public void before() {
        for (Long i = 1L; i <= 100; i++) {
            User user = insertUser("가가가", "bbb");
            userRepository.save(user);
        }
    }

    @DisplayName("키워드 조회 서비스")
    @Test
    void search() {
        //given
        String keyword = "가";
        PageRequest pageable = PageRequest.of(12, 5);

        //when
        SearchResponseDto search = searchService.search(keyword, pageable);

        //then
        assertThat(search.getUserInfo().get(0).getNickname()).isEqualTo("가가가");
        assertThat(search.getUserInfo().get(0).getUsername()).isEqualTo("bbb");
        assertThat(search.getTotalPage()).isEqualTo(20);
        assertThat(search.getCurrentPage()).isEqualTo(12);

    }

    private User insertUser(String nickname, String username) {
        return User.builder()
                .nickname(nickname)
                .username(username)
                .build();
    }
}