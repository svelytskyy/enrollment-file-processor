package com.availity.enrollments.service;

import com.availity.enrollments.entity.Enrollee;
import lombok.Data;
import java.util.*;

/**
 * Data structure for storing enrollees, error list, and cached user IDs.
 */

@Data
public class EnrollmentDS {

    /** A map containing enrollees sorted by insurance company. */
    Map<String, SortedSet<Enrollee>> mapEnrollee = new HashMap<>();

    /** A list of enrollees with errors. */
    List<Enrollee> errorList = new ArrayList<>();

    /** A set of cached user IDs.
     * Requires to avoid extra search for duplicate users
     * */
    Set<String> cachedUserId = new HashSet<>();

    public EnrollmentDS() {}
}
