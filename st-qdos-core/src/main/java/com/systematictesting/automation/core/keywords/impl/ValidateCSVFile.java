package com.systematictesting.automation.core.keywords.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.systematictesting.automation.core.constants.ElementType;
import com.systematictesting.automation.core.constants.FrameworkParams;
import com.systematictesting.automation.core.constants.Result;
import com.systematictesting.automation.core.constants.SystemParams;
import com.systematictesting.automation.core.framework.Browser;
import com.systematictesting.automation.core.keywords.Process;
import com.systematictesting.automation.core.utils.CommandLineParamsUtils;
import com.systematictesting.qdos.beans.TestStepData;

public class ValidateCSVFile implements Process {

	@Override
	public String execute(Map<String, String> elementKeyValuePairs, TestStepData testStep) {
		if (StringUtils.isBlank(elementKeyValuePairs.get(testStep.getElementKey()))){
			return Result.ABORTED + " : ELEMENT_KEY not found.";
		}
		if (StringUtils.isBlank(testStep.getElementValue())){
			return Result.ABORTED + " : ELEMENT_DATA not found.";
		}
		
		WebElement element = null;
		
		try {
			if (testStep.getElementType().equals(ElementType.XPATH)) {
				element = Browser.getInstance().getDriver().findElement(By.xpath(elementKeyValuePairs.get(testStep.getElementKey())));
			}
			if (testStep.getElementType().equals(ElementType.ID)) {
				element = Browser.getInstance().getDriver().findElement(By.id(elementKeyValuePairs.get(testStep.getElementKey())));
			}
			if (testStep.getElementType().equals(ElementType.CSS_SELECTOR)) {
				element = Browser.getInstance().getDriver().findElement(By.cssSelector(elementKeyValuePairs.get(testStep.getElementKey())));
			}
			if (element!=null){
				
				String csvFileName = element.getText();
				String fileLocation = SystemParams.DOWNLOAD_FILE_LOCATION + File.separator;
				if (CommandLineParamsUtils.getInstance().getOperatingSystem().equals(FrameworkParams.OS_WINDOWS_7) || CommandLineParamsUtils.getInstance().getOperatingSystem().equals(FrameworkParams.OS_WINDOWS_10)){
					fileLocation = fileLocation + "\"" +csvFileName + "\"";
				} else {
					fileLocation = fileLocation + csvFileName;
				}
				File csvFile = new File(fileLocation);
				if (csvFile.exists()){
					JsonElement jelement = new JsonParser().parse(testStep.getElementValue());
				    JsonArray  jsonDataValidationRules = jelement.getAsJsonArray();
				    Map<Integer,List<String>> csvContentInMap = new HashMap<Integer, List<String>>();
				    populateCSVFileContent(csvContentInMap, fileLocation);
				    
				    for(int index=0;index<jsonDataValidationRules.size();index++){
				    	JsonObject jsonSingleRule = jsonDataValidationRules.get(index).getAsJsonObject();
				    	int row = jsonSingleRule.get("row").getAsInt();
				    	int column = jsonSingleRule.get("column").getAsInt();
				    	String expectedData = jsonSingleRule.get("data").getAsString();
				    	
				    	boolean isDataMatching = true;
				    	isDataMatching = verifyExpectedDataWithActualData(csvContentInMap, row, column, expectedData.trim());
				    	if (!isDataMatching){
				    		return Result.FAIL + " - Data doesn't match with CSV File. Please check logs.";
				    	}
				    }
					
				} else {
					return Result.FAIL+" - CSV File does not exists on download location : "+fileLocation;
				}
			}
			
		} catch (Throwable t) {
			return Result.FAIL+" - " + t.getMessage();
		}

		return Result.PASS;
	}

	private void populateCSVFileContent(Map<Integer,List<String>> csvContentInMap, String csvFileNameWithPath) {
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFileNameWithPath))) {
        	int index=0;
            while ((line = br.readLine()) != null) {
                String[] dataInRow = line.split(cvsSplitBy);
                List<String> dataInRowList = new ArrayList<String>(Arrays.asList(dataInRow));
                csvContentInMap.put(index, dataInRowList);
                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	private boolean verifyExpectedDataWithActualData(Map<Integer, List<String>> csvContentInMap, int row, int column,
			String expectedData) {
		if (csvContentInMap.get(row) != null){
			List<String> listOfColumnsData = csvContentInMap.get(row);
			if (column<listOfColumnsData.size()){
				String actualData = listOfColumnsData.get(column).trim();
				String  actualDataToCompare = actualData.replaceAll("[^\\w\\s]","");
				String expectedDataToCompare = expectedData.replaceAll("[^\\w\\s]","");
				if (actualDataToCompare.equals(expectedDataToCompare)){
					return true;
				} else {
					System.out.println("ValidateCSVFile :: DATA NOT MATCHING :: Expected data : "+expectedData+" |VS| Actual data : "+actualData);
					return false;
				}
			} else {
				System.out.println("Expected column doesn't exists.");
				return false;
			}
		} else {
			System.out.println("Expected row doesn't exists.");
			return false;
		}
	}
	
	public static void main(String[] args) {
		String csvFileNameWithPath = "/Users/sharadkumar/Downloads/Rule-Violation-Report-20171112-221926.csv";
		Map<Integer,List<String>> csvContentInMap = new HashMap<Integer, List<String>>();
		ValidateCSVFile objValidateCSVFile = new ValidateCSVFile();
		objValidateCSVFile.populateCSVFileContent(csvContentInMap, csvFileNameWithPath);
		System.out.println("DATA : "+csvContentInMap);
	}

}

/*
 * Sample data validation rules which will go inside the Excel Sheet are:
 * 
[
    {
      "row": 0,
      "column": 0,
      "data" : "some value"
    },
    {
      "row": 0,
      "column": 1,
      "data" : "some value"
    },
    {
      "row": 0,
      "column": 2,
      "data" : "some value"
    },
    {
      "row": 0,
      "column": 3,
      "data" : "some value"
    }
]
 */

