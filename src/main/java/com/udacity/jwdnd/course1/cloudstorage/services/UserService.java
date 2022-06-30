package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public
class UserService {

    private UsersMapper usersMapper;
    private HashService hashService;

    public UserService(UsersMapper usersMapper, HashService hashService) {
        this.usersMapper = usersMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String userName){
        return usersMapper.getUser(userName) == null;
    }

    public Users getUser(String userName) throws NullPointerException{
        if (isUsernameAvailable(userName)){
            throw new NullPointerException();
        }
        Users user = usersMapper.getUser(userName);
        return user;
    }

    public int createUser(Users user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return usersMapper.addUser(new Users(user.getUserName(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
    }

    public void updateUser(Users user) throws NullPointerException{
        if (isUsernameAvailable(user.getUserName())){
            throw new NullPointerException();
        }
        usersMapper.updateUser(user);
    }

    public void deleteUser(Users user) throws NullPointerException{
        if (isUsernameAvailable(user.getUserName())){
            throw new NullPointerException();
        }
        usersMapper.deleteUser(user);
    }
}