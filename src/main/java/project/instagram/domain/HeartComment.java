package project.instagram.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeartComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HEARTCOMMENT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

}
