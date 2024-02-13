package com.availity.enrollments.service;

import com.availity.enrollments.entity.Enrollee;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileProcessorService {

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
        } else {
            System.err.println("No files found in directory: " + directory.getPath());
        }
    }
}
