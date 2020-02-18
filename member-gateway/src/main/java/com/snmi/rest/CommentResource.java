package com.snmi.rest;

import com.snmi.client.AuthServiceClient;
import com.snmi.client.CommentServiceClient;
import com.snmi.dto.CommentDTO;
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

@Api(value = "Member comment API")
@RestController
@RequestMapping("api/v1/comments")
public class CommentResource {

    private final CommentServiceClient commentServiceClient;
    private final AuthServiceClient authServiceClient;

    public CommentResource(CommentServiceClient commentServiceClient, AuthServiceClient authServiceClient) {
        this.commentServiceClient = commentServiceClient;
        this.authServiceClient = authServiceClient;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("")
    public ResponseEntity<?> createComment(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @Valid @RequestBody CommentDTO commentDTO
    ) {
        return commentServiceClient.createComment(token, commentDTO);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{commentId}")
    public ResponseEntity<?> getComment(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER, required = false) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_COMMENT_ID) Long commentId
    ) {
        if (token == null) {
            token = authServiceClient.generateAnonymousToken();
        }
        return commentServiceClient.getCommentById(token, commentId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("")
    public ResponseEntity<?> updateComment(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @RequestBody CommentDTO commentDTO
    ) {
        return commentServiceClient.updateComment(token, commentDTO);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @RequestHeader(value = BlogBoxSecurityGlobalConstant.TOKEN_HEADER) String token,
            @PathVariable(BlogBoxGlobalConstant.PATH_COMMENT_ID) Long commentId
    ) {
        return commentServiceClient.deleteComment(token, commentId);
    }

}
