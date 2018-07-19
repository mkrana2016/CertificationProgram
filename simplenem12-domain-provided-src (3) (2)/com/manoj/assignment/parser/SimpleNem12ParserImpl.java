package com.manoj.assignment.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.manoj.assignment.model.EnergyUnit;
import com.manoj.assignment.model.MeterRead;
import com.manoj.assignment.model.MeterVolume;
import com.manoj.assignment.model.Quality;

public class SimpleNem12ParserImpl implements SimpleNem12Parser {

	private boolean firstRecordParsed = false;
	private boolean lastRecordParsed = false;

	@Override
	public Collection<MeterRead> parseSimpleNem12(File simpleNem12File) {
		List<MeterRead> meterReads = new ArrayList<>();

		// Create reader for provided file
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(simpleNem12File));
		} catch (FileNotFoundException e) {
			System.err.println("File not found. Terminating execution");
			System.err.println("File not found. Terminating execution");
		}

		try {
			String data;
			MeterRead meterRead = null;

			// Read the file line by line
			while ((data = reader.readLine()) != null) {

				// Split comma separated data into an array
				String[] recordLine = data.split(",");

				if (recordLine == null || recordLine.length == 0) {
					System.err.println("Empty record found. Terminating execution");
					return null;
				}

				String recordType = recordLine[0];
				switch (recordType) {

				// Record type 100 must be the first line in file
				case "100":
					if (!firstRecordParsed) {
						firstRecordParsed = true;
					} else {
						System.err.println("Record type 100 must be the first line in file");
						System.err.println("Terminating execution");
						return null;
					}
					break;

				// Record type 100 must be the first line in file
				case "900":
					if (!firstRecordParsed) {
						System.err.println("Record type 100 must be the first line in file");
						System.err.println("Terminating execution");
						return null;
					}

					if (!lastRecordParsed) {
						lastRecordParsed = true;
					}
					break;
				case "200":
					// Validate record line
					if (!verifyDataRecord("200", 3, recordLine)) {
						return null;
					}

					// Extract data
					try {
						EnergyUnit energyUnit = EnergyUnit.valueOf(recordLine[2]);
						meterRead = new MeterRead(recordLine[1], energyUnit);
						meterReads.add(meterRead);
					} catch (IllegalArgumentException e) {
						System.err.println("Invalid Energy unit found in 200 record: " + Arrays.toString(recordLine));
						System.err.println("Terminating execution");
						return null;
					}
					break;
				case "300":
					// Validate record
					if (!verifyDataRecord("300", 4, recordLine)) {
						return null;
					}

					// A 300 record should come after a 200 record
					if (meterRead == null) {
						System.err.println("Invalid 300 record. Corresponding 200 record not found: "
								+ Arrays.toString(recordLine));
						System.err.println("Terminating execution");
						return null;
					}

					// Extract data
					try {
						LocalDate date = LocalDate.parse(recordLine[1], DateTimeFormatter.BASIC_ISO_DATE);
						Quality quality = Quality.valueOf(recordLine[3]);
						BigDecimal volume = new BigDecimal(recordLine[2]);

						MeterVolume meterVolume = new MeterVolume(volume, quality);
						meterRead.appendVolume(date, meterVolume);
					} catch (NumberFormatException e) {
						System.err.println("Invalid volume in 300 record: " + Arrays.toString(recordLine));
						System.err.println("Terminating execution");
						return null;
					} catch (DateTimeParseException e) {
						System.err.println("Invalid date format in 300 record: " + Arrays.toString(recordLine));
						System.err.println("Terminating execution");
						return null;
					} catch (IllegalArgumentException e) {
						System.err.println("Invalid Quality value found in 300 record: " + Arrays.toString(recordLine));
						System.err.println("Terminating execution");
						return null;
					}
					break;
				default:
					System.err.println("Invalid record type found in line: " + Arrays.toString(recordLine));
					System.err.println("Terminating execution");
					return null;
				}
			}
		} catch (IOException e) {
			System.err.println("Could not read from file. Terminating execution");
			return null;
		}

		// Confirm that 900 record is parsed.
		if (!lastRecordParsed) {
			System.err.println("Invalid file. 900 record not found");
			System.err.println("Terminating execution");
			return null;
		}

		return meterReads;
	}

	/**
	 * Verifies that record type 100 is parsed, record type 900 is not yet
	 * encountered, and record line contains all required data.
	 * 
	 * @param recordType
	 *            String record type
	 * @param expectedLength
	 *            int record line length
	 * @param recordLine
	 *            record line
	 * @return true if record is valid, false otherwise.
	 */
	private boolean verifyDataRecord(String recordType, int expectedLength, String[] recordLine) {
		if (!firstRecordParsed) {
			System.err.println("Record type 100 must be the first line in file");
			System.err.println("Terminating execution");
			return false;
		}

		if (lastRecordParsed) {
			System.err.println("record type 900 must be the last line in file");
			System.err.println("Terminating execution");
			return false;
		}

		if (recordLine.length != expectedLength) {
			System.err.println("Invalid " + recordType + " record found: " + Arrays.toString(recordLine));
			System.err.println("Terminating execution");
			return false;
		}

		return true;

	}
}
