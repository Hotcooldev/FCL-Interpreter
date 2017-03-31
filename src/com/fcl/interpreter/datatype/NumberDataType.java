package com.fcl.interpreter.datatype;

public class NumberDataType extends BaseDataType<Number> {
	
	private Double value;
	
	public NumberDataType() {
		this.value = new Double(0);
	}
	
	public NumberDataType(Integer value) {
		this.value = value.doubleValue();
	}
	
	public NumberDataType(Double value) {
		this.value = value;
	}
	
	public void add(NumberDataType other) {
		this.value += other.value;
	}
	
	public void subtract(NumberDataType other) {
		this.value -= other.value;
	}
	
	public void multiply(NumberDataType other) {
		this.value *= other.value;
	}
	
	public void divide(NumberDataType other) {
		this.value /= other.value;
	}
	
	public void mod(NumberDataType other) {
		this.value %= other.value;
	}
	
	public void power(NumberDataType other) {
		this.value = Math.pow(this.value, other.value);
	}
	
	public BooleanDataType greaterThan(NumberDataType other) {
		return new BooleanDataType(this.value > other.value);
	}
	
	public BooleanDataType lessThan(NumberDataType other) {
		return new BooleanDataType(this.value < other.value);
	}
	
	public BooleanDataType greaterThanOrEqual(NumberDataType other) {
		return new BooleanDataType(this.value >= other.value);
	}
	
	public BooleanDataType lessThanOrEqual(NumberDataType other) {
		return new BooleanDataType(this.value <= other.value);
	}
	
	@Override
	public BooleanDataType equal(BaseDataType<?> other) {
		if (!(other instanceof NumberDataType)) {
			return new BooleanDataType(false);
		}
		
		return new BooleanDataType(value.equals(((NumberDataType) other).value));
	}
	
	@Override
	public BooleanDataType notEqual(BaseDataType<?> other) {
		if (!(other instanceof NumberDataType)) {
			return new BooleanDataType(false);
		}
		
		return new BooleanDataType(!value.equals(((NumberDataType) other).value));
	}
	
	@Override
	public void setValue(Number value) {
		this.value = value.doubleValue();
	}

	@Override
	public void setValueFromString(String str) {
		this.value = Double.parseDouble(str);
	}
	
	@Override
	public Number getValue() {
		if(Double.isNaN(value) || Double.isInfinite(value)) {
			return null;
		} else if(value == Math.floor(value) && !Double.isInfinite(value)) {
			return value.intValue();
		} else {
			return value;
		}
	}
	
	@Override
	public String toString() {
		if(Double.isNaN(value) || Double.isInfinite(value)) {
			return "nil";
		} else if(value == Math.floor(value)) {
			return Integer.toString(value.intValue());
		} else {
			return Double.toString(value);
		}
	}
	
	public static BaseDataType<?> fromString(String str) {
		try {
			Double value = Double.parseDouble(str);
			return new NumberDataType(value);
		} catch (Exception e) {
		}
		
		return null;
	}

	@Override
	public BaseDataType<?> clone() {
		return new NumberDataType(this.value);
	}
}