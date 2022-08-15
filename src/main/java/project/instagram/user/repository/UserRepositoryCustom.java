package project.instagram.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.instagram.user.search.dto.SearchResponseDto;
import project.instagram.user.search.dto.UserInfo;

public interface UserRepositoryCustom {
    Page<UserInfo> searchKeyword(String keyword, Pageable pageable);
}
