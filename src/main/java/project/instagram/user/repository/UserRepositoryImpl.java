package project.instagram.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.instagram.user.search.dto.SearchResponseDto;
import project.instagram.user.search.dto.UserInfo;

import java.util.List;

import static org.aspectj.util.LangUtil.isEmpty;
import static project.instagram.domain.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<UserInfo> searchKeyword(String keyword, Pageable pageable) {
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
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression usernameContains(String keyword) {
        return isEmpty(keyword) ? null : user.username.contains(keyword);
    }

    private BooleanExpression nicknameContains(String keyword) {
        return isEmpty(keyword) ? null : user.nickname.contains(keyword);
    }
}
