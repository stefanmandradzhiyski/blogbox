package com.snmi.rest;

import com.snmi.client.NewsServiceClient;
import com.snmi.dto.NewsDTO;
import com.snmi.dto.NewsSearchDTO;
import com.snmi.util.BlogBoxGlobalConstant;
import com.snmi.util.BlogBoxSecurityGlobalConstant;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

@Api(value = "Admin inactive news API")
@RestController
@RequestMapping("api/v1/news/inactive")
public class NewsInactiveResource {

    private final NewsServiceClient newsServiceClient;

    public NewsInactiveResource(NewsServiceClient newsServiceClient) {
        this.newsServiceClient = newsServiceClient;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{newsId}")
    public ResponseEntity<?> getNewsById(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_NEWS_ID) Long newsId
    ) {
        return newsServiceClient.getNewsById(token, newsId, false);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllNewsByUser(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_USER_ID) Long userId
    ) {
        return newsServiceClient.getAllNewsByUser(token, userId, false);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> getAllNews(@RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token) {
        return newsServiceClient.getAllNews(token, false);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/search")
    public ResponseEntity<?> searchNews(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @Valid @RequestBody NewsSearchDTO newsSearchDTO
    ) {
        return newsServiceClient.searchNews(token, newsSearchDTO, false);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("")
    public ResponseEntity<?> updateNews(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @Valid @RequestBody NewsDTO newsDTO
    ) {
        return newsServiceClient.updateNews(token, newsDTO, false);
    }

}
