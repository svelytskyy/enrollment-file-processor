package com.availity.enrollments.service;
import com.availity.enrollments.entity.Enrollee;
import com.availity.enrollments.service.EnrolleeProcessorHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Service class responsible for writing enrollees to files.
 */

public class WriteService {

    private static final Logger logger = LogManager.getLogger(WriteService.class);
    public void writeEnrolleesToFiles(Map<String, SortedSet<Enrollee>> enrolleesByCompany, String outPath) throws IOException {
        Path outDirectory = Paths.get(outPath);
        EnrolleeProcessorHelper.deleteFiles(outPath);

        for (Map.Entry<String, SortedSet<Enrollee>> entry : enrolleesByCompany.entrySet()) {
            String company = entry.getKey();
            SortedSet<Enrollee> enrollees = entry.getValue();
            String safeCompanyName = company.replaceAll("\\W+", "");
            Path filePath = outDirectory.resolve(safeCompanyName + ".csv");
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(Files.newBufferedWriter(filePath));
                writer.println("userId,fName,lName,version,company");
                for (Enrollee enrollee : enrollees) {
                    writer.println(enrollee.toCSVString());
                }
                logger.debug("Enrollees for company " + company + " written to file: " + filePath);
            } catch (IOException e) {
                logger.error("Error writing enrollees to file: " + filePath);
                logger.error(e.getStackTrace());
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }

    /**
     * Writes error records to an errors.csv file.
     *
     * @param errorRecords the list of error records
     * @throws IOException if an I/O error occurs while writing the file
     */
    public void writeErrorRecords(List<Enrollee> errorRecords, String outPath) throws IOException {
        BufferedWriter writer = null;
        String fullFIleName = outPath + File.separator + "error.csv";
        try {
            writer = new BufferedWriter(new FileWriter(outPath + File.separator + "error.csv"));
            // Write headers
            writer.write("Error Message\n");
            // Write error records
            for (Enrollee errorRecord : errorRecords) {
                writer.write(errorRecord.toCSVString() + "\n");
            }
            logger.info("Error records written to " + fullFIleName);
        } catch (IOException e) {
            logger.error("Error writing error records to file: " + fullFIleName + " error - " + e.getMessage());
            throw e; // Re-throw the exception to be handled by the caller if necessary
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    logger.error("Error closing writer for error file " + e.getMessage());
                }
            }
        }
    }
}
