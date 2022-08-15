package project.instagram.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostImage {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POSTIMAGE_ID")
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;


}
