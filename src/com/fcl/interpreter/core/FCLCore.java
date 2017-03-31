package com.fcl.interpreter.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fcl.interpreter.datatype.BaseDataType;
import com.fcl.interpreter.datatype.BooleanDataType;
import com.fcl.interpreter.datatype.converter.DataTypeConverter;
import com.fcl.interpreter.exception.FCLCriticalException;
import com.fcl.interpreter.exception.FCLCriticalException.CriticalException;

public class FCLCore {
	
	/**
	 * Runs a FCL p-code file.
	 * @param filePath
	 * @return
	 */
	public Integer run(String filePath) {
		List<String> instructions = new ArrayList<>();
		
		File file = new File(filePath);
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				instructions.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(reader != null) {
					reader.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return run(instructions);
	}
	
	public Map<String, Integer> getLabels(List<String> instructions) throws FCLCriticalException {
		Map<String, Integer> labels = new HashMap<>();
		
		for (int i = 0; i < instructions.size(); ++i) {
			String instruction = instructions.get(i);
			String[] tokens = instruction.split("\\s+", 2);
			if(tokens[0].equals("LABEL")) {
				if(tokens.length < 2) {
					throw new FCLCriticalException(CriticalException.WrongNumberOfParameters);
				}
				
				labels.put(tokens[1], i);
			}
		}
		
		return labels;
	}
	
	public Map<String, Integer> getProcedures(List<String> instructions) throws FCLCriticalException {
		Map<String, Integer> procedures = new HashMap<>();
		
		for (int i = 0; i < instructions.size(); ++i) {
			String instruction = instructions.get(i);
			String[] tokens = instruction.split("\\s+");
			if(tokens[0].equals("PROCEDURE")) {
				if(tokens.length < 2) {
					throw new FCLCriticalException(CriticalException.WrongNumberOfParameters);
				}
				
				procedures.put(tokens[1], i);
			}
		}
		
		return procedures;
	}
	
	/**
	 * Executes a given set of instructions.
	 * @param instructions A list of instructions to be executed.
	 * @return
	 */
	public Integer run(List<String> instructions) {
		Map<String, Integer> labels;
		try {
			labels = getLabels(instructions);
		} catch (FCLCriticalException e) {
			e.printStackTrace();
			return -1;
		}
		
		// Initialize with an empty stack
		FCLStack stack = new FCLStack();
		
		for(int i = 0; i < instructions.size(); ++i) {
			String instruction = instructions.get(i);
			String[] tokens = instruction.split("\\s+");
			
			BaseDataType<?> top = null;
			if(!stack.isEmpty()) {
				top = stack.peek();
			}
			
//			if(true) {
//				stack.dump();
//				stack.dumpVariables();
//				System.out.println("");
//				System.out.println("Instruction " + i + " : " + instruction);
//			}
			
			try {
				switch (tokens[0]) {
				
				// Stack and memory instructions
				case "LOAD":
					if (tokens.length < 2) {
						throw new FCLCriticalException(CriticalException.WrongNumberOfParameters);
					}
					stack.load(tokens[1]);
					break;
					
				case "STORE":
					if (tokens.length < 2) {
						throw new FCLCriticalException(CriticalException.WrongNumberOfParameters);
					}
					stack.store(tokens[1]);
					break;
					
				case "PUSH":
					String [] arr = instruction.split("\\s+", 2);
					
					if(arr.length < 2) {
						throw new FCLCriticalException(CriticalException.WrongNumberOfParameters);
					}
					
					BaseDataType<?> dt = DataTypeConverter.stringToDataType(arr[1]);
					if (dt == null) {
						throw new FCLCriticalException(CriticalException.CouldNotConvertDataType, arr[1]);
					}
					
					stack.push(dt);
					
					break;
					
				case "POP":
					stack.pop();
					
					break;
					
				// Arithmetic instructions
				case "ADD":
					stack.add();
					break;
					
				case "SUB":
					stack.sub();
					break;
					
				case "MUL":
					stack.mul();
					break;
					
				case "DIV":
					stack.div();
					break;
					
				case "MOD":
					stack.mod();
					break;
					
				case "POW":
					stack.pow();
					break;
					
				// Concatenation instructions
				case "CONCAT":
					stack.concat();
					break;
				
				// Logical instructions
				case "NOT":
					stack.not();
					break;
					
				case "COMPARE":
					if(tokens.length < 2) {
						throw new FCLCriticalException(CriticalException.WrongNumberOfParameters);
					}
					stack.compare(tokens[1]);
					break;
					
				// Control instructions
				case "JMPTRUE":
					if(tokens.length < 2) {
						throw new FCLCriticalException(CriticalException.WrongNumberOfParameters);
					}
					
					BooleanDataType jmpTrue = DataTypeConverter.toBoolean(top);
					
					if(jmpTrue == null) {
						throw new FCLCriticalException(CriticalException.CouldNotConverBoolean, 
								top.getClass().getSimpleName());
					}
					
					if(jmpTrue.getValue().booleanValue() == true) {
						Integer addr = labels.get(tokens[1]);
						if(addr == null) {
							throw new FCLCriticalException(CriticalException.LabelUnexistent,
									tokens[1]);
						}
						
						i = addr;
					}
					
					stack.pop();
					break;
					
				case "JMPFALSE":
					if(tokens.length < 2) {
						throw new FCLCriticalException(CriticalException.WrongNumberOfParameters);
					}
					
					BooleanDataType jmpFalse = DataTypeConverter.toBoolean(top);
					
					if(jmpFalse == null) {
						throw new FCLCriticalException(CriticalException.CouldNotConverBoolean, 
								top.getClass().getSimpleName());
					}
					
					if(jmpFalse.getValue().booleanValue() == false) {
						Integer addr = labels.get(tokens[1]);
						if(addr == null) {
							throw new FCLCriticalException(CriticalException.LabelUnexistent,
									tokens[1]);
						}
						
						i = addr;
					}
					
					stack.pop();
					break;
					
				case "JMP":
					if(tokens.length < 2) {
						throw new FCLCriticalException(CriticalException.WrongNumberOfParameters);
					}
					
					Integer addr = labels.get(tokens[1]);
					if(addr == null) {
						throw new FCLCriticalException(CriticalException.LabelUnexistent,
								tokens[1]);
					}
					
					i = addr;
					break;
					
				case "CALL":
					break;
					
				// Platform instructions
				case "MOVE":
					System.out.println("Cannot execute MOVE command: platform is not connected.");
					break;
				case "PICKUP":
					System.out.println("Cannot execute PICKUP command: platform is not connected.");
					break;
				case "DISCARD":
					System.out.println("Cannot execute PICKUP command: platform is not connected.");
					break;
					
				// NOOP and HALT
				case "NOOP":
					break;
				case "HALT":
					System.out.println("Halting.");
					System.exit(0);
					break;
					
				// System instructions
				case "PRINT":
					stack.print();
					break;
				}
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("Instruction:        " + tokens[0]);
				System.out.println("Instruction number: " + i);
				stack.dump();
				return -1;
			}
		}
		
		return 0;
	}
}