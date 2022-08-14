package project.instagram.post;

import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
    private String content;
    private List<ImageRequestDto> imageList;
}
