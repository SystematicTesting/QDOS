package com.systematictesting.automation.core.constants;

public enum ElementType {
	XPATH("XPATH"), CSS_SELECTOR("CSS-SELECTOR"), ID("ID");

	private final String type;

	private ElementType(String param) {
		type = param;
	}

	public boolean equals(String paramType) {
		return (paramType == null) ? false : type.equals(paramType);
	}

	public String toString() {
		return this.type;
	}
}
