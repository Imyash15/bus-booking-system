package com.bookingService.service;

import com.bookingService.exception.UserException;
import com.bookingService.model.User;
import com.bookingService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
       return userRepository.save(user);
    }

    public User getUserById(Integer userId) throws UserException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("Invalid User id"));
        return user;
    }

    public List<User> getAllUser(){
        List<User> userList = userRepository.findAll();
        return userList;
    }

    public User updateUser(User user,Integer userId) throws UserException {
        User user1 = userRepository.findById(userId).orElseThrow(() -> new UserException("Invalid User Id"));

        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        user1.setMobile(user.getMobile());

        return userRepository.save(user1);

    }

    public void deleteUser(Integer userID) throws UserException{
        User user = userRepository.findById(userID).orElseThrow(() -> new UserException("Invalid User Id"));
        userRepository.delete(user);
    }
}
