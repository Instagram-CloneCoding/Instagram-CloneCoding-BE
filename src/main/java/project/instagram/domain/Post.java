package project.instagram.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends TimeStamped{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_ID")
    private Long id;

    @Lob
    private String content;

    @OneToMany(mappedBy = "post")
    private List<PostImage> postImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "post")
    private final List<HeartPost> heartPosts = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private final List<Comment> comments = new ArrayList<>();

    public void insertImage(List<PostImage> postImageList){
        this.postImages = postImageList;
    }


}
