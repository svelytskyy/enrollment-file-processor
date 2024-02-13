package com.availity.enrollments.service;

import com.availity.enrollments.entity.Enrollee;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class WriteService {

    public void writeEnrolleesToFiles(Map<String, SortedSet<Enrollee>> enrolleesByCompany, String outPath) {
        EnrolleeProcessorHelper.deleteFiles(outPath);
        enrolleesByCompany.forEach((company, enrollees) -> {
            String safeCompanyName = company.replaceAll("[^a-zA-Z0-9]", "");
            String filename = outPath + File.separator + safeCompanyName + ".csv";
             try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                writer.println("User Id,First Name,Last Name,Version,Insurance Company");
                enrollees.forEach(enrollee -> writer.println(enrollee.toCSVString()));
                System.out.println("Enrollees for company " + company + " written to file: " + filename);
            } catch (IOException e) {
                System.err.println("Error writing enrollees to file: " + filename);
                e.printStackTrace();
            }
        });
    }
}
