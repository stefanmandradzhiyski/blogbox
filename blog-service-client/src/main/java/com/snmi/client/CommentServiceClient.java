package com.snmi.client;

import com.snmi.dto.CommentDTO;
import com.snmi.enums.ResponseType;
import com.snmi.util.BlogBoxGlobalConstant;
import com.snmi.util.BlogBoxRESTTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Comment client which is used by outside world to send request to the internal comment resource
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@Service
public class CommentServiceClient {

    private static final String BLOG_COMMENT_BASE_URL
            = BlogBoxGlobalConstant.BLOG_BASE_URL + BlogBoxGlobalConstant.COMMENTS_BASE_URL;

    public CommentServiceClient() {

    }

    public ResponseEntity<?> createComment(String token, CommentDTO commentDTO) {
        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_COMMENT_BASE_URL,
                HttpMethod.POST,
                commentDTO,
                null,
                null,
                CommentDTO.class,
                ResponseType.UNIT
        );
    }

    public ResponseEntity<?> getCommentById(String token, Long commentId) {
        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_COMMENT_BASE_URL + "/",
                HttpMethod.GET,
                null,
                Long.toString(commentId),
                null,
                CommentDTO.class,
                ResponseType.UNIT
        );
    }

    public ResponseEntity<?> updateComment(String token, CommentDTO commentDTO) {
        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_COMMENT_BASE_URL,
                HttpMethod.PUT,
                commentDTO,
                null,
                null,
                CommentDTO.class,
                ResponseType.UNIT
        );
    }

    public ResponseEntity<?> deleteComment(String token, Long commentId) {
        return BlogBoxRESTTemplate.buildRequest(
                token,
                BLOG_COMMENT_BASE_URL + "/",
                HttpMethod.DELETE,
                null,
                Long.toString(commentId),
                null,
                CommentDTO.class,
                ResponseType.UNIT
        );
    }


}
