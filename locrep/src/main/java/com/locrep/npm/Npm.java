package com.locrep.npm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.locrep.configuration.*;
@SpringBootApplication
@EnableConfigurationProperties(value=LocrepConfiguration.class)
public class Npm {

	public static void main(String[] args) {
		SpringApplication.run(Npm.class, args);

	}

}
