package com.availity.entrollments;

import com.availity.enrollments.service.FileProcessorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FileProcessingRunner implements CommandLineRunner {

    @Autowired
    private FileProcessorService fileProcessorService;
    private static final Logger logger = LogManager.getLogger(FileProcessingRunner.class);
    @Autowired
    public FileProcessingRunner(FileProcessorService fileProcessorService) {
        this.fileProcessorService = fileProcessorService;
    }



    @Override
    public void run(String... args) throws Exception {
        // Call the file processing service to process CSV file
        String inPath, outPath;
        if (args != null && args.length > 1) {
            inPath = args[0];
            outPath = args[1];
            logger.info("paths used : " + "input : " + inPath +  " output :" + outPath);
        } else{
            outPath = getClass().getClassLoader().getResource("output").getPath();
            inPath = getClass().getClassLoader().getResource("input").getPath();
            logger.warn("No paths provided. Default paths used : " + "input : " + inPath +  " output :" + outPath);
        }
        fileProcessorService.processCsvFiles(inPath,outPath);
    }
}
