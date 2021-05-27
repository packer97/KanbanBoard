package com.group_3.kanbanboard.service;

import com.group_3.kanbanboard.rest.dto.UserResponseDto;
import com.group_3.kanbanboard.rest.dto.UserRequestDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDto getUserById(UUID id);

    UserResponseDto getUserByUsername(String username);

    List<UserResponseDto> getAllUsers();

    UserResponseDto addUser(UserRequestDto userRequestDto);

    UserResponseDto updateUser(UUID id, UserRequestDto userRequestDto);

    void deleteUserById(UUID id);

    void addAdmin();

}
