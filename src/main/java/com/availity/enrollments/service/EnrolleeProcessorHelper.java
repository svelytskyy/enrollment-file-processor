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

@Service
public class EnrolleeProcessorHelper {

    private static final Logger logger = LogManager.getLogger(EnrolleeProcessorHelper.class);

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

    private static Enrollee findEnrolleeByUserId(SortedSet<Enrollee> enrollees, String userId) {
        for (Enrollee enrollee : enrollees) {
            if (enrollee.getUserId().equals(userId)) {
                return enrollee;
            }
        }
        return null;
    }

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
                            logger.error(e);
                        }
                    });
        } catch (IOException e) {
           logger.error(e);
        }
    }

}
