package com.fcl.interpreter.datatype;

import java.util.ArrayList;
import java.util.List;

public class ListDataType extends BaseDataType<List<BaseDataType<?>>> {
	
	private List<BaseDataType<?>> value;
	
	public ListDataType() {
		this.value = new ArrayList<BaseDataType<?>>(0);
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
	public void setValue(List<BaseDataType<?>> value) {
	}

	@Override
	public List<BaseDataType<?>> getValue() {
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
		return new ListDataType();
	}
}