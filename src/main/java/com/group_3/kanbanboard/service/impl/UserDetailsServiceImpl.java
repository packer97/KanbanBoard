package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.exception.UserNotFoundException;
import com.group_3.kanbanboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String s) {
    UserEntity userFromDb = userRepository.findByUsername(s)
        .orElseThrow(() -> new UserNotFoundException("user.notExist" , s));
    return new User(userFromDb.getUsername(), userFromDb.getPassword(), userFromDb.getRoles());
  }
}
