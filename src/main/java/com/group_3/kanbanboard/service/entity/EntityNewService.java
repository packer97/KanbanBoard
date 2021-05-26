package com.group_3.kanbanboard.service.entity;

import java.util.List;
import java.util.UUID;

public interface EntityNewService<T, ID> {

    T saveEntity(T entity);

    T getEntity(ID id);

    List<T> getAllEntity();

    boolean exists(ID id);

    void deleteById(ID id);

}
