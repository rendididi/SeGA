package org.sega.viewer.forms;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Raysmond on 9/4/15.
 */
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
