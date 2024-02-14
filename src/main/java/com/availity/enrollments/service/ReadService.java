package com.availity.enrollments.service;

import com.availity.enrollments.entity.Enrollee;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Service class responsible for reading CSV files containing enrollee data.
 */
@Service
public class ReadService {


    private static final Logger logger = LogManager.getLogger(ReadService.class);

    /**
     * Reads a CSV file containing enrollee data and adds enrollees to the enrollment data structure.
     *
     * @param inputFile the input CSV file
     * @param ds        the enrollment data structure
     * @throws IOException if an I/O error occurs while reading the file
     */
   public void readCSVFile(File inputFile, EnrollmentDS ds) throws IOException {
       Reader reader = null;
       Enrollee enrollee = null;
        try {
            reader = new FileReader(inputFile);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for (CSVRecord record : csvParser) {
                try {
                    if(record.get("userId") ==null || record.get("company") == null) {
                        throw new Exception("Required field is null");
                    }
                    enrollee = new Enrollee(
                            record.get("userId"),
                            record.get("fName"),
                            record.get("lName"),
                            Integer.parseInt(record.get("version")),
                            record.get("company")
                    );
                    EnrolleeProcessorHelper.addEnrollee(ds, enrollee);
                } catch (Exception e) {
                    logger.error("error reading record file : " + inputFile.getName());
                    if (record != null) logger.error("actual record : " + record);
                    logger.error(e.getStackTrace());
                    ds.getErrorList().add(enrollee);
                }
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error("Error closing reader", e);
                    logger.error(e.getStackTrace());
                }
            }
        }
    }
}
