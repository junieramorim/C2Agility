package roles;

import java.util.ArrayList;
import java.util.List;

import domain.Treatment;
import entities.Drone;
import entities.Sensor;
import entities.Task;
import entities.Token;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.space.SpatialMath;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.collections.Pair;
import utils.Factory;
import utils.Parameters;
import utils.ResultsReport;

public class Executor extends Role implements Comparable<Role>{

	private List<Task> allocatedTasks;

	private List<Task> executedTasks;

	private Integer executionCount;
	
	private double reward = 0;

	private List<Task> impossibleTasks;
	
	private boolean reallocating = false;
	
	private Task finishedTask = null; //created to be used by the statechart EX

	private int reconfigurations = 0;
	
	private int operationTime = 0;
	
	public Executor() {
		this.allocatedTasks = new ArrayList<Task>();
		this.executedTasks = new ArrayList<Task>();
		this.executionCount = 0;
		this.impossibleTasks = new ArrayList<Task>();
	}
	
	public Integer getExecutionCount() {
		return this.executionCount;
	}

	private Task getCurrentTask() {
		if (this.allocatedTasks.isEmpty()) {
			return null;
		}
		return this.allocatedTasks.get(0);
	}

	@Override
	public void step(Drone drone, Token token) {
		
//		if (token != null) {
//			lookupToken(token, drone);
//		}
		
		if(Parameters.getInstance().agilityMethod().equals(Treatment.C2AGILITY))
			reconfigure(drone); //The drone must try to reconfigure first

		removeImpossibleTasks(drone); //remove tasks when sensors broke 
		
		if (getCurrentTask() != null) {
			if (drone.position().equals(getCurrentTask().getPosition())) {
				executeCurrentTask(drone);
			} else {
				move(drone);
			}
		}
		if (token != null) {
			updateToken(token, drone);
		}
	}

	private void lookupToken(Token token, Drone drone) {
		this.allocatedTasks = token.dronesAllocatedTasks(drone);
		this.allocatedTasks.removeAll(this.executedTasks);
		this.allocatedTasks.removeAll(this.impossibleTasks);
	}

//	private void removeImpossibleTasks(Drone drone, Token token) {
//		double lim = ((double) Parameters.getInstance().threshold())/100;
//		while (getCurrentTask() != null && (currentSensor(drone)==null || currentSensor(drone).qualityToTask(getCurrentTask()) < lim)) {
//			this.impossibleTasks.add(getCurrentTask());
//			this.allocatedTasks.remove(getCurrentTask());
//			this.executionCount = 0;
//			if(Parameters.getInstance().agilityMethod().equals(Treatment.C2AGILITY))
//				reconfigure(drone);
//		}
//	}
	
	private void removeImpossibleTasks(Drone drone) {
		double lim = ((double) Parameters.getInstance().threshold())/100;
		while (getCurrentTask() != null && drone.bestSensorToTask(getCurrentTask()).qualityToTask(getCurrentTask()) < lim) {
			this.impossibleTasks.add(getCurrentTask());
			this.allocatedTasks.remove(getCurrentTask());
			this.executionCount = 0;
		}
		if(Parameters.getInstance().agilityMethod().equals(Treatment.C2AGILITY))
			reconfigure(drone);
			
//		while (getCurrentTask() != null && drone.bestSensorToTask(getCurrentTask()).qualityToTask(getCurrentTask()) < lim) {
//			this.impossibleTasks.add(getCurrentTask());
//			this.allocatedTasks.remove(getCurrentTask());
//			this.executionCount = 0;
//			if(Parameters.getInstance().agilityMethod().equals(Treatment.C2AGILITY))
//				reconfigure(drone);
//		}
	}
	

	private Sensor currentSensor(Drone drone) {
		for (Sensor s : drone.getSensors()) {
			if (s.isEnable()) {
				return s;
			}
		}
		return null;
	}
	
	/**
	 * @param drone
	 * @return true in case of a reconfiguration executed
	 */
	private boolean reconfigure(Drone drone) {
		if(getCurrentTask() != null) {
			Sensor bestSensor = drone.bestSensorToTask(getCurrentTask()); 
			for (Sensor s : drone.getSensors()) {
				if(s.isEnable()) {
					if(s == bestSensor)
						return false;
					else {
						s.setEnable(false);
						bestSensor.setEnable(true);
						this.reconfigurations++;
						return true;
					}
				}else{
					if(s == bestSensor) {
						s.setEnable(true);
						this.reconfigurations++;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * @param memberId
	 * @param token
	 */
	private void executeCurrentTask(Drone drone) {
		Task currentTask = getCurrentTask();
		this.executionCount = this.executionCount + 1;
		this.finishedTask = null;
		if (this.executionCount == currentTask.getCost()) {
			this.executedTasks.add(currentTask);
			this.finishedTask = currentTask;
			this.reward = this.reward + currentTask.getQualityResolution();
			this.operationTime = (int) RunEnvironment.getInstance().getCurrentSchedule().getTickCount();
			this.allocatedTasks.remove(currentTask);
			this.executionCount = 0;
			//System.out.println("Execution Completed: (t " + currentTask + ", e " + drone + ")");
			//Write file results
			this.registerResultsModel1(drone.getId());
			if (ContextUtils.getContext(currentTask) != null) {
				ContextUtils.getContext(currentTask).remove(currentTask);
			}
		}
	}
	
	private void move(Drone drone) {
		Task target = getCurrentTask();
		double angle = SpatialMath.calcAngleFor2DMovement(Factory.getSpace(), drone.position(), target.getPosition());
		double targetDistance = Factory.getSpace().getDistance(drone.position(), target.getPosition());
		double moveDistance = Math.min(drone.getSpeed(), targetDistance);
		Factory.getSpace().moveByVector(drone, moveDistance, angle, 0);
	}
	
	private void updateToken(Token token, Drone drone) {
		for(Task t : this.impossibleTasks) {
			token.setTaskAsCh1(t, drone);
		}
		//verify if there is a task to be reallocated
		if(this.impossibleTasks != null && this.impossibleTasks.size() > 0)
			setReallocating(true);
		else
			setReallocating(false);
		
		//begin
		if((this.executedTasks != null && !executedTasks.isEmpty()) || (this.impossibleTasks != null && !impossibleTasks.isEmpty())) {
			List<Pair<Task, Drone>> toRemove = new ArrayList<Pair<Task, Drone>>();
			for (Pair<Task, Drone> relation : token.getAllocations()) {
				if (this.executedTasks.contains(relation.getFirst()) || this.impossibleTasks.contains(relation.getFirst())) {
					toRemove.add(relation);
				}
			}
			token.getAllocations().removeAll(toRemove);
		}
		
		this.allocatedTasks = token.dronesAllocatedTasks(drone);
		this.allocatedTasks.removeAll(this.executedTasks);
		this.allocatedTasks.removeAll(this.impossibleTasks);
		//end
		
		
		this.impossibleTasks.clear();
		this.executedTasks.forEach(t -> token.setTaskAsCh1(t, null));
		this.executedTasks.clear();
	}
	

	@Override
	public int getOrder() {
		return 2;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	public boolean isReallocating() {
		return reallocating;
	}

	public Task getFinishedTask() {
		return finishedTask;
	}

	public void setFinishedTask(Task finishedTask) {
		this.finishedTask = finishedTask;
	}

	public void setReallocating(boolean reallocating) {
		this.reallocating = reallocating;
	}

	public List<Task> getImpossibleTasks() {
		return impossibleTasks;
	}

	public void setImpossibleTasks(List<Task> impossibleTasks) {
		this.impossibleTasks = impossibleTasks;
	}

	public List<Task> getAllocatedTasks() {
		return allocatedTasks;
	}
	
	public String getAbbreviation() {
		return "EX";
	}
	
	public String information() {
		String information = "";
		information += "T: " + this.allocatedTasks + "\n";
//		information += "E: " + this.executedTasks + "\n";
		return information;
	}

	@Override
	public int compareTo(Role o) {
		if(this.getOrder() < o.getOrder())
			return -1;
		return 1;
	}
	
	/**
	 * Register results in file 
	 * Finished tasks, executor and time
	 * "OCCURENCE;MEMBER ID;TASK ID; TIME"
	 */
	private void registerResultsModel1(Integer id) {
		StringBuffer sb = new StringBuffer();
		//sb.append("OCCURENCE;MEMBER ID;TASK ID; TIME");
		sb.append("TASK FINISHED");
		sb.append(";");
		sb.append(id.intValue());
		sb.append(";");
		sb.append(this.finishedTask.getID());
		sb.append(";");
		sb.append(RunEnvironment.getInstance().getCurrentSchedule().getTickCount());
		sb.append(";");
		sb.append("\n");
		ResultsReport.getInstance().writeFile(ResultsReport.typeFiles.RUNNING.name(), true, sb.toString());
	}

	/**
	 * @return the reconfigurations
	 */
	public int getReconfigurations() {
		return reconfigurations;
	}

	/**
	 * @param reconfigurations the reconfigurations to set
	 */
	public void setReconfigurations(int reconfigurations) {
		this.reconfigurations = reconfigurations;
	}

	/**
	 * @return the operationTime
	 */
	public int getOperationTime() {
		return operationTime;
	}

	/**
	 * @param operationTime the operationTime to set
	 */
	public void setOperationTime(int operationTime) {
		this.operationTime = operationTime;
	}

	/**
	 * @return the reward
	 */
	public double getReward() {
		return reward;
	}

	/**
	 * @param reward the reward to set
	 */
	public void setReward(double reward) {
		this.reward = reward;
	}
	
}
