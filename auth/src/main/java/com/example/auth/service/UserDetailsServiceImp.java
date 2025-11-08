package com.example.auth.service;

import com.example.auth.Entites.User;
import com.example.auth.repostory.UserRepo;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private UserRepo userRepo;
    UserDetailsServiceImp( UserRepo userRepo){
        this.userRepo =userRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User>  account = userRepo.getUserByUsername(username);

        if(account.isEmpty())
            throw  new BadCredentialsException("Bad credential");

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + account.get().getRole().getNameRolle());

        return new org.springframework.security.core.userdetails.User(account.get().getUsername(),account.get().getPassword(),List.of(authority) );

    }
}
