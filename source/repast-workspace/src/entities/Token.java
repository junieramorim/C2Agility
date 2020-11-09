package entities;

import java.util.ArrayList;
import java.util.List;

import domain.C2Approach;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.util.collections.Pair;
import utils.Parameters;

public abstract class Token {

	static public Integer getNTasksPerRound() {
		return (Integer) RunEnvironment.getInstance().getParameters().getValue("nTasksPerRound");
	}
	
	static public Integer getNMasters() {
		return (Integer) RunEnvironment.getInstance().getParameters().getValue("nCollaborativeMasters");
	}
	
	private Pair<Drone, Drone> fromTo;
	
	private List<Drone> team = new ArrayList<Drone>();

	private List<Task> availableTasks; //Channel ch2

	private List<Pair<Task, Drone>> allocations = new ArrayList<Pair<Task, Drone>>(); //channel MK
	
	private List<Pair<Task, Drone>> ch1; //channel to report successful or impossible tasks (second parameter null)
	
	private List<Task> ch3; //channel used by Task Allocator to give back impossible tasks to the C2 Approach Selector
	
	private Integer version;
	
	private Integer timeout;

	private Pair<C2Approach, Drone> nextC2Approach;

	private List<Drone> nextMasters;
	
	private List<Pair<Drone, Boolean>> handShakeStatus;
	

	public Token(List<Task> availableTasks, List<Drone> team, Integer version) {
		this.fromTo = new Pair<Drone, Drone>(null, null);
		this.team = new ArrayList<Drone>(team);
		this.availableTasks = new ArrayList<Task>(availableTasks);
		this.allocations = new ArrayList<Pair<Task, Drone>>();
		this.ch1 = new ArrayList<Pair<Task, Drone>>();
		this.version = Integer.valueOf(version);
		this.timeout = team.size() - 1;
		this.nextMasters = new ArrayList<Drone>();
		this.nextC2Approach = new Pair<C2Approach, Drone>(null, null);
		this.handShakeStatus = new ArrayList<Pair<Drone, Boolean>>();
	}
	
	public Token(Token other) {
		this.fromTo = new Pair<Drone, Drone>(other.fromTo.getFirst(), other.fromTo.getSecond());
		this.allocations = new ArrayList<Pair<Task, Drone>>(other.allocations);
		this.availableTasks = new ArrayList<Task>(other.availableTasks);
		this.team = new ArrayList<Drone>(other.team);
		this.version = Integer.valueOf(other.version);
		this.timeout = Integer.valueOf(other.timeout);
		this.nextC2Approach = new Pair<C2Approach, Drone>(other.nextC2Approach.getFirst(), other.nextC2Approach.getSecond());
		this.nextMasters = new ArrayList<Drone>(other.nextMasters);
		this.handShakeStatus = new ArrayList<Pair<Drone, Boolean>>(other.handShakeStatus);
		this.ch1 = new ArrayList<Pair<Task, Drone>>(other.ch1);
		this.ch3 = new ArrayList<Task>(other.getCh3());
	}
	
	public C2Approach getNextC2Approach() {
		return nextC2Approach.getFirst();
	}

	public void setNextC2Approach(C2Approach nextC2Approach) {
		this.nextC2Approach.setFirst(nextC2Approach);
	}
	
	public void setRequester(Drone drone) {
		this.nextC2Approach.setSecond(drone);
	}
	
	public Drone getRequester() {
		return this.nextC2Approach.getSecond();
	}

	public List<Drone> getNextMasters() {
		return nextMasters;
	}

	public void setNextMasters(List<Drone> nextMasters) {
		this.nextMasters = nextMasters;
	}

	public List<Pair<Drone, Boolean>> getHandshake() {
		return this.handShakeStatus;
	}
	
	public Boolean handShakeStatus() {
		if (this.handShakeStatus == null ) return false;
		for (Pair<Drone, Boolean> pair : this.handShakeStatus) {
			if (pair.getSecond().equals(false)) return false;
		}
		
		List<Drone> allHandShakedDrones = new ArrayList<Drone>();
		this.handShakeStatus.forEach(pair -> allHandShakedDrones.add(pair.getFirst()));
		List<Drone> notAnsweredDrones = new ArrayList<Drone>(this.team);
		notAnsweredDrones.removeAll(allHandShakedDrones);
		if (notAnsweredDrones.isEmpty()) {
			return true;
		} else {
			return null;
		}
	}

	public void addHandShake(Drone drone, Boolean status) {
		if (this.handShakeStatus == null) {
			this.handShakeStatus = new ArrayList<Pair<Drone, Boolean>>();
		}
		for (Pair<Drone, Boolean> pair : this.handShakeStatus) {
			if (pair.getFirst() == drone) {
				this.handShakeStatus.remove(pair);
				break;
			}
		}
		this.handShakeStatus.add(new Pair<Drone, Boolean>(drone, status));
	}

	public abstract C2Approach getC2Approach();

	public void setFromTo(Drone from, Drone to) {
		this.fromTo = new Pair<Drone, Drone>(from, to);
	}
	
	public Pair<Drone, Drone> getFromTo() {
		return this.fromTo;
	}
	
	public List<Drone> getTeam() {
		return this.team;
	}

	public List<Pair<Task, Drone>> getAllocations() {
		return this.allocations;
	}

	/**
	 * Remove task from all the lists of the token and set it as available.
	 * 
	 * @param task
	 */
	public void setTaskAsAvailable(Task task) {
		removeTask(task);
		this.availableTasks.add(task);
	}

	/**
	 * Get the list of available task, which weren't allocated or executed by a
	 * drone.
	 * 
	 * @return The list of available task, which weren't allocated or executed by a
	 *         drone.
	 */
	public List<Task> getAvailableTasks() {
		return this.availableTasks;
	}

	/**
	 * Remove task from all the lists of the token and set it as allocated by a
	 * drone.
	 * 
	 * @param task  Task which are allocated.
	 * @param drone Drone which allocated the task.
	 */
	public void setTaskAsAllocated(Task task, Drone drone) {
		removeTask(task);
		this.allocations.add(new Pair<Task, Drone>(task, drone));
	}

	public List<Task> dronesAllocatedTasks(Drone drone) {
		List<Task> tasks = new ArrayList<Task>();
		for (Pair<Task, Drone> relation : this.allocations) {
			if (relation.getSecond().equals(drone)) {
				tasks.add(relation.getFirst());
			}
		}
		return tasks;
	}

	public void setTaskAsCh1(Task task, Drone executor) {
		removeTask(task);
		this.ch1.add(new Pair<Task, Drone>(task, executor));
	}
	
	
	/**
	 * Remove task from available tasks, allocations, and executions.
	 * 
	 * @param task
	 */
	private void removeTask(Task task) {
		List<Pair<Task, Drone>> toRemove = new ArrayList<Pair<Task, Drone>>();
		for (Pair<Task, Drone> relation : this.allocations) {
			if (relation.getFirst().equals(task)) {
				toRemove.add(relation);
			}
		}
		for (Pair<Task, Drone> relation : this.ch1) {
			if (relation.getFirst().equals(task)) {
				toRemove.add(relation);
			}
		}
		this.allocations.removeAll(toRemove);
		this.ch1.removeAll(toRemove);
		this.availableTasks.remove(task);
	}

	public String information() {
		String information = getC2Approach() + " Token \n";
		information += "Available: " + this.availableTasks + "\n";
		
        // Allocations information;
		if(Parameters.getInstance().showAllocations()) {
			ArrayList<String> allocationsInformation = new ArrayList<String>();
			for (Pair<Task, Drone> allocation : this.allocations) {
				allocationsInformation.add("(" + allocation.getFirst() + ", " + allocation.getSecond() + ")");
			}
			information += "Allocations: " + allocationsInformation + "\n";
		}
		
		ArrayList<String> ch1Information = new ArrayList<String>();
		for (Pair<Task, Drone> pair : this.ch1) {
			ch1Information.add("(" + pair.getFirst() + ", " + pair.getSecond() + ")");
		}
		information += "Ch1: " + ch1Information + "\n";
		
		information += "V " + this.version + " \n";
		
		return information;
	}
	
	public int getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public void incrementVersion() {
		this.version = this.version + 1;
	}

	public Integer getTimeout() {
		return this.timeout;
	}

	public List<Pair<Task, Drone>> getCh1() {
		return this.ch1;
	}

	public List<Task> getCh3() {
		if(ch3 == null)
			ch3 = new ArrayList<Task>();
		return ch3;
	}

	public void setCh3(List<Task> ch3) {
		this.ch3 = ch3;
	}
	
}
