package project.instagram.comment.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.instagram.common.util.Time;
import project.instagram.domain.Comment;
import project.instagram.domain.HeartComment;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommentResponseDto {
    private Long commentId;
    private String content;
    private boolean myHeart;
    private String createdTime;
    private String username;

    public RecommentResponseDto(Long commentId, String content, Long myHeart, LocalDateTime createdTime, String username) {
        this.commentId = commentId;
        this.content = content;
        this.myHeart = !isHeart(myHeart);
        this.createdTime = convertTime(createdTime);
        this.username = username;
    }

    public String getContent(){
        return this.content;
    }

    public Long getCommentId() {
        return commentId;
    }

    public boolean isMyHeart() {
        return myHeart;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getUsername() {
        return username;
    }

    private boolean isHeart(Long myHeart){
        return myHeart==null;
    }

    private String convertTime(LocalDateTime localDateTime){
        if(localDateTime==null) return null;
        return Time.convertLocaldatetimeToTime(localDateTime);
    }

}
