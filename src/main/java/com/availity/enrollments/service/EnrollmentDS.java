package com.availity.enrollments.service;

import com.availity.enrollments.entity.Enrollee;
import lombok.Data;
import java.util.*;

@Data
public class EnrollmentDS {

    Map<String, SortedSet<Enrollee>> mapEnrollee = new HashMap<>();
    List<Enrollee> errorList = new ArrayList<>();
    Set<String> cachedUserId = new HashSet<>();

    public EnrollmentDS() {}
}
