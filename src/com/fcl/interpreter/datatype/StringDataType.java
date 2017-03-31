package com.fcl.interpreter.datatype;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringDataType extends BaseDataType<String> {
	
	private String value;
	
	public StringDataType() {
		this.value = new String();
	}
	
	public StringDataType(String value) {
		this.value = value;
	}
	
	public void concat(BaseDataType<?> other) {
		if(other != null) {
			value += other.toString();
		}
	}
	
	@Override
	public BooleanDataType equal(BaseDataType<?> other) {
		if (!(other instanceof StringDataType)) {
			return new BooleanDataType(false);
		}
		
		return new BooleanDataType(value.equals(((StringDataType)other).value));
	}
	
	@Override
	public BooleanDataType notEqual(BaseDataType<?> other) {
		if (!(other instanceof StringDataType)) {
			return new BooleanDataType(false);
		}
		
		return new BooleanDataType(!value.equals(((StringDataType)other).value));
	}
	
	@Override
	public void setValueFromString(String str) {
	}

	@Override
	public void setValue(String value) {
		this.value = value;
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
		String regex = "\"(.+)\"";
		Pattern pattern = Pattern.compile(regex);
		
		Matcher m = pattern.matcher(str);
		if(m.find()) {
			return new StringDataType(m.group(1));
		}
		
		return null;
	}

	@Override
	public BaseDataType<?> clone() {
		return new StringDataType(this.value);
	}
}