package com.bookingService.controller;

import com.bookingService.exception.GeneralResponse;
import com.bookingService.exception.UserException;
import com.bookingService.model.User;
import com.bookingService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/user/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User user1 = userService.createUser(user);
        return new ResponseEntity<>(user1, HttpStatus.CREATED);
    }
    @PutMapping("/user/{userId}")
    public ResponseEntity<GeneralResponse> updateUser(@RequestBody User user, @PathVariable Integer userId) throws UserException{

        User user1 = userService.updateUser(user, userId);
        return ResponseEntity.ok(new GeneralResponse("Update Successfully",true,user1));
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<GeneralResponse> getUserById(@PathVariable Integer userId) throws UserException{
        User userById = userService.getUserById(userId);
        return ResponseEntity.ok(new GeneralResponse("Successfully Fetched",true,userById));
    }

    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity<GeneralResponse> deleteUser(@PathVariable Integer userId) throws UserException {
        userService.deleteUser(userId);

        return ResponseEntity.ok(new GeneralResponse("Deleted Successfully",true,""));
    }
}
