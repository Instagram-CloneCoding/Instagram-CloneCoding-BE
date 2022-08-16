package project.instagram.user.search.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.instagram.aop.annotation.Trace;
import project.instagram.user.search.dto.SearchResponseDto;
import project.instagram.user.search.service.SearchService;

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @Trace
    @GetMapping("/search")
    public SearchResponseDto search(@RequestParam("keyword") String keyword, Pageable pageable) {
        return searchService.search(keyword, pageable);
    }

}
