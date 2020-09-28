package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.addUser(new User(user.getUsername(), encodedSalt, hashedPassword, user.getFirstname(), user.getLastname()));
    }

    public User getUser(String username) throws NullPointerException{
        if (isUsernameAvailable(username)){
            throw new NullPointerException();
        }
        else {
            User user = userMapper.getUser(username);
            return user;
        }
    }

    public void deleteUser(User user) throws NullPointerException{
        if (isUsernameAvailable(user.getUsername())){
            throw new NullPointerException();
        }
        else {
            userMapper.deleteUser(user);
        }
    }

    public void updateUser(User user) throws NullPointerException {
        if (isUsernameAvailable(user.getUsername())){
            throw new NullPointerException();
        }
        userMapper.updateUser(user);
    }
}
