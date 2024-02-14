package com.avality.enrollments;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import com.availity.enrollments.entity.Enrollee;
import com.availity.enrollments.service.EnrolleeProcessorHelper;
import com.availity.enrollments.service.EnrollmentDS;
import java.util.*;

class EnrolleeProcessorHelperTest {

    private EnrollmentDS ds;
    private Enrollee enrollee1;
    private Enrollee enrollee2;
    private Enrollee enrollee3;

    @BeforeEach
    void setUp() {
        ds = new EnrollmentDS();

        enrollee1 = new Enrollee("123", "John", "Doe", 1, "BCBS");
        enrollee2 = new Enrollee("123", "John", "Doe", 2, "BCBS");
        enrollee3 = new Enrollee("456", "Jane", "Doe", 1, "BCBS");

        EnrolleeProcessorHelper.addEnrollee(ds, enrollee1);
    }

    @Test
    @DisplayName("Add Enrollee Successfully")
    void testAddEnrollee() {
        assertTrue(ds.getMapEnrollee().get("BCBS").contains(enrollee1));
    }

    @Test
    @DisplayName("Overwrite Enrollee With Higher Version")
    void testOverwriteEnrolleeWithHigherVersion() {
        EnrolleeProcessorHelper.addEnrollee(ds, enrollee2);
        // After adding a duplicate with a higher version, it should overwrite the previous one
        assertTrue(ds.getMapEnrollee().get("BCBS").contains(enrollee2));
        assertEquals(1, ds.getMapEnrollee().get("BCBS").size());
    }

    @Test
    @DisplayName("Ignore Enrollee With Lower Version")
    void testIgnoreEnrolleeWithLowerVersion() {
        // Add enrollee3 which has a different userId
        EnrolleeProcessorHelper.addEnrollee(ds, enrollee3);
        // Try to add enrollee1 again, which has a lower version than enrollee2
        EnrolleeProcessorHelper.addEnrollee(ds, enrollee1);
        // Enrollee with lower version should be ignored
        assertTrue(ds.getMapEnrollee().get("BCBS").contains(enrollee1));
        assertFalse(ds.getMapEnrollee().get("BCBS").contains(enrollee2));
        assertTrue(ds.getMapEnrollee().get("BCBS").contains(enrollee3));
        assertEquals(2, ds.getMapEnrollee().get("BCBS").size());
    }

    @Test
    @DisplayName("Maintain Sorted Order")
    void testMaintainSortedOrder() {
        EnrolleeProcessorHelper.addEnrollee(ds, enrollee3);
        EnrolleeProcessorHelper.addEnrollee(ds, enrollee2);

        // Assuming that TreeSet is used in the EnrollmentDS to maintain the order
        SortedSet<Enrollee> enrollees = ds.getMapEnrollee().get("BCBS");
        assertEquals(2, enrollees.size());

        // The first enrollee should be enrollee3 as it's sorted by last and first name
        assertEquals(enrollee3, enrollees.first());
    }
}
