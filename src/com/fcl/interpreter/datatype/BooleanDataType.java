package com.fcl.interpreter.datatype;

public class BooleanDataType extends BaseDataType<Boolean> {
	
	private Boolean value;
	
	public BooleanDataType() {
		this.value = new Boolean(false);
	}
	
	public BooleanDataType(Boolean value) {
		this.value = value;
	}
	
	@Override
	public BooleanDataType equal(BaseDataType<?> other) {
		if (!(other instanceof BooleanDataType)) {
			return new BooleanDataType(false);
		}
		
		return new BooleanDataType(value.equals(((BooleanDataType) other).value));
	}
	
	@Override
	public BooleanDataType notEqual(BaseDataType<?> other) {
		if (!(other instanceof BooleanDataType)) {
			return new BooleanDataType(false);
		}
		
		return new BooleanDataType(!value.equals(((BooleanDataType) other).value));
	}

	@Override
	public void setValueFromString(String str) {
	}

	@Override
	public void setValue(Boolean value) {
		this.value = value;
	}

	@Override
	public Boolean getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	public static BaseDataType<?> fromString(String str) {
		if(str.equals("true")) {
			return new BooleanDataType(true);
		} else if(str.equals("false")) {
			return new BooleanDataType(false);
		}
		
		return null;
	}

	@Override
	public BaseDataType<?> clone() {
		return new BooleanDataType(this.value);
	}
}