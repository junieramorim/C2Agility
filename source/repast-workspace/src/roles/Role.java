package roles;

import entities.Drone;
import entities.Token;

public abstract class Role {
	
	public abstract void step(Drone drone, Token token);
	
	public abstract String information();
	
	public abstract int getOrder();
	
	public abstract String getAbbreviation();
	
}
