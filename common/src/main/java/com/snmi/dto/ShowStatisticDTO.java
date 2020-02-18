package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Must be used when you want to read some kind of statistic for specific news
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@ApiModel(description = "Show news statistic data transfer object")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShowStatisticDTO {

    @ApiModelProperty(name = "User id", access = "READ_ONLY", example = "6", required = true)
    @NotNull(message = "Statistic user id")
    private Long id;

    @ApiModelProperty(name = "Username", access = "READ_ONLY", example = "username", required = true)
    @NotNull(message = "Statistic username")
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
