package com.softuni.fitshop.service.impl;

import com.softuni.fitshop.model.custom.FitShopUser;
import com.softuni.fitshop.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FitShopUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public FitShopUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.userRepository
                .findByUsername(username)
                .map(u -> {
                    Set<SimpleGrantedAuthority> authorities = u.getRoles()
                            .stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name()))
                            .collect(Collectors.toSet());
                    return new FitShopUser(
                            u.getUsername(),
                            u.getPassword(),
                            authorities,
                            u.getExperienceLevel().name()
                    );
                })
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));

    }
}