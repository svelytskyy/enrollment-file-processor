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

@Service
public class ReadService {


    private static final Logger logger = LogManager.getLogger(ReadService.class);

    public void readCSVFile(File inputFile, EnrollmentDS ds) throws IOException {
        try (Reader reader = new FileReader(inputFile);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : csvParser) {
                try {
                    Enrollee enrollee = new Enrollee(
                            record.get("userId"),
                            record.get("fName"),
                            record.get("lName"),
                            Integer.parseInt(record.get("version")),
                            record.get("company")
                    );
                    EnrolleeProcessorHelper.addEnrollee(ds, enrollee);
                 }catch(Exception e) {
                    logger.error("error reading record file : " + inputFile.getName());
                    if (record != null) logger.error("actual record : " + record.toString());
                    logger.error(e);
                }
            }
        }
    }
}
