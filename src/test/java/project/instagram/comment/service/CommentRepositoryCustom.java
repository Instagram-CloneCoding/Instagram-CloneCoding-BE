package project.instagram.comment.service;

import project.instagram.domain.User;

public interface CommentRepositoryCustom {
    RecommentListResponseDto getRecommentList(Long commentId, int page, User loginUser);
}
