package project.instagram.comment.service;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;
import project.instagram.domain.Comment;
import project.instagram.domain.User;

import java.util.List;

import static project.instagram.domain.QComment.comment;
import static project.instagram.domain.QHeartComment.heartComment;
import static project.instagram.domain.QUser.user;

@Repository
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    @BatchSize(size = 1000)
    public RecommentListResponseDto getRecommentList(Long commentId, int page, User loginUser) {
        List<RecommentResponseDto> commentList = queryFactory
                .select(Projections.constructor(RecommentResponseDto.class,
                        comment.id,
                        comment.content,
                        heartComment.id,
                        comment.createdAt,
//                        comment.content
                        user.username
                        ))
                .from(comment)
                .leftJoin(comment.heartComments,heartComment)
//                .on(heartComment.comment.eq(comment))
                .leftJoin(comment.user,user)
//                .on(comment.user.eq(user))
//                .where(comment.parent.id.eq(commentId).and(heartComment.user.eq(loginUser)))
                .orderBy(new OrderSpecifier(Order.DESC,comment.id))
                .offset(3*page)
                .limit(3)
                .fetch();
        int totalRecommentcnt = (int) queryFactory.select(comment)
                .from(comment)
                .where(comment.parent.id.eq(commentId))
                .stream().count();
        return new RecommentListResponseDto(commentId,totalRecommentcnt,commentList);
    }

}
