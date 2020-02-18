package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.snmi.enums.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Must be used when you want to update the status of specific news
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@ApiModel(description = "News update status data transfer object")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsUpdateStatusDTO {

    @ApiModelProperty(name = "News id which status will be updated", access = "WRITE_ONLY", example = "6", required = true)
    @NotNull(message = "Update status must be applied to specific news")
    private Long newsId;

    @ApiModelProperty(name = "News new status", access = "WRITE_ONLY", example = "6", required = true)
    @NotNull(message = "Update status must contain new news status")
    private Status status;

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
