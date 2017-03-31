package com.fcl.interpreter.datatype;

public abstract class BaseDataType<T> {
	
	public enum DataType {
		NUMBER("NUMBER"),
		BOOLEAN("BOOLEAN"),
		STRING("STRING"),
		POSITION("POSITION"),
		OBJECT("OBJECT"),
		LIST("LIST");
		
		String name;
		String regex;
		
		private DataType(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
	}
	
	static BaseDataType<?> create(String str) {
		return null;
	}
	
	public abstract void setValueFromString(String str);
	public abstract void setValue(T value);
	public abstract T getValue();
	
	public abstract BaseDataType<?> clone();
	
	public abstract BooleanDataType equal(BaseDataType<?> other);
	public abstract BooleanDataType notEqual(BaseDataType<?> other);
}