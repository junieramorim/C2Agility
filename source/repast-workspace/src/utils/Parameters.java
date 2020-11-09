package utils;

import domain.C2Approach;
import domain.Treatment;
import repast.simphony.engine.environment.RunEnvironment;

public class Parameters {

	static Parameters instance = null;
	
	private Parameters() { }
	
	static public Parameters getInstance() {
		if (instance == null) {
			instance = new Parameters();
		}
		return instance;
	}
	
	public int spaceSize() {
		return (Integer) RunEnvironment.getInstance().getParameters().getValue("spaceSize");
	}
	
	public int randomSeed() {
		return (Integer) RunEnvironment.getInstance().getParameters().getValue("randomSeed");
	}
	
	public int nDrones() {
		return (Integer) RunEnvironment.getInstance().getParameters().getValue("nDrones");
	}
	
	public int threshold() {
		return (Integer) RunEnvironment.getInstance().getParameters().getValue("threshold");
	}
	
	public int nTasks() {
		return (Integer) RunEnvironment.getInstance().getParameters().getValue("nTasks");
	}
	
	public int nTasksPerRound() {
		return (Integer) RunEnvironment.getInstance().getParameters().getValue("nTasksPerRound");
	}
	
	public boolean lightScreen() {
		return (Boolean) RunEnvironment.getInstance().getParameters().getValue("lightScreen");
	}
	
	public boolean infoType1() {
		return (Boolean) RunEnvironment.getInstance().getParameters().getValue("info1");
	}
	
	public boolean hasDropDrones() {
		return (Boolean) RunEnvironment.getInstance().getParameters().getValue("hasDropDrones");
	}
	
	public int dropDronesPeriod() {
		return (Integer) RunEnvironment.getInstance().getParameters().getValue("dropDronesPeriod");
	}
	
	public boolean hasSensorChanges() {
		return (Boolean) RunEnvironment.getInstance().getParameters().getValue("hasSensorChanges");
	}
	
	public int sensorChangePeriod() {
		return (Integer) RunEnvironment.getInstance().getParameters().getValue("sensorChangePeriod");
	}
	
	public C2Approach initialC2Approach() {
		switch ((String) RunEnvironment.getInstance().getParameters().getValue("initialC2Approach")) {
		case "CONFLICTED": return C2Approach.CONFLICTED;
		case "DECONFLICTED": return C2Approach.DECONFLICTED;
		case "COORDINATED": return C2Approach.COORDINATED;
		case "COLLABORATIVE": return C2Approach.COLLABORATIVE;
		case "EDGE": return C2Approach.EDGE;
		}
		return null;
	}
	
	public Treatment agilityMethod() {
		switch ((String) RunEnvironment.getInstance().getParameters().getValue("agilityMethod")) {
		case "#1": return Treatment.NOC2AGILITY;
		case "#2": return Treatment.ONLYC2MENEUVER;
		case "#3": return Treatment.C2AGILITY;
		}
		return null;
	}
	
	public int simulationTime() {
		return (Integer) RunEnvironment.getInstance().getParameters().getValue("simulationTime");
	}
	
	public int repetitions() {
		return (Integer) RunEnvironment.getInstance().getParameters().getValue("repetitions");
	}
	
	public boolean showAllocations() {
		return (Boolean) RunEnvironment.getInstance().getParameters().getValue("showAllocations");
	}
	
	public String changes() {
		switch ((String) RunEnvironment.getInstance().getParameters().getValue("aleatory")) {
		case "FILE": return "FILE";
		case "RANDOM": return "RANDOM";
		}
		return null;
	}
	
	public String fileName() {
		return ((String) RunEnvironment.getInstance().getParameters().getValue("fileName"));
	}
	
	public String path() {
		return (String) RunEnvironment.getInstance().getParameters().getValue("path");
	}
	
	public int scenario() {
		return (int) RunEnvironment.getInstance().getParameters().getValue("scenario");
	}
	
	public boolean appendFile() {
		return (Boolean) RunEnvironment.getInstance().getParameters().getValue("appendFile");
	}
	
}
