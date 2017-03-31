package com.fcl.interpreter.exception;

import com.fcl.interpreter.datatype.BaseDataType;

public class FCLCriticalException extends Exception {
	private static final long serialVersionUID = -5933117746717152901L;

	public enum CriticalException {
		ImplicitConversionFailed("Could not perform implicit conversion from type %s to type %s."),
		LogicalNotInvalidType("Attempted to apply a logical NOT over a value of type %s."),
		LogicalComparisonInvalidType("Attempted to perform an invalid comparison operation over a non-integer value."),
		WrongNumberOfParameters("The instruction has missing parameters."),
		CouldNotConvertDataType("Unable to convert the string %s to a valid data type."),
		CouldNotConverBoolean("Unable to convert the type %s to a BooleanDataType."),
		LabelUnexistent("The label %s does not exist.");
		
		private String message;
		
		CriticalException(String message) {
			this.message = message;
		}
		
		public String getMessage() {
			return message;
		}
	}
	
	public FCLCriticalException(CriticalException exception) {
		super(exception.getMessage());
	}
	
	public FCLCriticalException(CriticalException exception, String... strings) {
		super(String.format(exception.getMessage(), (Object[]) strings));
	}
	
	public FCLCriticalException(CriticalException exception, BaseDataType<?>... dataTypes) {
		super(String.format(exception.getMessage(), (Object[]) dataTypes));
	}
}