package entities;

import repast.simphony.space.continuous.NdPoint;
import utils.Factory;
import utils.Parameters;

/** Class that represent the task which the drones should execute. */
public class Task {

	static public final int N_TYPES = 5;

	/** Task identifier. */
	private int id;

	/**
	 * Type of the task, which only the drones with the respective sensor type can
	 * do this task.
	 */
	private int type;

	/** Energy cost remmaing to accomplish the task. */
	private int cost;

	private NdPoint position;
	
	private double qualityResolution;

	
	/**
	 * @return the qualityResolution
	 */
	public double getQualityResolution() {
		return qualityResolution;
	}


	/**
	 * @param qualityResolution the qualityResolution to set
	 */
	public void setQualityResolution(double qualityResolution) {
		this.qualityResolution = qualityResolution;
	}


	/**
	 * Get the initial cost to accomplish a task given the type of the task.
	 * 
	 * @param type Task's type.
	 * @return Initial cost to accomplish the task given it's type.
	 */
	private static int initialCost(int type) {
		return (type + 9) * 10;
	}


	/**
	 * Task constructor, which specifies the space where the task exists and it's
	 * type. Note: the task doesn't position itself in the space.
	 * 
	 * @param id    Task identifier
	 * @param space Space where the task exists.
	 * @param type  Type of the task, which only the drones with the respective
	 *              sensor type can do this task.
	 */
	public Task(int id, int type) {
		this.id = id;
		this.type = type;
		this.cost = initialCost(type);
	}

	/**
	 * Get task identifier.
	 * 
	 * @return Task identifier.
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Get the type of the task.
	 * 
	 * @return Type of the task.
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * Get the energy cost to accomplish the task.
	 * 
	 * @return Energy cost to accomplish the task.
	 */
	public int getCost() {
		return this.cost;
	}

	/**
	 * Get the position of the task in the space.
	 * 
	 * @return Position of the task in the space, represented by a n-dimension
	 *         point.
	 */
	public NdPoint getPosition() {
		if (this.position == null) {
			this.position = Factory.getSpace().getLocation(this);
		}
		return this.position;
	}

	/**
	 * Get the string responsable to generate task information in space display,
	 * used by Repast framework.
	 * 
	 * @return String with sensor information and fuel.
	 */
	public String information() {
		String information = "";
		if(Parameters.getInstance().lightScreen()) return information;
		
		information += "Id: " + this.id + "\n";
		information += "T: " + this.type + "\n";
		return information;
	}

	@Override
	public String toString() {
		return "" + this.id;
	}

}
