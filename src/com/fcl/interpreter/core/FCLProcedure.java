package com.fcl.interpreter.core;

import java.util.ArrayList;
import java.util.List;

public class FCLProcedure {
	Integer address;
	List<String> parameters;
	List<String> objects;
	
	public FCLProcedure(Integer address) {
		this.address = address;
		parameters = new ArrayList<>();
		objects = new ArrayList<>();
	}
}