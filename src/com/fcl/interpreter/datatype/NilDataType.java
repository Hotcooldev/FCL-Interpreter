package com.fcl.interpreter.datatype;

public class NilDataType extends BaseDataType<String> {

	private final String value = "nil";
	
	public NilDataType() {
	}
	
	@Override
	public BooleanDataType equal(BaseDataType<?> other) {
		return new BooleanDataType(value.equals(other));
	}
	
	@Override
	public BooleanDataType notEqual(BaseDataType<?> other) {
		return new BooleanDataType(!value.equals(other));
	}
	
	@Override
	public void setValueFromString(String str) {
	}

	@Override
	public void setValue(String value) {
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public static BaseDataType<?> fromString(String str) {
		if(str.equals("nil")) {
			return new NilDataType();
		}
		
		return null;
	}

	@Override
	public BaseDataType<?> clone() {
		return new NilDataType();
	}
}