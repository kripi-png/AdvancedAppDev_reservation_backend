package com.kripi.reservationbackend;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.core.io.Resource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(locations = {
        "classpath*:spring/applicationContext.xml",
        "classpath*:spring/applicationContext-jpa.xml",
        "classpath*:spring/applicationContext-security.xml" })
public class ResourceLoadingTests {

    @Test
    void testClasspathResource() {
        String fileName = "app.pub"; // Adjust the file name accordingly
        Resource resource = new ClassPathResource(fileName);
        assertTrue(resource.exists(), "Classpath resource not found: " + fileName);
    }
}