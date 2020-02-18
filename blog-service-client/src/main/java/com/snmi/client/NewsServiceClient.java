package com.snmi.client;

import com.snmi.dto.NewsDTO;
import com.snmi.dto.NewsSearchDTO;
import com.snmi.dto.NewsStatisticDTO;
import com.snmi.dto.NewsUpdateStatusDTO;
import com.snmi.enums.ResponseType;
import com.snmi.util.BlogBoxGlobalConstant;
import com.snmi.util.BlogBoxRESTTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * News client which is used by outside world to send request to the internal news resource
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Service
public class NewsServiceClient {

    private static final String BLOG_NEWS_BASE_URL
            = BlogBoxGlobalConstant.BLOG_BASE_URL + BlogBoxGlobalConstant.NEWS_BASE_URL;

    public NewsServiceClient() {

    }

    public ResponseEntity<?> createNews(String token, NewsDTO newsDTO) {
        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_NEWS_BASE_URL,
                HttpMethod.POST,
                newsDTO,
                null,
                null,
                NewsDTO.class,
                ResponseType.UNIT
        );
    }

    public ResponseEntity<?> getNewsById(String token, Long newsId, boolean display) {
        Map<String, Object> params = new HashMap<>();
        params.put(BlogBoxGlobalConstant.PATH_DISPLAY, display);

        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_NEWS_BASE_URL + "/",
                HttpMethod.GET,
                null,
                Long.toString(newsId),
                params,
                NewsDTO.class,
                ResponseType.UNIT
        );
    }

    public ResponseEntity<?> getAllNewsByUser(String token, Long userId, boolean display) {
        Map<String, Object> params = new HashMap<>();
        params.put(BlogBoxGlobalConstant.PATH_DISPLAY, display);

        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_NEWS_BASE_URL + "/user/",
                HttpMethod.GET,
                null,
                Long.toString(userId),
                params,
                NewsDTO.class,
                ResponseType.LIST
        );
    }

    public ResponseEntity<?> getAllNews(String token, boolean display) {
        Map<String, Object> params = new HashMap<>();
        params.put(BlogBoxGlobalConstant.PATH_DISPLAY, display);

        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_NEWS_BASE_URL,
                HttpMethod.GET,
                null,
                null,
                params,
                NewsDTO.class,
                ResponseType.LIST
        );
    }

    public ResponseEntity<?> searchNews(String token, NewsSearchDTO newsSearchDTO, boolean display) {
        Map<String, Object> params = new HashMap<>();
        params.put(BlogBoxGlobalConstant.PATH_DISPLAY, display);

        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_NEWS_BASE_URL + "/search",
                HttpMethod.POST,
                newsSearchDTO,
                null,
                params,
                NewsDTO.class,
                ResponseType.LIST
        );
    }

    public ResponseEntity<?> getAllNewsLikes(String token, Long newsId) {
        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_NEWS_BASE_URL + "/likes/",
                HttpMethod.GET,
                null,
                Long.toString(newsId),
                null,
                NewsDTO.class,
                ResponseType.LIST
        );
    }

    public ResponseEntity<?> getAllNewsDislikes(String token, Long newsId) {
        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_NEWS_BASE_URL + "/dislikes/",
                HttpMethod.GET,
                null,
                Long.toString(newsId),
                null,
                NewsDTO.class,
                ResponseType.LIST
        );
    }

    public ResponseEntity<?> getAllNewsFavourites(String token, Long newsId) {
        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_NEWS_BASE_URL + "/favourites/",
                HttpMethod.GET,
                null,
                Long.toString(newsId),
                null,
                NewsDTO.class,
                ResponseType.LIST
        );
    }

    public ResponseEntity<?> updateNews(String token, NewsDTO newsDTO, boolean display) {
        Map<String, Object> params = new HashMap<>();
        params.put(BlogBoxGlobalConstant.PATH_DISPLAY, display);

        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_NEWS_BASE_URL,
                HttpMethod.PUT,
                newsDTO,
                null,
                params,
                NewsDTO.class,
                ResponseType.UNIT
        );
    }

    public ResponseEntity<?> updateNewsStatistics(String token, NewsStatisticDTO newsStatisticDTO) {
        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_NEWS_BASE_URL + "/statistics",
                HttpMethod.POST,
                newsStatisticDTO,
                null,
                null,
                NewsDTO.class,
                ResponseType.UNIT
        );
    }

    public ResponseEntity<?> updateNewsStatus(String token, NewsUpdateStatusDTO newsUpdateStatusDTO) {
        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_NEWS_BASE_URL + "/status",
                HttpMethod.PATCH,
                newsUpdateStatusDTO,
                null,
                null,
                NewsDTO.class,
                ResponseType.UNIT
        );
    }

    public ResponseEntity<?> deleteNews(String token, Long newsId) {
        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_NEWS_BASE_URL + "/",
                HttpMethod.DELETE,
                null,
                Long.toString(newsId),
                null,
                NewsDTO.class,
                ResponseType.UNIT
        );
    }


}
