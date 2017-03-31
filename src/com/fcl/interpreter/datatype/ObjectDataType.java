package com.fcl.interpreter.datatype;

public class ObjectDataType extends BaseDataType<String> {
	
	private String value;
	
	public ObjectDataType() {
		this.value = new String();
	}
	
	@Override
	public BooleanDataType equal(BaseDataType<?> other) {
		return null;
	}
	
	@Override
	public BooleanDataType notEqual(BaseDataType<?> other) {
		return null;
	}

	@Override
	public void setValueFromString(String str) {
	}

	@Override
	public void setValue(String value) {
	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public String toString() {
		return null;
	}
	
	public static BaseDataType<?> fromString(String str) {
		return null;
	}

	@Override
	public BaseDataType<?> clone() {
		return new ObjectDataType();
	}
}