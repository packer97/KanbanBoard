package com.group_3.kanbanboard.service;

import java.util.List;
import java.util.UUID;

public interface EntityNewService<T, ID> {

    T saveEntity(T entity);

    T getEntity(ID id);

    List<T> getAllEntity();

    boolean exist(ID id);

    void deleteById(ID id);

}
