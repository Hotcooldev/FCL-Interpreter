package com.fcl.interpreter.core;

import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import com.fcl.interpreter.datatype.BaseDataType;
import com.fcl.interpreter.datatype.BooleanDataType;
import com.fcl.interpreter.datatype.NilDataType;
import com.fcl.interpreter.datatype.NumberDataType;
import com.fcl.interpreter.datatype.StringDataType;
import com.fcl.interpreter.datatype.converter.DataTypeConverter;
import com.fcl.interpreter.exception.FCLCriticalException;
import com.fcl.interpreter.exception.FCLCriticalException.CriticalException;

class FCLStack extends Stack<BaseDataType<?>> {
	private static final long serialVersionUID = 4581997786306865235L;
	
	private FCLVariables variables = new FCLVariables();
	
	//
	// Memory operations ============================================
	//
	
	public void load(String varName) throws FCLCriticalException {
		BaseDataType<?> dt = variables.get(varName);
		if(dt == null) {
			variables.put(varName, new NilDataType());
		}
		
		push(dt);
	}
	
	public void store(String varName) {
		BaseDataType<?> dt = pop();
		variables.put(varName, dt.clone());
	}
	
	// Push and pop operations are inherited from java.util.Stack
	
	//
	// Arithmetic operations ========================================
	//
	
	public void add() throws FCLCriticalException {
		BaseDataType<?> right = pop();
		BaseDataType<?> left = pop();
		NumberDataType rVal = DataTypeConverter.toNumber(right);
		NumberDataType lVal = DataTypeConverter.toNumber(left);
		
		if(rVal == null) {
			throw new FCLCriticalException(CriticalException.ImplicitConversionFailed,
					right.getClass().getSimpleName(),
					NumberDataType.class.getSimpleName());
		} else if(lVal == null) {
			throw new FCLCriticalException(CriticalException.ImplicitConversionFailed,
					left.getClass().getSimpleName(),
					NumberDataType.class.getSimpleName());
		}
		
		lVal.add(rVal);
		
		push(lVal);
	}
	
	public void sub() throws FCLCriticalException {
		BaseDataType<?> right = pop();
		BaseDataType<?> left = pop();
		NumberDataType rVal = DataTypeConverter.toNumber(right);
		NumberDataType lVal = DataTypeConverter.toNumber(left);
		
		if(rVal == null) {
			throw new FCLCriticalException(CriticalException.ImplicitConversionFailed,
					right.getClass().getSimpleName(),
					NumberDataType.class.getSimpleName());
		} else if(lVal == null) {
			throw new FCLCriticalException(CriticalException.ImplicitConversionFailed,
					left.getClass().getSimpleName(),
					NumberDataType.class.getSimpleName());
		}
		
		lVal.subtract(rVal);
		
		push(lVal);
	}
	
	public void mul() throws FCLCriticalException {
		BaseDataType<?> right = pop();
		BaseDataType<?> left = pop();
		NumberDataType rVal = DataTypeConverter.toNumber(right);
		NumberDataType lVal = DataTypeConverter.toNumber(left);
		
		if(rVal == null) {
			throw new FCLCriticalException(CriticalException.ImplicitConversionFailed,
					right.getClass().getSimpleName(),
					NumberDataType.class.getSimpleName());
		} else if(lVal == null) {
			throw new FCLCriticalException(CriticalException.ImplicitConversionFailed,
					left.getClass().getSimpleName(),
					NumberDataType.class.getSimpleName());
		}
		
		lVal.multiply(rVal);
		
		push(lVal);
	}
	
	public void div() throws FCLCriticalException {
		BaseDataType<?> right = pop();
		BaseDataType<?> left = pop();
		NumberDataType rVal = DataTypeConverter.toNumber(right);
		NumberDataType lVal = DataTypeConverter.toNumber(left);
		
		if(rVal == null) {
			throw new FCLCriticalException(CriticalException.ImplicitConversionFailed,
					right.getClass().getSimpleName(),
					NumberDataType.class.getSimpleName());
		} else if(lVal == null) {
			throw new FCLCriticalException(CriticalException.ImplicitConversionFailed,
					left.getClass().getSimpleName(),
					NumberDataType.class.getSimpleName());
		}
		
		lVal.divide(rVal);
		
		push(lVal);
	}
	
	public void mod() throws FCLCriticalException {
		BaseDataType<?> right = pop();
		BaseDataType<?> left = pop();
		NumberDataType rVal = DataTypeConverter.toNumber(right);
		NumberDataType lVal = DataTypeConverter.toNumber(left);
		
		if(rVal == null) {
			throw new FCLCriticalException(CriticalException.ImplicitConversionFailed,
					right.getClass().getSimpleName(),
					NumberDataType.class.getSimpleName());
		} else if(lVal == null) {
			throw new FCLCriticalException(CriticalException.ImplicitConversionFailed,
					left.getClass().getSimpleName(),
					NumberDataType.class.getSimpleName());
		}
		
		lVal.mod(rVal);
		
		push(lVal);
	}
	
	public void pow() throws FCLCriticalException {
		BaseDataType<?> right = pop();
		BaseDataType<?> left = pop();
		NumberDataType rVal = DataTypeConverter.toNumber(right);
		NumberDataType lVal = DataTypeConverter.toNumber(left);
		
		if(rVal == null) {
			throw new FCLCriticalException(CriticalException.ImplicitConversionFailed,
					right.getClass().getSimpleName(),
					NumberDataType.class.getSimpleName());
		} else if(lVal == null) {
			throw new FCLCriticalException(CriticalException.ImplicitConversionFailed,
					left.getClass().getSimpleName(),
					NumberDataType.class.getSimpleName());
		}
		
		lVal.power(rVal);
		
		push(lVal);
	}
	
	//
	// Concatenation operations =====================================
	//
	
	public void concat() {
		BaseDataType<?> right = pop();
		BaseDataType<?> left = pop();
		
		StringDataType rVal = DataTypeConverter.toString(right);
		StringDataType lVal = DataTypeConverter.toString(left);
		
		lVal.concat(rVal);
		
		push(lVal);
	}
	
	//
	// Logical operations ===========================================
	//
	
	public void not() throws FCLCriticalException {
		BaseDataType<?> dt = pop();
		BooleanDataType val = DataTypeConverter.toBoolean(dt);
		
		if(val == null) {
			throw new FCLCriticalException(CriticalException.LogicalNotInvalidType, dt.getClass().getSimpleName());
		}
		
		val.setValue(!val.getValue());
		
		push(val);
	}
	
	public void compare(String operator) throws FCLCriticalException {
		BaseDataType<?> right = pop();
		BaseDataType<?> left = pop();
		BooleanDataType result = null;
		
		switch(operator) {
		case ">":
			if(left instanceof NumberDataType && right instanceof NumberDataType) {
				NumberDataType rVal = (NumberDataType) right;
				NumberDataType lVal = (NumberDataType) left;
				result = lVal.greaterThan(rVal);
			} else {
				throw new FCLCriticalException(CriticalException.LogicalComparisonInvalidType);
			}
			break;
		case "<":
			if(left instanceof NumberDataType && right instanceof NumberDataType) {
				NumberDataType rVal = (NumberDataType) right;
				NumberDataType lVal = (NumberDataType) left;
				result = lVal.lessThan(rVal);
			} else {
				throw new FCLCriticalException(CriticalException.LogicalComparisonInvalidType);
			}
			break;
		case ">=":
			if(left instanceof NumberDataType && right instanceof NumberDataType) {
				NumberDataType rVal = (NumberDataType) right;
				NumberDataType lVal = (NumberDataType) left;
				result = lVal.greaterThanOrEqual(rVal);
			} else {
				throw new FCLCriticalException(CriticalException.LogicalComparisonInvalidType);
			}
			break;
		case "<=":
			if(left instanceof NumberDataType && right instanceof NumberDataType) {
				NumberDataType rVal = (NumberDataType) right;
				NumberDataType lVal = (NumberDataType) left;
				result = lVal.lessThanOrEqual(rVal);
			} else {
				throw new FCLCriticalException(CriticalException.LogicalComparisonInvalidType);
			}
			break;
		case "==":
			result = left.equal(right);
			break;
		case "!=":
			result = left.notEqual(right);
			break;
		}
		
		push(result);
	}
	
	public void print() {
		if(!isEmpty()) {
			System.out.println(peek().toString());
		} else {
			System.out.println("Stack is empty.");
		}
	}
	
	public void dump() {
		if(!isEmpty()) {
			System.out.println("DUMP =========================");
			for(int i = 0; i < size(); i++) {
				System.out.println(get(i));
			}
			System.out.println("END ==========================");
		}
	}
	
	public void dumpVariables() {
		if(!variables.isEmpty()) {
			System.out.println("VARIABLES ====================");
			for (Map.Entry<String, BaseDataType<?>> entry : variables.entrySet()) {
			    System.out.println(entry.getKey() + " = " + entry.getValue().getValue());
			}
			System.out.println("END ==========================");
		}
	}
	
	public static final void main(String[] args) {
		BaseDataType<?> right = new StringDataType("5");
		BaseDataType<?> left = new StringDataType("3");
		FCLStack stack = new FCLStack();
		stack.push(left);
		stack.print();
		stack.push(right);
		stack.print();
		
		try {
			stack.mul();
			stack.print();
			stack.pop();
			stack.print();
		} catch (FCLCriticalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}