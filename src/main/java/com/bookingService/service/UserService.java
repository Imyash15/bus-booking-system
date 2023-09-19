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

    public User createUser(User user) throws UserException {
        User registeredUser = userRepository.findByEmail(user.getEmail());
        if (registeredUser !=null) throw new UserException(" User Already Registered With This Email "+ user.getEmail());
        return userRepository.save(user);
    }

    public User getUserById(Integer userId) throws UserException {
         return userRepository.findById(userId).orElseThrow(() -> new UserException("Invalid User id"));
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
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

    public User deleteUser(Integer userId) throws UserException{

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()){
            User existingUser = user.get();
            userRepository.delete(existingUser);

            return existingUser;
        }else
            throw new UserException("User id not Available with this id "+ userId);

    }
}
