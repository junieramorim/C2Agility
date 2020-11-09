package controller;

import java.util.List;

import entities.Drone;
import entities.Sensor;
import entities.Task;

/**
 * @author junier
 * Seeds used: 
 * 			2500 a 92219 (interval 1831)
 * 			2500 a 916169 (interval 1831)
 *
 */
public class DynamicChange {

	static public enum Type {
		MEMBER_FAILURE, SENSOR_FAILURE, ENV_CHANGE, ADD_TASKS;
	}

	private Type type;
	private int triggerTick;

	private Drone drone;
	
	private double delta;
	
	private Sensor droneSensor;
	
	private List<Sensor> allSensors;
	
	private List<Task> newTasks;


	private DynamicChange(Type type, int triggerTick) {
		this.type = type;
		this.triggerTick = triggerTick;
	}
	
	static public DynamicChange createDropDrone(int triggerTick, Drone drone) {
		DynamicChange instance = new DynamicChange(Type.MEMBER_FAILURE, triggerTick);
		instance.setDrone(drone);
		return instance;
	}

	static public DynamicChange createSensorChange(int triggerTick, Sensor dronesSensor, double delta) {
		DynamicChange instance = new DynamicChange(Type.SENSOR_FAILURE, triggerTick);
		instance.setDroneSensor(dronesSensor);
		instance.setDelta(delta);
		return instance;
	}
	
	static public DynamicChange createEnvChange(int triggerTick, List<Sensor> sensorsToChange, double delta) {
		DynamicChange instance = new DynamicChange(Type.ENV_CHANGE, triggerTick);
		instance.setAllSensors(sensorsToChange);
		instance.setDelta(delta);
		return instance;
	}
	
	static public DynamicChange addNewTasks(int triggerTick, List<Task> tasks) {
		DynamicChange instance = new DynamicChange(Type.ADD_TASKS, triggerTick);
		instance.setNewTasks(tasks);
		return instance;
	}

	public Object occur(int tickCount) {
		if (tickCount != this.triggerTick) {
			throw new RuntimeException("tickCount didn't triggered DynamicChange.");
		}
		switch (this.type) {
		case MEMBER_FAILURE:
			this.drone.drop();
			break;
		case SENSOR_FAILURE:
			this.droneSensor.updateQualities(this.delta);
			break;
		case ENV_CHANGE:
			for(Sensor s:allSensors)
				s.updateQualities(this.delta);
			break;
		case ADD_TASKS:
			return this.newTasks;
		}
		return true;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Drone getDrone() {
		return drone;
	}

	public void setDrone(Drone drone) {
		this.drone = drone;
	}

	public Sensor getDroneSensor() {
		return droneSensor;
	}

	public void setDroneSensor(Sensor droneSensor) {
		this.droneSensor = droneSensor;
	}

	public List<Sensor> getAllSensors() {
		return allSensors;
	}

	public void setAllSensors(List<Sensor> allSensors) {
		this.allSensors = allSensors;
	}

	public List<Task> getNewTasks() {
		return newTasks;
	}

	public void setNewTasks(List<Task> newTasks) {
		this.newTasks = newTasks;
	}
	

}