package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticatedUserService {

    @Autowired
    private UsersMapper usersMapper;
    private Users loggedInUser = null;

    public void setUser(String userName) {
        this.loggedInUser = usersMapper.getUser(userName);
    }


    public Users getLoggedInUser() {
        return loggedInUser;
    }

    public Integer getLoggedInUserId() throws NullPointerException{
        if(loggedInUser != null){
            return loggedInUser.getUserId();
        }else{
            throw new NullPointerException();
        }
    }


    public String getLoggedInUserName() throws NullPointerException{
        if(loggedInUser != null){
            return loggedInUser.getUserName();
        }else{
            throw new NullPointerException();
        }
    }

    public String getLoggedInName() throws NullPointerException{
        if(loggedInUser != null){
            return loggedInUser.getFirstName()+" "+loggedInUser.getLastName();
        }else{
            throw new NullPointerException();
        }
    }

}