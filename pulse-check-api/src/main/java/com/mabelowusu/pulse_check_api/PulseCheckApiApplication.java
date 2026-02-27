package com.mabelowusu.pulse_check_api;

import com.mabelowusu.pulse_check_api.config.DotenvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
public class PulseCheckApiApplication {

	public static void main(String[] args) {
		DotenvConfig.loadEnvironmentVariables();
		
		SpringApplication.run(PulseCheckApiApplication.class, args);
	}


}
