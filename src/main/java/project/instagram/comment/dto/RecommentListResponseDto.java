package project.instagram.comment.dto;

import lombok.Getter;

import java.util.List;
@Getter
public class RecommentListResponseDto {
    private Long parentId;
    private int totalRecommentcnt;
    private List<RecommentResponseDto> commentList;

    public RecommentListResponseDto(Long parentId, int totalRecommentcnt, List<RecommentResponseDto> commentList) {
        this.parentId = parentId;
        this.totalRecommentcnt = totalRecommentcnt;
        this.commentList = commentList;
    }

    public RecommentListResponseDto(){
    }
    public List<RecommentResponseDto> getCommentList(){
        return this.commentList;
    }

    public Long getParentId() {
        return parentId;
    }

    public int getTotalRecommentcnt() {
        return totalRecommentcnt;
    }
}
