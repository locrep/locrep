package com.locrep.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="locrep")
public class LocrepConfiguration {
	private short verbose=0;

	public short getVerbose() {
		return verbose;
	}

	public void setVerbose(short verbose) {
		this.verbose = verbose;
	}
	
}
