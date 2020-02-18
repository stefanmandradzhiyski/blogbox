package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.snmi.enums.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Must be used when you want to send or receive news data
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@ApiModel(description = "News data transfer object")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsDTO implements Serializable  {

    @ApiModelProperty(name = "News id", access = "READ_ONLY", example = "6")
    private Long id;

    @ApiModelProperty(name = "News name", example = "Java upcoming features", access = "READ_WRITE", required = true)
    @NotNull(message = "News must has a name")
    @Size(min = 10, max = 50, message = "News name must be between 10 and 50")
    private String name;

    @ApiModelProperty(name = "News short description", example = "Java version '15'", access = "READ_WRITE", required = true)
    @NotNull(message = "News must has a short description")
    @Size(min = 10, max = 150, message = "News short description must be between 10 and 150")
    private String shortDescription;

    @ApiModelProperty(name = "News main information", example = "Full text about the new", access = "READ_WRITE", required = true)
    @NotNull(message = "News must has body text")
    @Size(min = 10, max = 5000, message = "News main information must be between 10 and 5000")
    private String mainInformation;

    @ApiModelProperty(name = "News views count", access = "READ_ONLY")
    private Long viewsCount;

    @ApiModelProperty(name = "News likes count", access = "READ_ONLY")
    private Long likesCount;

    @ApiModelProperty(name = "News views count", access = "READ_ONLY")
    private Long dislikesCount;

    @ApiModelProperty(name = "News favourites count", access = "READ_ONLY")
    private Long favouritesCount;

    @ApiModelProperty(name = "News status", access = "READ_WRITE")
    private Status status;

    @ApiModelProperty(name = "News user's id", example = "3", access = "READ_ONLY")
    private Long userId;

    @ApiModelProperty(name = "News user's name", access = "READ_ONLY", example = "1")
    private String username;

    @ApiModelProperty(name = "News created date", access = "READ_ONLY")
    private Date createdAt;

    @ApiModelProperty(name = "News last update date", access = "READ_ONLY")
    private Date updatedAt;

    @ApiModelProperty(name = "News version", access = "READ_ONLY")
    private Long version;

    @ApiModelProperty(name = "News comments", access = "READ_ONLY")
    private List<CommentDTO> commentDTOs = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getMainInformation() {
        return mainInformation;
    }

    public void setMainInformation(String mainInformation) {
        this.mainInformation = mainInformation;
    }

    public Long getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Long viewsCount) {
        this.viewsCount = viewsCount;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

    public Long getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(Long dislikesCount) {
        this.dislikesCount = dislikesCount;
    }

    public Long getFavouritesCount() {
        return favouritesCount;
    }

    public void setFavouritesCount(Long favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<CommentDTO> getCommentDTOs() {
        return commentDTOs;
    }

    public void setCommentDTOs(List<CommentDTO> commentDTOs) {
        this.commentDTOs = commentDTOs;
    }
}
