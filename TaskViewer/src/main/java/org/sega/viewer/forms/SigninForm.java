package org.sega.viewer.forms;

import org.hibernate.validator.constraints.NotBlank;


public class SigninForm {

    @NotBlank(message = "{notBlank.message}")
    private String userType;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
