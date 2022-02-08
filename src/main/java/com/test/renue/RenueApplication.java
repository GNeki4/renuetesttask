package com.test.renue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RenueApplication {

	public static void main(String[] args) {
		SpringApplication.run(RenueApplication.class, args);

		Runtime runtime = Runtime.getRuntime();

		long totalMemory = runtime.totalMemory();
		long freeMemory = runtime.freeMemory();
		double usedMemory = (double)(totalMemory - freeMemory) / (double)(1024 * 1024);

		System.out.println("Used memory: " + usedMemory + " mb's.");
	}
}

