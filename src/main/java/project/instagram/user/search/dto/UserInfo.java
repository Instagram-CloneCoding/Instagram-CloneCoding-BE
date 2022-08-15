package project.instagram.user.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.instagram.domain.User;

@Getter
@ToString
public class UserInfo {
    private Long userId;
    private String profileImage;
    private String username;
    private String nickname;

    public UserInfo(User user) {
        this.userId = user.getId();
        this.profileImage = user.getProfileImage();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }

}
