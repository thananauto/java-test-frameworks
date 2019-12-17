package com.test.automation.fp.testdata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.opencsv.CSVReader;

public class CsvDataAccess {
	private char ch = ';';
	private String filePath;
	private String fileName;
	private String filename_Sheet;

	public CsvDataAccess(String filePath, String fileName) {
		this.filePath = filePath;
		this.fileName = fileName;
	}

	public void setFileSheetName(String filename_Sheet) {
		this.filename_Sheet = filename_Sheet;
	}

	private void checkPreRequisites() {
		if (this.fileName == null) {
			// throw new Exception("CSVfile.fileName is not set!");
		}

		if (this.filename_Sheet != null) {
			this.fileName = this.filename_Sheet;
		}
	}

	public int getLastRowNum() {
		checkPreRequisites();

		FileReader workbook = openFileForReading();
		int count = 0;
		String line = "";
		try {
			BufferedReader br = new BufferedReader(workbook);
			while ((line = br.readLine()) != null) {

				count++;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	private FileReader openFileForReading() {
		checkPreRequisites();
		String absoluteFilePath = this.filePath + this.fileName + ".csv";
		FileReader fileInputStream = null;
		try {
			fileInputStream = new FileReader(absoluteFilePath);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			// throw new FrameworkException("The specified file \"" +
			// absoluteFilePath + "\" does not exist!");
		}

		return fileInputStream;
	}

	public String getValue(int rowNum, String columnHeader) {
		checkPreRequisites();

		FileReader workbook = openFileForReading();
		String strCellValue = null;
		try {

			// CSVReader csvReader = new CSVReader(workbook, ch);

			CSVReader csvReader = new CSVReader(workbook, ch);
			List<String[]> content = csvReader.readAll();

			// read the first line always for coloumn index
			int index = 0;
			String[] strFirstLine = content.get(0);
			for (int i = 0; i < strFirstLine.length; i++) {
				if (strFirstLine[i].equals(columnHeader)) {
					index = i;
					break;
				}
			}

			// read the row content
			strCellValue = content.get(rowNum)[index];

			csvReader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strCellValue;
	}

	public int getRowNum(String key, int columnNum, int startRowNum) {
		checkPreRequisites();

		FileReader workbook = openFileForReading();
		try {

			@SuppressWarnings("resource")
			CSVReader csvReader = new CSVReader(workbook, ch);
			List<String[]> content = csvReader.readAll();

			for (int currentRowNum = startRowNum; currentRowNum < getLastRowNum(); currentRowNum++) {

				String strKey = content.get(currentRowNum)[columnNum];
				if (strKey.equals(key))
					return currentRowNum;

			}
			csvReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public int getRowNum(String key, int columnNum) {
		return getRowNum(key, columnNum, 0);
	}

	public String getValue(int rowNum, int columnNum) {
		checkPreRequisites();

		FileReader workbook = openFileForReading();
		// HSSFSheet worksheet = getWorkSheet(workbook);

		String cellValue = getCellValue(workbook, rowNum, columnNum);

		return cellValue;
	}

	private String getCellValue(FileReader workbook, int rowNum, int columnNum) {
		String cellValue = null;
		try {

			CSVReader csvReader = new CSVReader(workbook, ch);
			List<String[]> content = csvReader.readAll();

			cellValue = content.get(rowNum)[columnNum];
			csvReader.close();
		} catch (Exception e) {
			cellValue = "";
		}
		if (cellValue == null)
			cellValue = "";
		return cellValue;
	}

	public int getRowCount(String key, int columnNum, int startRowNum) {
		checkPreRequisites();

		FileReader workbook = openFileForReading();
		// HSSFSheet worksheet = getWorkSheet(workbook);

		int rowCount = 0;
		Boolean keyFound = Boolean.valueOf(false);
		for (int currentRowNum = startRowNum; currentRowNum <= getLastRowNum(); currentRowNum++) {
			String currentValue = getCellValue(workbook, currentRowNum,
					columnNum);
			if (currentValue.equals(key)) {
				rowCount++;
				keyFound = Boolean.valueOf(true);
			} else {
				if (keyFound.booleanValue()) {
					break;
				}
			}
		}
		return rowCount;
	}

	public int getRowCount(String key, int columnNum) {
		return getRowCount(key, columnNum, 0);
	}

}