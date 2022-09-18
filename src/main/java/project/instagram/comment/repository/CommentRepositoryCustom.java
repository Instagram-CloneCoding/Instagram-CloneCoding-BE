package project.instagram.comment.repository;

import project.instagram.comment.dto.RecommentListResponseDto;
import project.instagram.domain.User;

public interface CommentRepositoryCustom {
    RecommentListResponseDto getRecommentList(Long commentId, int page, User loginUser);
}
