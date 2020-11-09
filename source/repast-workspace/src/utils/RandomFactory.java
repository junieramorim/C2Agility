package utils;

import java.util.ArrayList;
import java.util.List;

import controller.DynamicChange;
import domain.Treatment;
import entities.CollaborativeToken;
import entities.ConflictedToken;
import entities.CoordinatedToken;
import entities.DeconflictedToken;
import entities.Drone;
import entities.EdgeToken;
import entities.Sensor;
import entities.Task;
import entities.Token;
import repast.simphony.context.Context;
import repast.simphony.random.RandomHelper;
import repast.simphony.util.collections.Pair;

public class RandomFactory extends Factory {

	private int defaultDronesFuel;
	
	RandomFactory(Context<Object> context) {
		super(context, Parameters.getInstance().spaceSize());
		this.defaultDronesFuel = Parameters.getInstance().simulationTime();
	}

	@Override
	public List<Drone> createDrones() {
		List<Drone> drones = new ArrayList<Drone>();
		for (int id = 0; id < Parameters.getInstance().nDrones(); id = id + 1) {
			
			// createSensors();
			List<Sensor> sensors = new ArrayList<Sensor>();
			do {
				//Following lines provide a randomize sensors' selection
				List<Sensor.Type> types = new ArrayList<Sensor.Type>();
				for (Sensor.Type sT : Sensor.Type.values()) {
					if (RandomHelper.nextIntFromTo(0, 1) == 1) {
						types.add(sT);
					}
				}
				while (types.size() > 0) {
					Sensor.Type t = types.get(RandomHelper.nextIntFromTo(0, types.size() - 1));
					sensors.add(new Sensor(t));
					types.remove(t);
				}
				//Block end
				
			} while (sensors.size() == 0);
			
			Drone drone = new Drone(id, sensors, this.defaultDronesFuel);
			drones.add(drone);
			super.getContext().add(drone);
		}
		return drones;
	}
	
	@Override
	public List<Drone> createDrones(int numberDrones) {
		List<Drone> drones = new ArrayList<Drone>();
		for (int id = 0; id < numberDrones; id = id + 1) {
			
			// createSensors();
			List<Sensor> sensors = new ArrayList<Sensor>();
			do {
				//Following lines provide a randomize sensors' selection
				List<Sensor.Type> types = new ArrayList<Sensor.Type>();
				for (Sensor.Type sT : Sensor.Type.values()) {
					if (RandomHelper.nextIntFromTo(0, 1) == 1) {
						types.add(sT);
					}
				}
				while (types.size() > 0) {
					Sensor.Type t = types.get(RandomHelper.nextIntFromTo(0, types.size() - 1));
					sensors.add(new Sensor(t));
					types.remove(t);
				}
				//Block end
				
			} while (sensors.size() == 0);
			
			// In case of Agility Method where we do not have reconfiguration (No C2 Agility)
			if (Parameters.getInstance().agilityMethod().equals(Treatment.NOC2AGILITY) || Parameters.getInstance().agilityMethod().equals(Treatment.ONLYC2MENEUVER)) {
				sensors.get(RandomHelper.nextIntFromTo(0, sensors.size() - 1)).setEnable(true);
			}
			
			Drone drone = new Drone(id, sensors, this.defaultDronesFuel);
			drones.add(drone);
			super.getContext().add(drone);
		}
		return drones;
	}

	@Override
	public List<Task> createTasks() {
		List<Task> tasks = new ArrayList<Task>();
		for (int id = 0; id < Parameters.getInstance().nTasks(); id = id + 1) {
			int taskType = RandomHelper.nextIntFromTo(0, Task.N_TYPES - 1);
			Task task = new Task(id, taskType);
			tasks.add(task);
			super.getContext().add(task);
		}
		return tasks;
	}
	
	@Override
	public List<Task> createTasks(int numberTasks) {
		List<Task> tasks = new ArrayList<Task>();
		for (int id = 0; id < numberTasks; id = id + 1) {
			int taskType = RandomHelper.nextIntFromTo(0, Task.N_TYPES - 1);
			Task task = new Task(id, taskType);
			tasks.add(task);
			super.getContext().add(task);
		}
		return tasks;
	}
	
	//Create a quantity of tasks to be added in the available tasks
	@Override
	public List<Task> createTasks(int initialIndex, int quantity){
		List<Task> ret = new ArrayList<Task>();
		for (int delta = 0; delta < quantity; delta++) {
			int taskType = RandomHelper.nextIntFromTo(0, Task.N_TYPES - 1);
			Task task = new Task(initialIndex+delta, taskType);
			ret.add(task);
		}
		return ret;
	}
	
	@Override
	public List<DynamicChange> createDynamicChanges(List<Drone> drones) {
		List<DynamicChange> dynamicChanges = new ArrayList<DynamicChange>();
		List<Drone> activeDrones = new ArrayList<Drone>(drones);
		
		for(int tickCount = 1; tickCount < Parameters.getInstance().simulationTime(); tickCount += 1) {
			
			// Create a random DropDrone without repeating;
			if (Parameters.getInstance().hasDropDrones() && tickCount % Parameters.getInstance().dropDronesPeriod() == 0 && activeDrones.size() > 0) {
				Drone toDrop = activeDrones.get(RandomHelper.nextIntFromTo(0, activeDrones.size() - 1));
				dynamicChanges.add(DynamicChange.createDropDrone(tickCount, toDrop));
				activeDrones.remove(toDrop);
			}
			
			// Create a random SensorChange without repeating or choosing from a dropped drone; 
			if (Parameters.getInstance().hasSensorChanges() && tickCount % Parameters.getInstance().sensorChangePeriod() == 0 && activeDrones.size() > 0) {
				Drone choice = activeDrones.get(RandomHelper.nextIntFromTo(0, activeDrones.size() - 1));
				List<Sensor> sensors = choice.getSensors();
				Sensor toChange = sensors.get(RandomHelper.nextIntFromTo(0, sensors.size() - 1));
				
				if(RandomHelper.nextIntFromTo(0,10)>=5) {
					//Sensors broken
					dynamicChanges.add(DynamicChange.createSensorChange(tickCount, toChange, 0.0));
				}else {
					//Environment impact on the sensors
					dynamicChanges.add(DynamicChange.createSensorChange(tickCount, toChange, 0.5));
				}
			}
		}
		return dynamicChanges;
	}
	
	/**
	 * Create dynamic changes according to a predefined list of actions
	 */
	public List<DynamicChange> createDynamicChanges(List<Drone> drones, List<Pair<Integer, String>> sequence){
		List<String> effects = new ArrayList<String>();
		//int seed = 0;
		for(Pair<Integer,String> p : sequence) {
			//seed=+p.getFirst();
			for(int j=0;j < p.getFirst(); j++)
				effects.add(p.getSecond());
		}
		
		int i = 0;
		
		List<DynamicChange> dynamicChanges = new ArrayList<DynamicChange>();
		List<Drone> activeDrones = new ArrayList<Drone>(drones);
		List<Sensor> changedSensors = new ArrayList<Sensor>();
		
		//To create a random effect within each timeslice
		int timeslice = Parameters.getInstance().simulationTime() / effects.size();
		//int eventTime = ((int)Math.floor(Math.random() * ((timeslice - 1 + 1) + 1)))%timeslice;
//		if(eventTime == 0)
//			eventTime = 1;
		int eventTime = (int)RandomHelper.nextIntFromTo(1, timeslice);
		int count = 0;
		
		for(int tickCount = 1; tickCount < Parameters.getInstance().simulationTime(); tickCount += 1) {
			
			if((tickCount % eventTime == 0) && (activeDrones.size() > 0) && (i < effects.size())) {
				
				count++;
//				eventTime = ((int)Math.floor(Math.random() * ((timeslice - 1 + 1) + 1)))%timeslice;
//				if(eventTime == 0)
//					eventTime = 1;
				eventTime = (int)RandomHelper.nextIntFromTo(1, timeslice);
				eventTime = eventTime + (count*timeslice);
				//eventTime = RandomHelper.nextIntFromTo(1, timeslice-1) + (count*timeslice);
				
				String event = effects.get(i);
				
				// Create a random DropDrone without repeating;
				if(event.equals(DynamicChange.Type.MEMBER_FAILURE.name())) {
					Drone toDrop = activeDrones.get(RandomHelper.nextIntFromTo(0, activeDrones.size() - 1));
					dynamicChanges.add(DynamicChange.createDropDrone(tickCount, toDrop));
					activeDrones.remove(toDrop);
				}
				
				// Create a sensor failure;
				if(event.equals(DynamicChange.Type.SENSOR_FAILURE.name())) {
					Drone choice = activeDrones.get(RandomHelper.nextIntFromTo(0, activeDrones.size() - 1));
					Sensor toChange = null;
					if (Parameters.getInstance().agilityMethod().equals(Treatment.NOC2AGILITY) || Parameters.getInstance().agilityMethod().equals(Treatment.ONLYC2MENEUVER)) {
						for(Sensor s : choice.getSensors()) {
							if(s.isEnable() && !changedSensors.contains(s)) {
								toChange = s;
								break;
							}
						}
					}else {
						if(!changedSensors.containsAll(choice.getSensors())){
							toChange = choice.getSensors().get(RandomHelper.nextIntFromTo(0, choice.getSensors().size() - 1));
							if(changedSensors.contains(toChange)) {
								while(changedSensors.contains(toChange)) {
									toChange = choice.getSensors().get(RandomHelper.nextIntFromTo(0, choice.getSensors().size() - 1));
								}
							}
						}
					}
					
					if(toChange != null) {
						System.out.println("====> Sensor "+toChange.hashCode() + "  ------ Time:"+tickCount);
					
						changedSensors.add(toChange);
						dynamicChanges.add(DynamicChange.createSensorChange(tickCount, toChange, 0.0));
					}
				}
				
				// Environment changes that impact the sensors;
				// Consider the operation cost 
				if(event.equals(DynamicChange.Type.ENV_CHANGE.name())) {
					List<Sensor> sensorsChanged = new ArrayList<Sensor>();
					Sensor.Type type = Sensor.Type.values()[RandomHelper.nextIntFromTo(0, Sensor.Type.values().length - 1)];
					for(Drone drone : activeDrones) {
						Sensor uniqueSensor = null;
						if (Parameters.getInstance().agilityMethod().equals(Treatment.NOC2AGILITY) || Parameters.getInstance().agilityMethod().equals(Treatment.ONLYC2MENEUVER)) {
							uniqueSensor =drone.getActiveSensor();
							if(uniqueSensor != null && uniqueSensor.getType().name().equals(type.name()))
								sensorsChanged.add(uniqueSensor);
						}else {
							for(Sensor sensor : drone.getSensors()) {
								if(sensor.getType().name().equals(type.name()))
									sensorsChanged.add(sensor);
							}
						}
					}
					
					System.out.println("====> Set of Sensors # "+changedSensors.size());
					dynamicChanges.add(DynamicChange.createEnvChange(tickCount, sensorsChanged, 0.7));
				}
				
				// Add tasks - we are considering 1 per time;
				if(event.equals(DynamicChange.Type.ADD_TASKS.name())) {
					int taskQuantitity = 1;
					int initialId = tickCount*100;
					List<Task> newTasks = createTasks(initialId, taskQuantitity);
					dynamicChanges.add(DynamicChange.addNewTasks(tickCount, newTasks));
				}
				
				if(i < dynamicChanges.size()) {
					StringBuffer log = new StringBuffer();
					log.append("Event inserted: Drone - ");
					log.append(dynamicChanges.get(i).getDrone()!=null?dynamicChanges.get(i).getDrone().getId():"- - -");
					log.append(" | Sensor ID - ");
					log.append(dynamicChanges.get(i).getDroneSensor()!=null?dynamicChanges.get(i).getDroneSensor().toString():"- - -");
					log.append(" | Sensor Type - ");
					log.append(dynamicChanges.get(i).getDroneSensor()!=null?dynamicChanges.get(i).getDroneSensor().getType().name():"- - -");
					log.append(" | Event - ");
					log.append(dynamicChanges.get(i).getType().toString());
					ResultsReport.getInstance().writeFile(ResultsReport.typeFiles.PARTIAL.toString(), true, log.toString());
				}
				
				i++;
			}
		}
		return dynamicChanges;
	}
	
	/**
	 * @return the defaultDronesFuel
	 */
	public int getDefaultDronesFuel() {
		return defaultDronesFuel;
	}

	/**
	 * @param defaultDronesFuel the defaultDronesFuel to set
	 */
	public void setDefaultDronesFuel(int defaultDronesFuel) {
		this.defaultDronesFuel = defaultDronesFuel;
	}

	@Override
	public List<DynamicChange> createDynamicChanges(List<Drone> drones, List<Scenario> scenarios, int id) {
		List<Pair<Integer,String>> actions = new ArrayList<Pair<Integer,String>>(); //number of repetitions and the action to be repeated
		for(String action : scenarios.get(id).getActions()) {
			actions.add(new Pair<Integer, String>(1, DynamicChange.Type.valueOf(action).name()));
		}
		return createDynamicChanges(drones, actions);
	}
	
	
	@Override
	public Token createToken(List<Drone> team, List<Task> availableTasks) {
		switch (Parameters.getInstance().initialC2Approach()) {
		
		case CONFLICTED: {
			ConflictedToken token = new ConflictedToken(team, availableTasks, 0);
			return token;
		}
		case DECONFLICTED: {
			Drone c2ApproachSelector = team.get(0);
			DeconflictedToken token = new DeconflictedToken(team, availableTasks, 0, c2ApproachSelector);
			return token;
		}

		case COORDINATED: {
			Drone master = team.get(0);
			CoordinatedToken token = new CoordinatedToken(team, availableTasks, master, 0);
			return token;
		}
		
		case COLLABORATIVE: {
			List<Drone> masters = new ArrayList<Drone>();
			for (int idx = 0; idx < CollaborativeToken.getNMasters(); idx++) {
				masters.add(team.get(idx));
			}
			CollaborativeToken token = new CollaborativeToken(team, availableTasks, masters, 0);
			return token;
		}
		
		case EDGE: {
			EdgeToken token = new EdgeToken(team, availableTasks, 0);
			return token;
		}
	
		}
		return null;
	}
	
	//Define files that will be used by the simulation to register results
	@Override
	public void prepareFiles() {
		
		ResultsReport.getInstance().getFileNames().put(ResultsReport.typeFiles.RUNNING.name(), "results.log");
		ResultsReport.getInstance().getFileNames().put(ResultsReport.typeFiles.PARTIAL.name(), "partial.log");
		ResultsReport.getInstance().getFileNames().put(ResultsReport.typeFiles.FINAL.name(), "finalResults.log");
		ResultsReport.getInstance().setUrl(Parameters.getInstance().path());
		if(!Parameters.getInstance().appendFile())
			ResultsReport.getInstance().clearAll();
		else {
			ResultsReport.getInstance().writeFile(ResultsReport.typeFiles.RUNNING.name(), true, "###########################################################\n");
			ResultsReport.getInstance().writeFile(ResultsReport.typeFiles.RUNNING.name(), true, "#########               NEW EXECUTION             #########\n");
			ResultsReport.getInstance().writeFile(ResultsReport.typeFiles.RUNNING.name(), true, "###########################################################\n");
		}
	}
}
