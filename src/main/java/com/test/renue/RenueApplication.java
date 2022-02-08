package com.test.renue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.TreeMap;
import java.util.stream.Stream;

@SpringBootApplication
public class RenueApplication {

	public static void main(String[] args) {
		SpringApplication.run(RenueApplication.class, args);

		var column = readColumn();
		Date currentTime = new Date(System.currentTimeMillis());


		TreeMap<String, AirportInfo> allData = readAll(column);

		for (String row: allData.keySet()) {
			System.out.println(row);
			printMemory();
		}

		//ArrayList<String> list = readAll3(column);
		printMemory();



		Date newTime = new Date(System.currentTimeMillis());
		System.out.println("Preparations took: " + (newTime.getTime() - currentTime.getTime()) + " m'scs.");

	}

	public static void printMemory() {
		Runtime runtime = Runtime.getRuntime();

		var totalMemory = runtime.totalMemory();
		var freeMemory = runtime.freeMemory();
		var usedMemory = (double)(totalMemory - freeMemory) / (double)(1024 * 1024);

		System.out.println("Used memory: " + usedMemory + " mb's.");
	}

	public static TreeMap<String, AirportInfo> readAll2(Integer column) {
		TreeMap<String, AirportInfo> airportMap = new TreeMap<>();

		try(BufferedReader br = new BufferedReader(new FileReader("airports.dat"))) {
			for(String line; (line = br.readLine()) != null; ) {
				var split = line.split(",");

				airportMap.put(split[column], new AirportInfo(
						split[0],
						split[1],
						split[2],
						split[3],
						split[4],
						split[5],
						split[6],
						split[7],
						split[8],
						split[9],
						split[10],
						split[11],
						split[12]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return airportMap;
	}

	public static TreeMap<String, AirportInfo> readAll(Integer column) {
		TreeMap<String, AirportInfo> airportMap = new TreeMap<>();

		try (Stream<String> stream = Files.lines(Paths.get("airports.dat"))) {
			stream.forEach(line -> {
				var split = line.split(",");

				airportMap.put(split[column], new AirportInfo(
						split[0],
						split[1],
						split[2],
						split[3],
						split[4],
						split[5],
						split[6],
						split[7],
						split[8],
						split[9],
						split[10],
						split[11],
						split[12]));
			});

		} catch (IOException e) {
			e.printStackTrace();
		}

		return airportMap;
	}

	public static Integer readColumn() {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		String column = "1";
		try {
			System.out.println("Input column: ");
			column = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		reader = null;
		return Integer.parseInt(column);
	}

	public static String readRow() {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		String row = "a";
		try {
			System.out.println("Введите строку для поиска: ");
			row = reader.readLine();
			System.out.println("Строка: " + row);
		} catch (IOException e) {
			e.printStackTrace();
		}

		reader = null;
		return row;
	}
}

