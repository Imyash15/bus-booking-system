package com.bookingService.service;

import com.bookingService.exception.ResourceNotFoundException;
import com.bookingService.model.User;
import com.bookingService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user){
        User registeredUser = userRepository.findByEmail(user.getEmail());
        if (registeredUser !=null) throw new ResourceNotFoundException(" User Already Registered With This Email "+ user.getEmail());

        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        return userRepository.save(user);
    }

    public User getUserById(Integer userId) {
         return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Invalid User id"));
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User updateUser(User user,Integer userId){
        User user1 = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Invalid User Id"));

        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setEmail(user.getEmail());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setMobile(user.getMobile());
        user1.setRoles(user.getRoles());

        return userRepository.save(user1);

    }

    public User deleteUser(Integer userId){

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()){
            User existingUser = user.get();
            userRepository.delete(existingUser);

            return existingUser;
        }else
            throw new ResourceNotFoundException("User id not Available with this id "+ userId);

    }
}
