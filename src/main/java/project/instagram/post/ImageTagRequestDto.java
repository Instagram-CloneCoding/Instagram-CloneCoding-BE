package project.instagram.post;

import lombok.*;

@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageTagRequestDto {
    private Long tagId;
    private double positionX;
    private double positionY;
    private UserTagRequestDto userInfo;

}
