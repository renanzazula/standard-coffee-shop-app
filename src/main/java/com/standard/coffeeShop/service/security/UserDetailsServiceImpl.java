package com.standard.coffeeShop.service.security;

import com.standard.coffeeShop.repository.security.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername: {}",username);

        UserDetails userResponse = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User name: " + username + "not found"));

        log.debug("loadUserByUsername: {}",userResponse);

        return userResponse;
    }


}
