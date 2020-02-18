package com.snmi.rest;

import com.snmi.client.NewsServiceClient;
import com.snmi.dto.NewsDTO;
import com.snmi.dto.NewsSearchDTO;
import com.snmi.dto.NewsStatisticDTO;
import com.snmi.dto.NewsUpdateStatusDTO;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

@Api(value = "Admin news API")
@RestController
@RequestMapping("api/v1/news")
public class NewsResource {

    private final NewsServiceClient newsServiceClient;

    public NewsResource(NewsServiceClient newsServiceClient) {
        this.newsServiceClient = newsServiceClient;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> createNews(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @Valid @RequestBody NewsDTO newsDTO
    ) {
        return newsServiceClient.createNews(token, newsDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{newsId}")
    public ResponseEntity<?> getNewsById(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_NEWS_ID) Long newsId
    ) {
        return newsServiceClient.getNewsById(token, newsId, true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllNewsByUser(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_USER_ID) Long userId
    ) {
        return newsServiceClient.getAllNewsByUser(token, userId, true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public ResponseEntity<?> getAllNews(@RequestHeader("Authorization") String token) {
        return newsServiceClient.getAllNews(token, true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/search")
    public ResponseEntity<?> searchUsers(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @Valid @RequestBody NewsSearchDTO newsSearchDTO
    ) {
        return newsServiceClient.searchNews(token, newsSearchDTO, true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/likes/{newsId}")
    public ResponseEntity<?> getAllNewsLikes(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_NEWS_ID) Long newsId
    ) {
        return newsServiceClient.getAllNewsLikes(token, newsId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dislikes/{newsId}")
    public ResponseEntity<?> getAllNewsDislikes(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_NEWS_ID) Long newsId
    ) {
        return newsServiceClient.getAllNewsDislikes(token, newsId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/favourites/{newsId}")
    public ResponseEntity<?> getAllNewsFavourites(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_NEWS_ID) Long newsId
    ) {
        return newsServiceClient.getAllNewsFavourites(token, newsId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("")
    public ResponseEntity<?> updateNews(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @Valid @RequestBody NewsDTO newsDTO
    ) {
        return newsServiceClient.updateNews(token, newsDTO, true);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/statistics")
    public ResponseEntity<?> updateNewsStatistics(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @Valid @RequestBody NewsStatisticDTO newsStatisticDTO
    ) {
        return newsServiceClient.updateNewsStatistics(token, newsStatisticDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/status")
    public ResponseEntity<?> updateNewsStatus(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @Valid @RequestBody NewsUpdateStatusDTO newsUpdateStatusDTO
    ) {
        return newsServiceClient.updateNewsStatus(token, newsUpdateStatusDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{newsId}")
    public ResponseEntity<?> deleteNews(
            @RequestHeader(BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_NEWS_ID) Long newsId
    ) {
        return newsServiceClient.deleteNews(token, newsId);
    }

}
