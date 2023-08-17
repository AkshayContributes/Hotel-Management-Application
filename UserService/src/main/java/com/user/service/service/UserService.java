package com.user.service.service;

import com.user.service.entities.User;

import java.util.List;

public interface UserService {

    public User saveUser(User user);

    public List<User> getAllUsers();

    public User getUserById(String userId);


}
