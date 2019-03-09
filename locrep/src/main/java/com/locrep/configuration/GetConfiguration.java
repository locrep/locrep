package com.locrep.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetConfiguration {
	@Autowired
	private LocrepConfiguration locrepConfiguration;
	
	@PostConstruct
	public void init() {
		// postconstruct anotasyonuna sahip bir lifecycle call back metodu tanımlıyoruz
		// bu spring tarafından otomatik invoke edilecek
		System.out.println("Locrep verbose configuration is = "+ locrepConfiguration.getVerbose());	
	}

}
