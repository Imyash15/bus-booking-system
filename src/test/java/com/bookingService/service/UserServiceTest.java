package com.bookingService.service;

import com.bookingService.exception.UserException;
import com.bookingService.model.User;
import com.bookingService.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void testCreateUser() throws UserException {

        //create user object
        User expectedUser=new User();
        expectedUser.setFirstName("Ram");
        expectedUser.setMobile("321654987");
        expectedUser.setEmail("test@gmail.com");

        //Checking  user  registered by email id
        when(userRepository.findByEmail(expectedUser.getEmail())).thenReturn(null);
        //Mock the behavior to save
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        //Act
        User actualUser = userService.createUser(expectedUser);

        //verify userRepository
        verify(userRepository,times(1)).findByEmail(expectedUser.getEmail());
        verify(userRepository,times(1)).save(expectedUser);

        //Assert
        assertEquals(expectedUser,actualUser);

    }

    @Test
    void testCreateUserUserAlreadyRegistered(){

        //create user object
        User user= new User();
        user.setEmail("test@gmail.com");

        //Mock
        when(userRepository.findByEmail(user.getEmail())).thenReturn(new User());

        //Act  and Assert
        assertThrows(UserException.class,()-> userService.createUser(user));

    }


    @Test
    void testGetUserById() throws UserException {

        User user = new User();
        user.setUserId(user.getUserId());

        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));

        User userById = userService.getUserById(user.getUserId());

        verify(userRepository,times(1)).findById(user.getUserId());

        assertEquals(user,userById);

    }

    @Test
    void testGetUserByIdInvalidUserId(){
        User user = new User();
        user.setUserId(user.getUserId());

        when(userRepository.findById(user.getUserId())).thenReturn(Optional.empty());

        assertThrows(UserException.class,()-> userService.getUserById(user.getUserId()));


    }

    @Test
    void testGetAllUser() {
        List<User> users =new ArrayList<>();

        //create Users
        User user1= new User();
        user1.setUserId(1);
        user1.setFirstName("Bob");

        User user2= new User();
        user2.setUserId(2);
        user2.setFirstName("Casey");

        //add user in list
        users.add(user1);
        users.add(user2);

        //Mock Behavior
        when(userRepository.findAll()).thenReturn(users);

        //Act
        List<User> allUser = userService.getAllUser();

        //Verify
        verify(userRepository,times(1)).findAll();

        //Assert
        assertEquals(users,allUser);


    }

    @Test
    void testUpdateUser() throws UserException {
        int userId=1;
        //Crete User Object
        User existingdUser= new User();
        existingdUser.setUserId(userId);
        existingdUser.setFirstName("Raj");
        existingdUser.setEmail("test@gmail.com");

        //update use object
        User updatedUser=new User();
        updatedUser.setFirstName("Ronit");
        updatedUser.setEmail("ronit@gmail.com");


        when(userRepository.findById(userId)).thenReturn(Optional.of(existingdUser));
        when(userRepository.save(existingdUser)).thenReturn(updatedUser);


        //Act
        User result = userService.updateUser(updatedUser, userId);

        //Assert
        assertEquals(updatedUser,result);

    }


    @Test
    void testUpdateUserWithInvalidUserId(){
        int userId=1;
        //Crete User Object
        User user= new User();
        user.setUserId(userId);
        user.setFirstName("Raj");
        user.setEmail("test@gmail.com");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserException.class,()-> userService.updateUser(user,userId));

    }

    @Test
    void testDeleteUser() throws UserException {

        //create User object
        User existingUser= new User();
        existingUser.setUserId(1);
        existingUser.setFirstName("Ramesh");


        when(userRepository.findById(existingUser.getUserId())).thenReturn(Optional.of(existingUser));

        //Act
        User deletedUser = userService.deleteUser(existingUser.getUserId());

        //Verify
        verify(userRepository,times(1)).findById(existingUser.getUserId());
        verify(userRepository,times(1)).delete(existingUser);

        //Assert
        assertEquals(existingUser,deletedUser);


    }

    @Test
    void deleteUserWithUserNotFound(){
        User existingUser= new User();
        existingUser.setUserId(1);
        existingUser.setFirstName("Ramesh");

        //Act
        when(userRepository.findById(existingUser.getUserId())).thenReturn(Optional.empty());

        //Assert
        assertThrows(UserException.class,()-> userService.deleteUser(existingUser.getUserId()));



    }
}