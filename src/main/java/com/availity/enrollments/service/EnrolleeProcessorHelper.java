package com.availity.enrollments.service;

import com.availity.enrollments.entity.Enrollee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Helper class providing utility methods for processing enrollees.
 */

@Service
public class EnrolleeProcessorHelper {

    private static final Logger logger = LogManager.getLogger(EnrolleeProcessorHelper.class);

    /**
     * Adds an enrollee to the enrollment data structure, ensuring that only
     * enrollees with the highest version are included for each insurance company.
     *
     * @param ds       the enrollment data structure
     * @param enrollee the enrollee to add
     */
    public static void addEnrollee(EnrollmentDS ds, Enrollee enrollee){
        Map<String, SortedSet<Enrollee>>mapEnrollee = ds.getMapEnrollee();
        //New Insurance company in Data Structure
        if(mapEnrollee.get(enrollee.getInsuranceCompany()) == null){
            //caching added users for search functionality
            ds.getCachedUserId().add(enrollee.getUserId());
            SortedSet<Enrollee> sortedSetEnrollee = new TreeSet<>();
            sortedSetEnrollee.add(enrollee);
            mapEnrollee.put(enrollee.getInsuranceCompany(), sortedSetEnrollee);
        }else{
            Set<String> cachedUsers = ds.getCachedUserId();
            SortedSet<Enrollee> sortedSetEnrollee = ds.getMapEnrollee().get(enrollee.getInsuranceCompany());
            //search users for duplicates with high versions only not cached in cachedUsers map
            if(!cachedUsers.contains(enrollee.getUserId())){
                sortedSetEnrollee.add(enrollee);
                cachedUsers.add(enrollee.getUserId());
            }else{
                Enrollee existEnrollee = findEnrolleeByUserId(sortedSetEnrollee, enrollee.getUserId());
                if(existEnrollee.getVersion() < enrollee.getVersion()){
                    existEnrollee.setVersion(enrollee.getVersion());
                    existEnrollee.setFirstName(enrollee.getFirstName());
                    existEnrollee.setLastName(enrollee.getLastName());
                }
            }
        }
    }

    /**
     * Finds an enrollee by user ID in a sorted set of enrollees.
     *
     * @param enrollees the sorted set of enrollees
     * @param userId    the user ID to search for
     * @return the enrollee with the specified user ID, or null if not found
     */
    private static Enrollee findEnrolleeByUserId(SortedSet<Enrollee> enrollees, String userId) {
        for (Enrollee enrollee : enrollees) {
            if (enrollee.getUserId().equals(userId)) {
                return enrollee;
            }
        }
        return null;
    }

    /**
     * Deletes all files in the specified folder. Cleanup output folder
     *
     * @param folder the folder path
     */
   public static void deleteFiles(String folder){
        Path dir = Paths.get(folder);

        try {
            // Use Files.walk to traverse the directory
            Files.walk(dir)
                    .filter(Files::isRegularFile) // Filter to delete only files, not directories
                    .forEach(path -> {
                        try {
                           Files.delete(path); // Delete each file
                           logger.info("Deleted file: " + path);
                        } catch (IOException e) {
                            logger.error("Error during delete files in out put folder. Init clean" + e + "Output folder : " + folder);
                           logger.error(e.getStackTrace());
                        }
                    });
        } catch (IOException e) {
           logger.error("Output folder : " + folder + e);
           logger.error(e.getStackTrace());
        }
    }

}
