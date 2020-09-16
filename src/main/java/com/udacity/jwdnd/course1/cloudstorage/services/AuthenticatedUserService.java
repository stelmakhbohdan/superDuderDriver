package com.udacity.jwdnd.course1.cloudstorage.services;

import mapper.UserMapper;
import model.User;

public class AuthenticatedUserService {
    private UserMapper userMapper;
    private User loggedInUser;

    public AuthenticatedUserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void setUser(String username){
        this.loggedInUser= userMapper.getUser(username);
    }

    public String getLoggedInUserName() throws NullPointerException {
        if (loggedInUser!=null){
            return loggedInUser.getUsername();
        }else {
            throw new NullPointerException();
        }
    }

    public Integer getLoggedInUserId() throws NullPointerException{
        if (loggedInUser!=null){
            return loggedInUser.getUserid();
        }
        else{
            throw new NullPointerException();
        }
    }
    public String getLoggedInName() throws NullPointerException{
        if (loggedInUser!=null){
            return loggedInUser.getFirstname()+""+loggedInUser.getLastname();
        }
        else {
            throw new NullPointerException();
        }
    }
}

