package entities;

import java.util.List;

import domain.C2Approach;

public class CoordinatedToken extends Token {
	
	private Drone master;
	
	public CoordinatedToken(List<Drone> drones, List<Task> availableTasks, Drone master, Integer version) {
		super(availableTasks, drones, version);
		super.setNextC2Approach(C2Approach.COORDINATED);
		this.master = master;
	}
	
	public CoordinatedToken(CoordinatedToken other) {
		super(other);
		this.master = other.master;
	}
		
	@Override
	public C2Approach getC2Approach() {
		return C2Approach.COORDINATED;
	}

	public Drone getMaster() {
		return master;
	}

	public String information() {
		String information = super.information();
//		information += "Master: " + this.master;
		return information;
	}
	
}