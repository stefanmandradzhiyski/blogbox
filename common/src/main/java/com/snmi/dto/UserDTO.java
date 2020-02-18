package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Must be used when you want to send or receive user data
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@ApiModel(description = "Class represents a user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {

    @ApiModelProperty(name = "User's id", example = "2", access = "READ_WRITE")
    private Long id;

    @ApiModelProperty(name = "User's first name", example = "Stefan", access = "READ_WRITE", required = true)
    @NotNull(message = "First name is required")
    @Size(min = 3, max = 20, message = "First name length must be between 3 and 20")
    private String firstName;

    @ApiModelProperty(name = "User's last name", example = "Mandradzhiyski", access = "READ_WRITE", required = true)
    @NotNull(message = "Last name is required")
    @Size(min = 3, max = 25, message = "Last name length must be between 3 and 25")
    private String lastName;

    @ApiModelProperty(name = "User's username", example = "snmi", access = "READ_WRITE", required = true)
    @NotNull(message = "Username is required")
    @Size(min = 3, max = 25, message = "Username length must be between 3 and 25")
    private String username;

    @ApiModelProperty(name = "User's password", example = "12345S", access = "WRITE_ONLY")
    private String password;

    @ApiModelProperty(name = "User's favourites count", access = "READ_ONLY")
    private Long favouritesCount;

    @ApiModelProperty(name = "User's registration date", access = "READ_ONLY")
    private Date createdAt;

    @ApiModelProperty(name = "User's last update date", access = "READ_ONLY")
    private Date updatedAt;

    @ApiModelProperty(name = "User version", access = "READ_ONLY")
    private Long version;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getFavouritesCount() {
        return favouritesCount;
    }

    public void setFavouritesCount(Long favouritesCount) {
        this.favouritesCount = favouritesCount;
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
}
