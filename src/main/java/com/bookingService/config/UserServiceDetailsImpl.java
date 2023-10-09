package com.bookingService.config;

import com.bookingService.model.User;
import com.bookingService.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceDetailsImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);


        if (user ==null) throw new UsernameNotFoundException("User Details not Found with this UserName "+ username);
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),getAuthorities(user));

    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
//        return user.getRoles().stream().filter(Objects::nonNull).map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
        return  user.getRoles().stream().map(role->new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }
}