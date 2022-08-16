package project.instagram.user.search.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class SearchResponseDto {
    private int currentPage;
    private int totalPage;
    private List<UserInfo> userInfo;

    public SearchResponseDto(Page<UserInfo> userInfo) {
        this.currentPage = userInfo.getNumber();
        this.totalPage = userInfo.getTotalPages();
        this.userInfo = userInfo.getContent();
    }
}
