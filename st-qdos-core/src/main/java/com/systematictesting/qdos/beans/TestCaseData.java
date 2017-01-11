package com.systematictesting.qdos.beans;

import java.util.ArrayList;
import java.util.List;

public class TestCaseData {
	private String testCaseId;
	private String testCaseName;
	private String testCaseMode;
	private List<TestStepData> testStepsData = new ArrayList<TestStepData>();
	public String getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}
	public String getTestCaseName() {
		return testCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	public String getTestCaseMode() {
		return testCaseMode;
	}
	public void setTestCaseMode(String testCaseMode) {
		this.testCaseMode = testCaseMode;
	}
	public List<TestStepData> getTestStepsData() {
		return testStepsData;
	}
	public void setTestStepsData(List<TestStepData> testStepsData) {
		this.testStepsData = testStepsData;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((testCaseId == null) ? 0 : testCaseId.hashCode());
		result = prime * result + ((testCaseMode == null) ? 0 : testCaseMode.hashCode());
		result = prime * result + ((testCaseName == null) ? 0 : testCaseName.hashCode());
		result = prime * result + ((testStepsData == null) ? 0 : testStepsData.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestCaseData other = (TestCaseData) obj;
		if (testCaseId == null) {
			if (other.testCaseId != null)
				return false;
		} else if (!testCaseId.equals(other.testCaseId))
			return false;
		if (testCaseMode == null) {
			if (other.testCaseMode != null)
				return false;
		} else if (!testCaseMode.equals(other.testCaseMode))
			return false;
		if (testCaseName == null) {
			if (other.testCaseName != null)
				return false;
		} else if (!testCaseName.equals(other.testCaseName))
			return false;
		if (testStepsData == null) {
			if (other.testStepsData != null)
				return false;
		} else if (!testStepsData.equals(other.testStepsData))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TestCaseData [testCaseId=" + testCaseId + ", testCaseName=" + testCaseName + ", testCaseMode="
				+ testCaseMode + ", testStepsData=" + testStepsData + "]";
	}
}
