package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.snmi.enums.Order;
import com.snmi.enums.UserFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Must be used when you want to search users
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@ApiModel(description = "User search request data transfer object")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSearchDTO {

    @ApiModelProperty(name = "Search by that user id", access = "WRITE_ONLY", example = "6")
    private Long id;

    @ApiModelProperty(name = "Search by that user first name", access = "WRITE_ONLY", example = "stefan")
    private String firstName;

    @ApiModelProperty(name = "Search by that user last name", access = "WRITE_ONLY", example = "mandradzhiyski")
    private String lastName;

    @ApiModelProperty(name = "Search by that username", access = "WRITE_ONLY", example = "snmi")
    private String username;

    @ApiModelProperty(name = "Filter all last week results if parameter is true", access = "WRITE_ONLY", example = "true")
    private boolean lastWeek = false;

    @ApiModelProperty(name = "Filter all results by specific value", access = "WRITE_ONLY", required = true)
    @NotNull(message = "Search request must has a specific filter")
    private UserFilter userFilter;

    @ApiModelProperty(name = "Choose result filtering order", access = "WRITE_ONLY", required = true)
    @NotNull(message = "Search request must has a specific order")
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public UserFilter getUserFilter() {
        return userFilter;
    }

    public void setUserFilter(UserFilter userFilter) {
        this.userFilter = userFilter;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
