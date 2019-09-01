package com.waes.diff.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Application class that runs Diff Application Service with all required configuration.
 * Diff Application Service is a service to compare incoming plain text values to check discrepancies and provide
 * validation result appropriate to discrepancy type
 */
@SpringBootApplication
public class AssessmentDiffServiceApplication {

    /**
     * Defines the main class to Run Application
     *
     * @param args - command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AssessmentDiffServiceApplication.class, args);
    }

}
