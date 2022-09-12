package project.instagram.domain;

import lombok.*;
import project.instagram.comment.dto.CommentRequestDto;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="COMMENT_ID")
    private Long id;

    @Column(length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<HeartComment> heartComments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Comment parent;

    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
    private List<Comment> children = new ArrayList<>();

    public Comment(CommentRequestDto commentRequestDto){
        this.content = commentRequestDto.getContent();
    }
    @Builder
    public Comment(String content){
        this.content = content;
    }
}
