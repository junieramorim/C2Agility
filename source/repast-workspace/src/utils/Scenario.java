package utils;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
	
	private Long id;
	
	private int members;
	
	private int tasks;
	
	private List<String> actions;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the members
	 */
	public int getMembers() {
		return members;
	}

	/**
	 * @param members the members to set
	 */
	public void setMembers(int members) {
		this.members = members;
	}

	/**
	 * @return the tasks
	 */
	public int getTasks() {
		return tasks;
	}

	/**
	 * @param tasks the tasks to set
	 */
	public void setTasks(int tasks) {
		this.tasks = tasks;
	}

	/**
	 * @return the actions
	 */
	public List<String> getActions() {
		if(actions == null)
			actions = new ArrayList<String>();
		return actions;
	}

	/**
	 * @param actions the actions to set
	 */
	public void setActions(List<String> actions) {
		this.actions = actions;
	}

}
