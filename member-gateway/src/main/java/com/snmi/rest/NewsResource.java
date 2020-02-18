package com.snmi.rest;

import com.snmi.client.AuthServiceClient;
import com.snmi.client.NewsServiceClient;
import com.snmi.dto.NewsDTO;
import com.snmi.dto.NewsSearchDTO;
import com.snmi.dto.NewsStatisticDTO;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

@Api(value = "Member news API")
@RestController
@RequestMapping("api/v1/news")
public class NewsResource {

    private final NewsServiceClient newsServiceClient;
    private final AuthServiceClient authServiceClient;

    public NewsResource(NewsServiceClient newsServiceClient, AuthServiceClient authServiceClient) {
        this.newsServiceClient = newsServiceClient;
        this.authServiceClient = authServiceClient;
    }

    @PreAuthorize("hasRole('ROLE_WRITER')")
    @PostMapping("")
    public ResponseEntity<?> createNews(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @Valid @RequestBody NewsDTO newsDTO
    ) {
        return newsServiceClient.createNews(token, newsDTO);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{newsId}")
    public ResponseEntity<?> getNewsById(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER, required = false) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_NEWS_ID) Long newsId
    ) {
        if (token == null) {
            token = authServiceClient.generateAnonymousToken();
        }
        return newsServiceClient.getNewsById(token, newsId, true);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/search")
    public ResponseEntity<?> searchNews(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER, required = false) String token,
            @Valid @RequestBody NewsSearchDTO newsSearchDTO
    ) {
        if (token == null) {
            token = authServiceClient.generateAnonymousToken();
        }
        return newsServiceClient.searchNews(token, newsSearchDTO, true);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/likes/{newsId}")
    public ResponseEntity<?> getAllNewsLikes(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER, required = false) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_NEWS_ID) Long newsId
    ) {
        if (token == null) {
            token = authServiceClient.generateAnonymousToken();
        }
        return newsServiceClient.getAllNewsLikes(token, newsId);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/dislikes/{newsId}")
    public ResponseEntity<?> getAllNewsDislikes(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER, required = false) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_NEWS_ID) Long newsId
    ) {
        if (token == null) {
            token = authServiceClient.generateAnonymousToken();
        }
        return newsServiceClient.getAllNewsDislikes(token, newsId);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/favourites/{newsId}")
    public ResponseEntity<?> getAllNewsFavourites(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER, required = false) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_NEWS_ID) Long newsId
    ) {
        if (token == null) {
            token = authServiceClient.generateAnonymousToken();
        }
        return newsServiceClient.getAllNewsFavourites(token, newsId);
    }

    @PreAuthorize("hasRole('ROLE_WRITER')")
    @PutMapping("")
    public ResponseEntity<?> updateNews(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @Valid @RequestBody NewsDTO newsDTO
    ) {
        return newsServiceClient.updateNews(token, newsDTO, true);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/statistics")
    public ResponseEntity<?> updateNewsStatistics(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @Valid @RequestBody NewsStatisticDTO newsStatisticDTO
    ) {
        return newsServiceClient.updateNewsStatistics(token, newsStatisticDTO);
    }

    @PreAuthorize("hasRole('ROLE_WRITER')")
    @DeleteMapping("/{newsId}")
    public ResponseEntity<?> deleteNews(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_NEWS_ID) Long newsId
    ) {
        return newsServiceClient.deleteNews(token, newsId);
    }

}
