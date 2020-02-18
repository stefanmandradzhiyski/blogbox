package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.snmi.enums.Rating;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Must be used when you want to send or receive comment data
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@ApiModel(description = "Comment data transfer object")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO implements Serializable {

    @ApiModelProperty(name = "Comment id", access = "READ_ONLY", example = "6")
    private Long id;

    @ApiModelProperty(name = "Comment news id", access = "READ_WRITE", example = "6", required = true)
    @NotNull(message = "Comment must be related to some news")
    private Long newsId;

    @ApiModelProperty(name = "Comment news name", access = "READ_ONLY", example = "news name")
    private String newsName;

    @ApiModelProperty(name = "Comment user id", access = "READ_ONLY", example = "2")
    private Long userId;

    @ApiModelProperty(name = "Comment user username", access = "READ_ONLY", example = "user")
    private String username;

    @ApiModelProperty(name = "Comment rating", access = "READ_WRITE", required = true)
    @NotNull(message = "Comment must has a rating")
    private Rating rating;

    @ApiModelProperty(name = "Comment", access = "READ_WRITE", example = "That news was really important", required = true)
    @NotNull(message = "Comment must be related to some news")
    @Size(min = 5, max = 250, message = "Comment length must be between 5 and 250 symbols")
    private String fullComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getFullComment() {
        return fullComment;
    }

    public void setFullComment(String fullComment) {
        this.fullComment = fullComment;
    }
}
