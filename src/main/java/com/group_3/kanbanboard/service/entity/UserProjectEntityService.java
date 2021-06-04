package com.group_3.kanbanboard.service.entity;

import java.util.List;

public interface UserProjectEntityService<T, F, S> {


    List<T> getFromFirst(F firstEntity);

    List<T> getFromSecond(S secondEntity);

    T getFromDependencies(F firstEntity, S secondEntity);


}
