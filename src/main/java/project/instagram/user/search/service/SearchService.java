package project.instagram.user.search.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.instagram.aop.annotation.Trace;
import project.instagram.user.repository.UserRepository;
import project.instagram.user.search.dto.SearchResponseDto;
import project.instagram.user.search.dto.UserInfo;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final UserRepository userRepository;

    @Trace
    public SearchResponseDto search(String keyword, Pageable pageable) {
        Page<UserInfo> userInfo = userRepository.searchKeyword(keyword, pageable);
        return new SearchResponseDto(userInfo);
    }
}
