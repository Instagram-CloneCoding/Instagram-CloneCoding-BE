package project.instagram.post;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageRequestDto {
    private MultipartFile imageFile;
    private List<ImageTagRequestDto> imageTagList;

}
