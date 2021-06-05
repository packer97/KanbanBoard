package com.group_3.kanbanboard.specification;

import com.group_3.kanbanboard.entity.TaskEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public final class TaskSpecification {

    private TaskSpecification() {
    }

    public static Specification<TaskEntity> categoryOrStatusContainsIgnoreCase(final String searchTerm) {
        String containsLikePattern = getContainsLikePattern(searchTerm);
        return (root, cq, cb) -> cb.or(
                cb.like(cb.lower(root.get("taskCategory")), containsLikePattern),
                cb.like(cb.lower(root.get("taskStatus")), containsLikePattern)
        );
    }

    private static String getContainsLikePattern(String searchTerm) {
        if (StringUtils.isEmpty(searchTerm)) {
            return "%";
        }
        else {
            return "%" + searchTerm.toLowerCase() + "%";
        }
    }
}
