package com.mabelowusu.pulse_check_api.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class DotenvConfig {

    public static void loadEnvironmentVariables() {
        try {
            // Try to load with dotenv first
            try {
                Dotenv dotenv = Dotenv.configure()
                        .directory("C:\\Users\\Mabel\\Desktop\\Spring-Boot\\Pulse-Check-API\\pulse-check-api")
                        .load();

                // Load all environment variables and set them as system properties
                dotenv.entries().forEach(entry -> {
                    System.setProperty(entry.getKey(), entry.getValue());
                    System.out.println("Loaded environment variable: " + entry.getKey());
                });

                System.out.println("Environment variables loaded successfully with dotenv!");
                return;

            } catch (DotenvException e) {
                System.out.println("Dotenv failed, trying manual parsing: " + e.getMessage());
            }

            // Fallback to manual parsing with UTF-8 encoding
            String envPath = "C:\\Users\\Mabel\\Desktop\\Spring-Boot\\Pulse-Check-API\\pulse-check-api\\.env";
            File envFile = new File(envPath);
            
            if (!envFile.exists()) {
                throw new RuntimeException(".env file not found at: " + envPath);
            }

            // Read file with UTF-8 encoding
            String content = new String(Files.readAllBytes(Paths.get(envPath)), StandardCharsets.UTF_8);
            String[] lines = content.split("\n");

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                int equalIndex = line.indexOf('=');
                if (equalIndex > 0) {
                    String key = line.substring(0, equalIndex).trim();
                    String value = line.substring(equalIndex + 1).trim();

                    // Remove quotes if present
                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        value = value.substring(1, value.length() - 1);
                    } else if (value.startsWith("'") && value.endsWith("'")) {
                        value = value.substring(1, value.length() - 1);
                    }

                    System.setProperty(key, value);
                    System.out.println("Loaded environment variable: " + key + " = " + value);
                }
            }

            System.out.println("Environment variables loaded successfully with manual parsing!");

        } catch (Exception e) {
            System.err.println("Error loading .env file: " + e.getMessage());
            System.err.println("Please ensure .env file exists and is properly encoded");
            throw new RuntimeException("Failed to load environment variables", e);
        }
    }
}
