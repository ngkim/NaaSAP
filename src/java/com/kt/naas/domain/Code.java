package com.kt.naas.domain;

public class Code {
	private String codeType;
	private String codeName;
	private String codeValue;
	private int	displayOrder;
	
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getCodeValue() {
		return codeValue;
	}
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Override
	public String toString() {
		return String
				.format("Code [codeType=%s, codeName=%s, codeValue=%s, displayOrder=%s]",
						codeType, codeName, codeValue, displayOrder);
	}
}
