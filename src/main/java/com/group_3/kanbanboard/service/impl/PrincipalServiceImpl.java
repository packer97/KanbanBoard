package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.exception.UserNotFoundException;
import com.group_3.kanbanboard.mappers.UserMapper;
import com.group_3.kanbanboard.repository.UserRepository;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.service.PrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;
import java.util.UUID;

@Service
public class PrincipalServiceImpl implements PrincipalService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;


  @Autowired
  public PrincipalServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
  }

  public UserEntity getPrincipalEntity(){
    String currentPrincipalUsername = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    return userRepository.findByUsername(currentPrincipalUsername).orElseThrow(() -> new UserNotFoundException("Something gone wrong"));
  }

  @Override
  public UserResponseDto getPrincipal() {
    return userMapper.toResponseDto(getPrincipalEntity());
  }

  public boolean isPrincipalPassword(String password){
    return passwordEncoder.matches(password, getPrincipalEntity().getPassword());
  }

  @Override
  public UUID getPrincipalId() {
    return getPrincipalEntity().getId();
  }

  @Override
  public void updatePrincipalPassword(String password) {
    UserEntity principal = getPrincipalEntity();
    principal.setPassword(passwordEncoder.encode(password));
    userRepository.save(principal);
  }
}
