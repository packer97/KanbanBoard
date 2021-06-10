package com.group_3.kanbanboard.service.impl;

import com.group_3.kanbanboard.exception.ForbiddenException;
import com.group_3.kanbanboard.service.PrincipalService;
import com.group_3.kanbanboard.service.UserProjectService;
import com.group_3.kanbanboard.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilServiceImpl implements UtilService {

    private final UserProjectService userProjectService;
    private final PrincipalService principalService;

    @Autowired
    public UtilServiceImpl(UserProjectService userProjectService,
                           PrincipalService principalService) {
        this.userProjectService = userProjectService;
        this.principalService = principalService;
    }

    @Override
    public boolean checkLeadAccess(UUID projectId) {
        boolean isLead = userProjectService
                .isUserLeadInProject(principalService.getPrincipalId(), projectId);
        if (!isLead) {
            throw new ForbiddenException("error.notLead");
        }
        return true;
    }
}
