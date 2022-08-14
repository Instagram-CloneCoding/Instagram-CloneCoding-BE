package project.instagram.post;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTagRequestDto {
    private Long userId;
    private String username;

}
