package com.snmi.rest;

import com.snmi.dto.NewsDTO;
import com.snmi.dto.NewsSearchDTO;
import com.snmi.dto.NewsStatisticDTO;
import com.snmi.dto.NewsUpdateStatusDTO;
import com.snmi.dto.ShowStatisticDTO;
import com.snmi.enums.Statistic;
import com.snmi.service.NewsService;
import com.snmi.service.NewsServiceImpl;
import com.snmi.util.BlogBoxGlobalConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(value = "News API")
@RestController
@RequestMapping("api/v1/news")
public class NewsResource {

    private NewsService newsService;

    public NewsResource(NewsServiceImpl newsService) {
        this.newsService = newsService;
    }

    @ApiOperation(value = "Create new news")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "News has been registered successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "User doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("")
    public ResponseEntity<NewsDTO> createNews(@Valid @RequestBody NewsDTO newsDTO) {
        return ResponseEntity.ok(newsService.createNews(newsDTO));
    }

    @ApiOperation(value = "Get specific news")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "News has been retrieved successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid news id",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "News doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{newsId}")
    public ResponseEntity<NewsDTO> getNewsById(
            @PathVariable(BlogBoxGlobalConstant.PATH_NEWS_ID) Long newsId,
            @RequestParam(BlogBoxGlobalConstant.PATH_DISPLAY) boolean display
    ) {
        return ResponseEntity.ok(newsService.getNewsById(newsId, display));
    }

    @ApiOperation(value = "Get news by user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User news list has been retrieved successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid user id",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "User doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NewsDTO>> getAllNewsByUser(
            @PathVariable(BlogBoxGlobalConstant.PATH_USER_ID) Long userId,
            @RequestParam(BlogBoxGlobalConstant.PATH_DISPLAY) boolean display
    ) {
        return ResponseEntity.ok(newsService.getAllNewsByUser(userId, display));
    }

    @ApiOperation(value = "Get all system news")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "System news list has been retrieved successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public ResponseEntity<List<NewsDTO>> getAllNews(@RequestParam(BlogBoxGlobalConstant.PATH_DISPLAY) boolean display) {
        return ResponseEntity.ok(newsService.getAll(display));
    }

    @ApiOperation(value = "Search news by criteria")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "News list has been retrieved successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/search")
    public ResponseEntity<List<NewsDTO>> searchNews(
            @Valid @RequestBody NewsSearchDTO newsSearchDTO,
            @RequestParam(BlogBoxGlobalConstant.PATH_DISPLAY) boolean display
    ) {
        return ResponseEntity.ok(newsService.searchNews(newsSearchDTO, display));
    }

    @ApiOperation(value = "Get specific news likes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "News likes list has been retrieved successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid news id",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/likes/{newsId}")
    public ResponseEntity<List<ShowStatisticDTO>> getAllNewsLikes(@PathVariable(BlogBoxGlobalConstant.PATH_NEWS_ID) Long newsId) {
        return ResponseEntity.ok(newsService.getStatistic(newsId, Statistic.LIKES));
    }

    @ApiOperation(value = "Get specific news dislikes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "News dislikes list has been retrieved successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid news id",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/dislikes/{newsId}")
    public ResponseEntity<List<ShowStatisticDTO>> getAllNewsDislikes(@PathVariable(BlogBoxGlobalConstant.PATH_NEWS_ID) Long newsId) {
        return ResponseEntity.ok(newsService.getStatistic(newsId, Statistic.DISLIKES));
    }

    @ApiOperation(value = "Get specific news favourites")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "News favourites list has been retrieved successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid news id",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/favourites/{newsId}")
    public ResponseEntity<List<ShowStatisticDTO>> getAllNewsFavourites(@PathVariable(BlogBoxGlobalConstant.PATH_NEWS_ID) Long newsId) {
        return ResponseEntity.ok(newsService.getStatistic(newsId, Statistic.FAVOURITES));
    }

    @ApiOperation(value = "Update specific news")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "News has been updated successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid news id",
                    response = ResponseEntity.class),
            @ApiResponse(code = 403, message = "You can't manipulate other news",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "News doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @PutMapping("")
    public ResponseEntity<NewsDTO> updateNews(
            @Valid @RequestBody NewsDTO newsDTO,
            @RequestParam(BlogBoxGlobalConstant.PATH_DISPLAY) boolean display
    ) {
        return ResponseEntity.ok(newsService.updateNews(newsDTO, display));
    }

    @ApiOperation(value = "Update specific news statistic")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "News statistic has been updated successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid news or user id",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "News or user doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/statistics")
    public ResponseEntity<NewsDTO> updateNewsStatistics(@Valid @RequestBody NewsStatisticDTO newsStatisticDTO) {
        return ResponseEntity.ok(newsService.updateNewsStatistics(newsStatisticDTO));
    }

    @ApiOperation(value = "Update specific news status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "News statistic has been updated successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid news id",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "News doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/status")
    public ResponseEntity<NewsDTO> updateNewsStatus(@Valid @RequestBody NewsUpdateStatusDTO newsUpdateStatusDTO) {
        return ResponseEntity.ok(newsService.updateNewsStatus(newsUpdateStatusDTO));
    }

    @ApiOperation(value = "Delete specific news")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "News has been deleted successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Not valid news id",
                    response = ResponseEntity.class),
            @ApiResponse(code = 403, message = "You can't manipulate other news",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "News doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{newsId}")
    public ResponseEntity<NewsDTO> deleteNews(@PathVariable(BlogBoxGlobalConstant.PATH_NEWS_ID) Long newsId) {
        return ResponseEntity.ok(newsService.deleteNews(newsId));
    }

}
