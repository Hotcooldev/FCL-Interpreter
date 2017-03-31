package com.fcl.interpreter.datatype.converter;

import com.fcl.interpreter.datatype.BaseDataType;
import com.fcl.interpreter.datatype.BooleanDataType;
import com.fcl.interpreter.datatype.ListDataType;
import com.fcl.interpreter.datatype.NilDataType;
import com.fcl.interpreter.datatype.NumberDataType;
import com.fcl.interpreter.datatype.ObjectDataType;
import com.fcl.interpreter.datatype.PositionDataType;
import com.fcl.interpreter.datatype.StringDataType;

public class DataTypeConverter {
	
	public static NumberDataType toNumber(BaseDataType<?> dt) {
		if (dt instanceof NumberDataType) {
			return (NumberDataType) dt;
		} else if (dt instanceof StringDataType) {
			String str = ((StringDataType) dt).toString();
			
			try {
				Double value = Double.parseDouble(str);
				return new NumberDataType(value);
			} catch (Exception e) {
			}
		}
		
		return null;
	}
	
	public static StringDataType toString(BaseDataType<?> dt) {
		return new StringDataType(dt.toString());
	}
	
	public static BooleanDataType toBoolean(BaseDataType<?> dt) {
		if (dt instanceof BooleanDataType) {
			return (BooleanDataType) dt;
		} else if (dt instanceof NumberDataType) {
			Double value = (Double)((NumberDataType) dt).getValue();
			return new BooleanDataType(value != 0);
		} else if (dt instanceof StringDataType) {
			String value = ((StringDataType) dt).getValue();
			return new BooleanDataType(Boolean.parseBoolean(value));
		} else if (dt instanceof NilDataType) {
			return new BooleanDataType(false);
		}
		
		return null;
	}
	
	public static BaseDataType<?> stringToDataType(String str) {
		BaseDataType<?> dt = null;
		
		if ((dt = NilDataType.fromString(str)) != null) {
		} else if ((dt = BooleanDataType.fromString(str)) != null) {
		} else if ((dt = NumberDataType.fromString(str)) != null) {
		} else if ((dt = StringDataType.fromString(str)) != null) {
		} else if ((dt = ObjectDataType.fromString(str)) != null) {
		} else if ((dt = PositionDataType.fromString(str)) != null) {
		} else if ((dt = ListDataType.fromString(str)) != null) {
		}
		
		return dt;
	}
}