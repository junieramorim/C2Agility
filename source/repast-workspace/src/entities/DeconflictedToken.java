package entities;

import java.util.List;

import domain.C2Approach;

public class DeconflictedToken extends Token {

	private Drone c2ApproachSelector;
	private Integer nTasksPerRound;

	/**
	 * Token constructor which take drones and tasks to fill unvisited drones list
	 * and available tasks list respectively. The lists of visited drones,
	 * allocations and executions are initialized as empty lists.
	 * 
	 * @param unvisitedDrones List of drones which didn't receive the token yet.
	 * @param availableTasks  List of available tasks that drones must execute.
	 */
	public DeconflictedToken(List<Drone> drones, List<Task> availableTasks, Integer version, Drone c2ApproachSelector) {
		super(availableTasks, drones, version);
		super.setNextC2Approach(C2Approach.DECONFLICTED);
		this.c2ApproachSelector = c2ApproachSelector;
		this.nTasksPerRound = 1;
	}

	/**
	 * Make a clone of the token, cloning the internal lists and parameters, but not
	 * cloning the internal objects of the lists.
	 */
	public DeconflictedToken(DeconflictedToken other) {
		super(other);
		this.nTasksPerRound = other.nTasksPerRound;
	}

	public Drone getC2ApproachSelector() {
		return this.c2ApproachSelector;
	}

	@Override
	public C2Approach getC2Approach() {
		return C2Approach.DECONFLICTED;
	}

	@Override
	public String information() {
		String information = super.information();
//		information += "Timeout: " + this.timeout + "\n";
//		information += "nTasksPerRound: " + this.nTasksPerRound + "\n";
		return information;
	}

}