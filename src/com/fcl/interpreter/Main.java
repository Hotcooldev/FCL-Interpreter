package com.fcl.interpreter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fcl.interpreter.core.FCLCore;

public class Main {

	public static void main(String[] args) {
		if(args.length < 1) {
			System.out.println("Invalid number of arguments.");
			System.out.println("Usage: fclrun file.pfcl");
			System.exit(0);
		}
		
		String inFile = args[0];
		
		// Read input.
		List<String> instructions = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(inFile))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	instructions.add(line);
		    }
		} catch (FileNotFoundException e) {
			System.out.println("The specified file was not found.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Could not open the specified file.");
			System.out.println("Perhaps you should check for read permission.");
			System.exit(0);
		}
		
		FCLCore core = new FCLCore();
		core.run(instructions);
	}

}
