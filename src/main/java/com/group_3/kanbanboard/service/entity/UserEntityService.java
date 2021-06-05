package com.group_3.kanbanboard.service.entity;

import com.group_3.kanbanboard.entity.UserEntity;

import java.util.UUID;

public interface UserEntityService extends EntityNewService<UserEntity, UUID> {

    UserEntity getEntity(String username);

}
