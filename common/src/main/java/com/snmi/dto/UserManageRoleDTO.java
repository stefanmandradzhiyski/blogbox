package com.snmi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.snmi.enums.ManageRoleAction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Must be used when you want to manage specific user roles
 * @author Stefan Mandradzhiyski
 * @version 1.0
 */
@ApiModel(description = "Manage user role data transfer object")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserManageRoleDTO {

    @ApiModelProperty(name = "User id", access = "WRITE_ONLY", example = "1", required = true)
    @NotNull(message = "User id is required")
    private Long userId;

    @ApiModelProperty(
            name = "Role id which will be granted to the user or removed from him", access = "WRITE_ONLY",
            example = "2",
            required = true
    )
    @NotNull(message = "Role id is required")
    private Long roleId;

    @ApiModelProperty(name = "Manage user role by choose the action promote or demote", access = "WRITE_ONLY", required = true)
    @NotNull(message = "Manage role action is required")
    private ManageRoleAction manageRoleAction;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public ManageRoleAction getManageRoleAction() {
        return manageRoleAction;
    }

    public void setManageRoleAction(ManageRoleAction manageRoleAction) {
        this.manageRoleAction = manageRoleAction;
    }
}
