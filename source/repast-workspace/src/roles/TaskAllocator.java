package roles;

import java.util.ArrayList;
import java.util.List;

import entities.CollaborativeToken;
import entities.Drone;
import entities.Task;
import entities.Token;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.util.collections.Pair;
import utils.Factory;
import utils.Parameters;

public class TaskAllocator extends Role implements Comparable<Role> {

	private boolean allocating = true;
	
	private List<Task> newTasks = null;

	public void step(Drone drone, Token token) {
		if (token == null) {
			return;
		}
		
		//Check for new tasks
//		for(Task newTask : getNewTasks()) {
//			token.getAvailableTasks().add(newTask);
//		}
//		getNewTasks().clear();
		
		// treat CH1 in case of some task with problem (not completed);
		List<Task> reallocationTasks = new ArrayList<Task>();
		for (Pair<Task, Drone> pair : token.getCh1()) {
			if (pair.getSecond() != null) {
				reallocationTasks.add(pair.getFirst());			
			} 
		}
		for (Task t : reallocationTasks){
			token.setTaskAsAvailable(t);														
		} 
		//block_end

		List<Drone> executors = new ArrayList<Drone>();
		Pair<Task, Drone> bestPair = null;
		boolean flag = false;
		switch (token.getC2Approach()) {
		case CONFLICTED:
			if (token.getTeam().size() > 1) return;
			executors.add(drone);
			do {
				bestPair = AllocateBestPair(token.getAvailableTasks(), executors, token);
				if (bestPair != null) {
					token.setTaskAsAllocated(bestPair.getFirst(), bestPair.getSecond());
					flag = true;
				}
			} while (bestPair != null);
			break;
		case DECONFLICTED:
			executors.add(drone);
			Integer ntasksAllocated = 0;
			do {
				bestPair = AllocateBestPair(token.getAvailableTasks(), executors, token);
				if (bestPair != null) {
					token.setTaskAsAllocated(bestPair.getFirst(), bestPair.getSecond());
					ntasksAllocated = ntasksAllocated + 1;
					flag = true;
				}
			} while (ntasksAllocated < Token.getNTasksPerRound() && bestPair != null);
			break;
		case COORDINATED:
			executors.addAll(token.getTeam());
			executors.remove(drone);
			do {
				bestPair = AllocateBestPair(token.getAvailableTasks(), executors, token);
				if (bestPair != null) {
					token.setTaskAsAllocated(bestPair.getFirst(), bestPair.getSecond());
					flag = true;
				}
			} while (bestPair != null);
			break;
		case COLLABORATIVE:
			executors.addAll(((CollaborativeToken) token).slavesOf(drone));
			executors.addAll(((CollaborativeToken) token).getMasters());
			List<Task> availableTasks = new ArrayList<Task>(token.getAvailableTasks());
			do {
				bestPair = AllocateBestPair(availableTasks, executors, token);
				if (bestPair != null) {
					if (drone == bestPair.getSecond() 
							|| ((CollaborativeToken)token).slavesOf(drone).contains(bestPair.getSecond())) {
						token.setTaskAsAllocated(bestPair.getFirst(), bestPair.getSecond());
						flag = true;
					}
					availableTasks.remove(bestPair.getFirst());
				}
			} while (bestPair != null);
			break;
		case EDGE:
			executors.addAll(token.getTeam());
			do {
				bestPair = AllocateBestPair(token.getAvailableTasks(), executors, token);
				if (bestPair != null) {
					token.setTaskAsAllocated(bestPair.getFirst(), bestPair.getSecond());
					flag = true;
				}
			} while (bestPair != null);
			break;
		}
		this.allocating = flag;
		if(!flag) { // In case of no allocation, the ch3 channel is filled
			token.setCh3(token.getAvailableTasks());
		}
	}

	private Double taskWeight(Task task, Drone drone, Token token) {
		Double distance = Factory.getSpace().getDistance(dronesLastPosition(drone, token), task.getPosition());
		Double quality = drone.bestSensorToTask(task).qualityToTask(task);
		return distance / quality;
	}

	public void refillTasksFromDrone(Token token, Drone drone) {
		token.dronesAllocatedTasks(drone).forEach(t -> token.setTaskAsAvailable(t));
	}

	public NdPoint dronesLastPosition	(Drone drone, Token token) {
		List<Task> allocatedTasks = token.dronesAllocatedTasks(drone);
		return allocatedTasks.isEmpty() ? drone.position()
				: allocatedTasks.get(allocatedTasks.size() - 1).getPosition();
	}

	public String information() {
		return getAbbreviation()+"\n";
	}

	@Override
	public int getOrder() {
		return 1;
	}

	private Pair<Task, Drone> AllocateBestPair(List<Task> tasks, List<Drone> executors, Token token) {
		Drone bestExecutor = null;
		Task bestTask = null;
		Double bestWeight = Double.POSITIVE_INFINITY;
		for (Drone executor : executors) {
			Integer allocatedFuel = executor.allocatedFuel(token);
			for (Task task : tasks) {
				Double routeDistance = Factory.getSpace().getDistance(task.getPosition(),
						dronesLastPosition(executor, token));
				Integer routeFuel = (int) Math.ceil(routeDistance / executor.getSpeed());
				Double weight = taskWeight(task, executor, token);
				if (executor.bestSensorToTask(task).qualityToTask(task) >= (((double) Parameters.getInstance().threshold()) / 100)
						&& allocatedFuel + routeFuel + task.getCost() <= executor.getFuel() && (weight < bestWeight)) {
					bestExecutor = executor;
					bestTask = task;
					bestTask.setQualityResolution(executor.bestSensorToTask(task).qualityToTask(task));
					bestWeight = weight;
				}
			}
		}
		if (bestTask == null || bestExecutor == null)
			return null;
		else
			return new Pair<Task, Drone>(bestTask, bestExecutor);
	}

	@Override
	public int compareTo(Role o) {
		if (this.getOrder() < o.getOrder())
			return -1;
		return 1;
	}

	public boolean isAllocating() {
		return allocating;
	}

	public void setAllocating(boolean allocating) {
		this.allocating = allocating;
	}

	public String getAbbreviation() {
		return "TA";
	}

	/**
	 * @return the newTasks
	 */
	public List<Task> getNewTasks() {
		if(newTasks == null)
			newTasks = new ArrayList<Task>();
		return newTasks;
	}

	/**
	 * @param newTasks the newTasks to set
	 */
	public void setNewTasks(List<Task> newTasks) {
		this.newTasks = newTasks;
	}
	
}
