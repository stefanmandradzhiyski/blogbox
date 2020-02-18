package com.snmi.rest;

import com.snmi.dto.CommentDTO;
import com.snmi.service.CommentService;
import com.snmi.service.CommentServiceImpl;
import com.snmi.util.BlogBoxGlobalConstant;
import io.swagger.annotations.Api;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Comment API")
@RestController
@RequestMapping("api/v1/comments")
public class CommentResource {

    private final CommentService commentService;

    public CommentResource(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "Create comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment has been created successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 401, message = "Unauthorized user",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "News or user doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.createComment(commentDTO));
    }

    @ApiOperation(value = "Get specific comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment has been retrieved successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "Comment doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable(BlogBoxGlobalConstant.PATH_COMMENT_ID) Long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(commentId));
    }

    @ApiOperation(value = "Update specific comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment has been updated successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 401, message = "Unauthorized user",
                    response = ResponseEntity.class),
            @ApiResponse(code = 403, message = "You can't manipulate other comments",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "Comment doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @PutMapping("")
    public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.updateComment(commentDTO));
    }

    @ApiOperation(value = "Delete specific comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment has been deleted successfully",
                    response = ResponseEntity.class),
            @ApiResponse(code = 401, message = "Unauthorized user",
                    response = ResponseEntity.class),
            @ApiResponse(code = 403, message = "You can't manipulate other comments",
                    response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "Comment doesn't exist",
                    response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Something wrong with our service",
                    response = ResponseEntity.class)
    })
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable(BlogBoxGlobalConstant.PATH_COMMENT_ID) Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
