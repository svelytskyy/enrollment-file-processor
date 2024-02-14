package com.availity.enrollments.service;

import com.availity.enrollments.entity.Enrollee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Service class responsible for processing CSV files containing enrollee data.
 */
@Service
public class FileProcessorService {

    private static final Logger logger = LogManager.getLogger(FileProcessorService.class);
    @Autowired
    private ReadService readService;
    @Autowired
    private WriteService writeService;

    public FileProcessorService(ReadService readService, WriteService writeService) {
        this.readService = readService;
        this.writeService = writeService;
    }

    public FileProcessorService() {
    }

    /**
     * Processes CSV files in the specified folder path, separating enrollees by insurance company
     * and writing them to individual files sorted by last name and first name.
     *
     * @param folderPath the folder path containing CSV files
     * @param outPath    the output directory path where the processed files will be written
     * @throws IOException if an I/O error occurs while reading or writing files
     */
    public void processCsvFiles(String folderPath, String outPath) throws IOException {
        File directory = new File(folderPath);
        File[] files = directory.listFiles();
        EnrollmentDS ds = new EnrollmentDS();
        if (files != null) {
            Map<String, TreeSet<Enrollee>> enrolleesByCompany = new HashMap<>();

            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".csv")) {
                   readService.readCSVFile(file,ds);
                }
           }
           writeService.writeEnrolleesToFiles(ds.getMapEnrollee(), outPath);
           if(ds.getErrorList() != null && ds.getErrorList().size() > 0){
               writeService.writeErrorRecords(ds.getErrorList(), outPath);
           }
        } else {
           logger.error("No files found in directory: " + directory.getPath());
        }
    }
}
