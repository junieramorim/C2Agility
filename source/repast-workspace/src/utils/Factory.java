package utils;

import java.util.List;

import controller.DynamicChange;
import entities.Drone;
import entities.Task;
import entities.Token;
import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.util.collections.Pair;

/** Class that is responsible to create the agents in the environment. */
public abstract class Factory {

	static private Context<Object> context;
	
	static private ContinuousSpace<Object> space;
	
	static public Context<Object> getContext() {
		return context;
	}
	
	static public ContinuousSpace<Object> getSpace() {
		return space;
	}
	
	protected Factory(Context<Object> context, int spaceSize) {
		Factory.context = context;
		Factory.space = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null).
				createContinuousSpace("space", context, new RandomCartesianAdder<Object>(),
						new repast.simphony.space.continuous.StrictBorders(), spaceSize, spaceSize);
	}
	

	public abstract void prepareFiles();
	
	public abstract List<Drone> createDrones();
	
	public abstract List<Drone> createDrones(int numberDrones);
	
	public abstract List<Task> createTasks();
	
	public abstract List<Task> createTasks(int numberTasks);
	
	public abstract List<Task> createTasks(int initialIndex, int quantity);
	
	public abstract List<DynamicChange> createDynamicChanges(List<Drone> drones);
	
	public abstract List<DynamicChange> createDynamicChanges(List<Drone> drones, List<Pair<Integer, String>> sequence);
	
	public abstract List<DynamicChange> createDynamicChanges(List<Drone> drones, List<Scenario> scenarios, int id);

	public abstract Token createToken(List<Drone> team, List<Task> availableTasks);

}
