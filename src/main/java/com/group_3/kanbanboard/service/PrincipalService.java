package com.group_3.kanbanboard.service;

import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface PrincipalService {

  UUID getPrincipalId();

  void updatePrincipalPassword(String password);

  boolean isPrincipalPassword(String password);

  UserResponseDto getPrincipal();

  UserEntity getPrincipalEntity();

}
