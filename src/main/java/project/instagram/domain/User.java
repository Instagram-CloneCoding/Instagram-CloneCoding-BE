package project.instagram.domain;

import lombok.*;

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

    //test
    public User(String username) {
        this.username = username;
    }

    @Builder
    public User(String username, String nickname, String profileImage) {
        this.username = username;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}
