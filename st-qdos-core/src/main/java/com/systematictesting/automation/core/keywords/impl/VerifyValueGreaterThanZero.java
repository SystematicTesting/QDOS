/*
 * Copyright (c) Mar 16, 2017 Systematic Testing Ltd. (www.systematictesting.com) to Present..
 * All rights reserved. 
 */
package com.systematictesting.automation.core.keywords.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.systematictesting.automation.core.constants.ElementType;
import com.systematictesting.automation.core.constants.Result;
import com.systematictesting.automation.core.framework.Browser;
import com.systematictesting.automation.core.keywords.Process;
import com.systematictesting.qdos.beans.TestStepData;

public class VerifyValueGreaterThanZero implements Process {

	@Override
	public String execute(Map<String, String> elementKeyValuePairs, TestStepData testStep) {
		if (StringUtils.isBlank(elementKeyValuePairs.get(testStep.getElementKey()))){
			return Result.ABORTED + " : ELEMENT_KEY not found.";
		}

		String actual = null;
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
		} catch (Exception e){
			return Result.FAIL + " - Exception occured while locating an element.";
		}
		if (element !=null){
			actual = element.getText();
		} else {
			return Result.FAIL + " - Web element doesn't exist on page.";
		}
		actual = actual.replaceAll("[^0-9]", "");
		try {
			long actualDataInNumber = Long.parseLong(actual);
			if (actualDataInNumber>0){
				return Result.PASS;
			} else {
				return Result.FAIL;
			}
		} catch (Exception e){
			return Result.FAIL + " - Actual data from Site is not a number.";
		}
	}

}
