package com.availity.enrollments.service;
import com.availity.enrollments.entity.Enrollee;
import com.availity.enrollments.service.EnrolleeProcessorHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class WriteService {

    private static final Logger logger = LogManager.getLogger(WriteService.class);
    public void writeEnrolleesToFiles(Map<String, SortedSet<Enrollee>> enrolleesByCompany, String outPath) throws IOException {
        Path outDirectory = Paths.get(outPath);
        EnrolleeProcessorHelper.deleteFiles(outPath);

        enrolleesByCompany.forEach((company, enrollees) -> {
            String safeCompanyName = company.replaceAll("\\W+", "");
            Path filePath = outDirectory.resolve(safeCompanyName + ".csv");

            try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(filePath))) {
                writer.println("User Id,First Name,Last Name,Version,Insurance Company");
                enrollees.forEach(enrollee -> writer.println(enrollee.toCSVString()));
                logger.debug("Enrollees for company " + company + " written to file: " + filePath);
            } catch (IOException e) {
                logger.error("Error writing enrollees to file: " + filePath);
                logger.error(e);
            }
        });
    }
}
