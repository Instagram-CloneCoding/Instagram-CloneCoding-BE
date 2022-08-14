package project.instagram.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    private String userEmail;
    private String phoneNumber;
    private String password;
    private String username;
    private String nickname;
    private String profileImage;
    private String introduction;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "follow") // 날 팔로우 한 사람
    private List<Follow> follows = new ArrayList<>();

    @OneToMany(mappedBy = "follower") // 내가 팔로우 한 사람들
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarks = new ArrayList<>();

    public User(Long id) {
        this.id = id;
    }
}
