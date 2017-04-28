package com.dark.domain;

import java.io.Serializable;

/**
 * redis-simple
 * User: dark xue
 * Date: 2017/4/28
 * Time: 15:20
 * description:
 */
public class UserInfo implements Serializable {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
