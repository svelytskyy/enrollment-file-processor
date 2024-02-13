package com.availity.entrollments;

//import com.availity.enrollments.service.FileProcessorService;
import com.availity.enrollments.service.FileProcessorService;
import com.availity.enrollments.service.EnrolleeProcessorHelper;
import com.availity.enrollments.service.ReadService;
import com.availity.enrollments.service.WriteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppEnrollments {

    public static void main(String[] args) {

        SpringApplication.run(AppEnrollments.class, args);
    }

    @Bean
    public FileProcessorService fileProcessorService() {
        return new FileProcessorService();
    }

    @Bean
    public ReadService readService() {
        return new ReadService();
    }

    @Bean
    public EnrolleeProcessorHelper processService() {
        return new EnrolleeProcessorHelper();
    }

    @Bean
    public WriteService writeService() {
        return new WriteService();
    }
}
