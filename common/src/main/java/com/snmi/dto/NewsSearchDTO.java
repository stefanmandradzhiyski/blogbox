package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.snmi.enums.NewsFilter;
import com.snmi.enums.Order;
import com.snmi.enums.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Must be used when you want to search news
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@ApiModel(description = "News search request data transfer object")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsSearchDTO {

    @ApiModelProperty(name = "Search by that news id", access = "WRITE_ONLY", example = "6")
    private Long id;

    @ApiModelProperty(name = "Search by that news name", access = "WRITE_ONLY", example = "news")
    private String name;

    @ApiModelProperty(name = "Search by that news writer username", access = "WRITE_ONLY", example = "writer")
    private String username;

    @ApiModelProperty(name = "Filter all last week results if parameter is true", access = "WRITE_ONLY", example = "true")
    private boolean lastWeek = false;

    @ApiModelProperty(name = "Filter all results by specific value", access = "WRITE_ONLY", required = true)
    @NotNull(message = "Search request must has a specific filter")
    private NewsFilter newsFilter;

    @ApiModelProperty(name = "Filter all results by news status", access = "WRITE_ONLY")
    private Status status;

    @ApiModelProperty(name = "Choose result filtering order", access = "WRITE_ONLY", required = true)
    @NotNull(message = "Search request must has a specific order")
    private Order order;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getLastWeek() {
        return lastWeek;
    }

    public void setLastWeek(boolean lastWeek) {
        this.lastWeek = lastWeek;
    }

    public NewsFilter getNewsFilter() {
        return newsFilter;
    }

    public void setNewsFilter(NewsFilter newsFilter) {
        this.newsFilter = newsFilter;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
