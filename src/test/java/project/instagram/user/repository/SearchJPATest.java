package project.instagram.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import project.instagram.domain.User;
import project.instagram.user.search.dto.UserInfo;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.aspectj.util.LangUtil.isEmpty;
import static org.assertj.core.api.Assertions.assertThat;
import static project.instagram.domain.QUser.user;

@SpringBootTest
class SearchJPATest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    @AfterEach
    void after() {
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("검색")
    @Transactional
    class searchTest {

        @BeforeEach
        public void before() {
            userRepository.save(insertUser("가나뷁","가나다"));
            for (Long i = 0L; i < 50; i++) {
                userRepository.save(insertUser("가나다","뷁가"));
            }
            for (Long i = 0L; i < 50; i++) {
                userRepository.save(insertUser("가나다","나가"));
            }
        }

        @DisplayName("키워드 조회 쿼리")
        @Test
        void search() {
            //given
            String keyword = "뷁";
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
            assertThat(userInfo.getContent().get(0).getNickname()).isEqualTo("가나뷁");
            assertThat(userInfo.getContent().get(1).getNickname()).isEqualTo("가나다");
            assertThat(userInfo.getTotalPages()).isEqualTo(11);
            assertThat(userInfo.getNumber()).isEqualTo(0);
        }

        private BooleanExpression usernameContains(String keyword) {
            return isEmpty(keyword) ? null : user.username.contains(keyword);
        }

        private BooleanExpression nicknameContains(String keyword) {
            return isEmpty(keyword) ? null : user.nickname.contains(keyword);
        }

        private User insertUser(String nickname, String username) {
            return User.builder()
                    .nickname(nickname)
                    .username(username)
                    .build();
        }
    }

}