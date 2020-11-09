package entities;

import java.util.List;

import domain.C2Approach;

public class EdgeToken extends Token {

	public EdgeToken(List<Drone> team, List<Task> availableTasks, Integer version) {
		super(availableTasks, team, version);
		super.setNextC2Approach(C2Approach.EDGE);
	}
	
	public EdgeToken(EdgeToken other) {
		super(other);
	}

	@Override
	public C2Approach getC2Approach() {
		return C2Approach.EDGE;
	}

}
