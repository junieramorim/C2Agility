package entities;

import java.util.List;

import domain.C2Approach;

public class ConflictedToken extends Token 	{
	
	public ConflictedToken(List<Drone> team, List<Task> availableTasks, Integer version) {
		super(availableTasks, team, version);
		super.setNextC2Approach(C2Approach.CONFLICTED);
	}
	
	public ConflictedToken(ConflictedToken other) {
		super(other);
	}

	@Override
	public C2Approach getC2Approach() {
		return C2Approach.CONFLICTED;
	}

	
}
