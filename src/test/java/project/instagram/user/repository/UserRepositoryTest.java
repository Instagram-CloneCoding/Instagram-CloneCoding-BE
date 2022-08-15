package project.instagram.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import project.instagram.domain.User;
import project.instagram.user.search.dto.UserInfo;

import javax.persistence.EntityManager;
import java.util.List;

import static org.aspectj.util.LangUtil.isEmpty;
import static org.assertj.core.api.Assertions.assertThat;
import static project.instagram.domain.QUser.user;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
        User user1 = insertUser(1L,"가나다", "라마바");
        User user2 = insertUser(2L,"라마바", "다나가");
        User user3 = insertUser(3L,"나나나", "다다다");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        for (Long i = 4L; i <= 100; i++) {
            User user = insertUser(i, "가가가", "bbb");
            userRepository.save(user);
        }
    }

    @DisplayName("키워드 조회 쿼리")
    @Test
    void search() {
        //given
        String keyword = "가";
        PageRequest pageable = PageRequest.of(0, 5);
        List<UserInfo> content = queryFactory
                .select(Projections.constructor(UserInfo.class, user))
                .from(user)
                .where(nicknameContains(keyword)
                        .or(usernameContains(keyword)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(user.count())
                .from(user)
                .where(nicknameContains(keyword)
                        .or(usernameContains(keyword)))
                .fetchOne();

        //when
        PageImpl<UserInfo> userInfo = new PageImpl<>(content, pageable, total);

        //then
        assertThat(userInfo.getSize()).isEqualTo(5);
        assertThat(userInfo.getContent().get(0).getNickname()).isEqualTo("가나다");
        assertThat(userInfo.getContent().get(1).getNickname()).isEqualTo("라마바");
        assertThat(userInfo.getTotalPages()).isEqualTo(20);
        assertThat(userInfo.getNumber()).isEqualTo(0);
    }

    private BooleanExpression usernameContains(String keyword) {
        return isEmpty(keyword) ? null : user.username.contains(keyword);
    }

    private BooleanExpression nicknameContains(String keyword) {
        return isEmpty(keyword) ? null : user.nickname.contains(keyword);
    }

    private User insertUser(Long id,String nickname, String username) {
        return User.builder()
                .id(id)
                .nickname(nickname)
                .username(username)
                .build();
    }
}