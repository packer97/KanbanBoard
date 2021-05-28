package com.group_3.kanbanboard.service.entity;

import com.group_3.kanbanboard.entity.UserEntity;
import com.group_3.kanbanboard.exception.UserNotFoundException;
import com.group_3.kanbanboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserEntityServiceImpl implements UserEntityService {
    private final UserRepository userRepository;

    @Autowired
    public UserEntityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity saveEntity(UserEntity entity) {
        return userRepository.save(entity);
    }

    @Override
    public UserEntity getEntity(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id = %s not found", userId)));
    }

    @Override
    public List<UserEntity> getAllEntity() {
        return userRepository.findAll();
    }

    @Override
    public boolean exists(UUID userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public void deleteById(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserEntity getEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username = %s not found", username)));
    }
}
