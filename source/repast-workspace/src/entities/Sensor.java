package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.Parameters;
import utils.RandomFactory;

/**
 * Sensor which is used by the Drone to execute tasks.
 */
public class Sensor {

	/**
	 * Enum of the types a sensor can be, which determines the initial qualities to
	 * execute the different type of existing tasks.
	 */
	static public enum Type {
		A, B, C, D, E
	}

	private Type type;
	private List<Double> qualities;
	private boolean enable;

	/**
	 * Construct a new sensor, set as disable, informing the type.
	 * 
	 * @param type Type of the new sensor.
	 */
	public Sensor(Type type) {
		this.type = type;
		this.enable = false;
		this.qualities = initialQualities(type);
	}

	/**
	 * Get initial qualities of sensor mapped to all tasks by it's type. The
	 * maximmum value of a qualitie is 1 and the minimmum is 0.
	 * 
	 * @return An array representing the initial qualities of a sensor to perform
	 *         the tasks ordered by task type.
	 */
	private List<Double> initialQualities(Sensor.Type type) {
		Double[][] qualitiesMatrix = {
				// 0 || 1 || 2 || 3 || 4 || Task.Type / Sensor.Type
				{ 1.0, 0.5, 0.3, 0.0, 0.0 }, // A
				{ 0.0, 1.0, 0.8, 0.0, 0.0 }, // B
				{ 0.0, 0.5, 1.0, 0.2, 0.0 }, // C
				{ 0.5, 0.0, 0.0, 1.0, 0.0 }, // D
				{ 0.0, 0.0, 0.0, 0.0, 1.0 }, // E
		};
		return new ArrayList<Double>(Arrays.asList(qualitiesMatrix[type.ordinal()]));
	}

	/**
	 * Get the sensor type.
	 * 
	 * @return Sensor type.
	 */
	public Type getType() {
		return this.type;
	}

	/**
	 * Set new qualities to perform each task by type, ordered by the type of the
	 * tasks. For example: qualities[1] is the performace of the sensor to execute
	 * tasks of type 1;
	 * 
	 * @param qualities New qualities to perform each task by type.
	 */
	public void setQualities(List<Double> qualities) {
		this.qualities = qualities;
	}
	
	/**
	 * @return
	 */
	public List<Double> getQualities(){
		return this.qualities;
	}

	/**
	 * Get the quality to execute the task.
	 * 
	 * @param task Task to be executed.
	 * @return Quality to execute the task.
	 */
	public double qualityToTask(Task task) {
		return this.qualities.get(task.getType());
	}

	/**
	 * Set the new status of the sensor as enable or disable
	 * 
	 * @param isEnable true to set the sensor as enable, false otherwise.
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * Get status of the sensor.
	 * 
	 * @return true if the sensor is enable, false otherwise.
	 */
	public boolean isEnable() {
		return this.enable;
	}
	
	private boolean getIsBroken() {
		for (Double quality : this.qualities) {
			if (quality >  ((double) Parameters.getInstance().threshold()) / 100) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return (this.enable ? "+" : "") + (this.getIsBroken() ? "-" : "") +  this.type;
	}
	
	public void updateQualities(double delta) {
		List<Double> newQualities = new ArrayList<Double>();
		for(Double d : qualities) 
			newQualities.add(d*delta);
		this.qualities = newQualities;
		}

}
