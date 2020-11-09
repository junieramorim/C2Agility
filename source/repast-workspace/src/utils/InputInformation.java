package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.DynamicChange;
import repast.simphony.util.collections.Pair;

public class InputInformation {
	
	private final String separator = ";";
	
	private static InputInformation inputInformation = null;
	
	private InputInformation() {
		//singleton pattern
	}
	
	/**
	 * @param fileName
	 * @return a list of Strings representing each line in the file
	 */
	public List<String> readFileByLine(String fileName) {
		List<String> data = new ArrayList<String>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			while (line != null) {
				data.add(line);
				line = reader.readLine();
			}
			reader.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public static InputInformation getInstance() {
		if(inputInformation == null)
			inputInformation = new InputInformation();
		return inputInformation;
	}

	/**
	 * @param lines
	 * @return converts each line (represented by Strings) in Scenarios
	 */
	public List<Scenario> linesToScenarios(List<String> lines){
		List<Scenario> scenarios = new ArrayList<Scenario>();
		for(int i=0 ; i<lines.size() ; i++) {
			if(i == 0) continue; //ignore the line 0 - head
			Scenario sc = new Scenario();
			String[] dt = lines.get(i).split(separator);
			sc.setId(Long.valueOf(dt[0]));
			sc.setMembers(Integer.valueOf(dt[1]));
			sc.setTasks(Integer.valueOf(dt[2]));
			for(int j=3 ; j<dt.length ; j++)
				sc.getActions().add(dt[j]);
			scenarios.add(sc);
		}
		return scenarios;
	}
	
	
	
	public  List<Pair<Integer,String>> getActions(){
		List<Pair<Integer,String>> scenario = new ArrayList<Pair<Integer,String>>();
		
		scenario.add(new Pair<Integer, String>(2, DynamicChange.Type.SENSOR_FAILURE.name()));

		scenario.add(new Pair<Integer, String>(2, DynamicChange.Type.MEMBER_FAILURE.name()));

		scenario.add(new Pair<Integer, String>(1, DynamicChange.Type.SENSOR_FAILURE.name()));
		
		return scenario;
		
	}
	
}
