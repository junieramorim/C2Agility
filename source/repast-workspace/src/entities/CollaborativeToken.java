package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import domain.C2Approach;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;

public class CollaborativeToken extends Token{
	
	private Map<Drone, List<Drone>> coordenations;
	
	public CollaborativeToken(List<Drone> team, List<Task> availableTasks, List<Drone> masters, Integer version) {
		super(availableTasks, team, version);
		super.setNextC2Approach(C2Approach.COLLABORATIVE);
		this.coordenations = new HashMap<Drone, List<Drone>>();
		setCoordenations(masters, team);
	}
	
	public CollaborativeToken(CollaborativeToken other) {
		super(other);
		this.coordenations = new HashMap<Drone, List<Drone>>(other.coordenations);
	}
	
	private void setCoordenations(List<Drone> masters, List<Drone> team) {
		List<Drone> slaves = new ArrayList<Drone>(team);
		slaves.removeAll(masters);
		for (Drone master : masters) {
			this.coordenations.put(master, new ArrayList<Drone>());
		}
		for (int slave = 0; slave < slaves.size(); ++slave) {
			Drone master = masters.get(slave % masters.size());
			this.coordenations.get(master).add(slaves.get(slave));
		}
	}
	
	public Set<Drone> getMasters() {
		return this.coordenations.keySet();
	}
	
	public List<Drone> slavesOf(Drone master) {
		if (this.getMasters().contains(master)) {
			return this.coordenations.get(master);			
		} else {
			return new ArrayList<Drone>(); 			
		}
	}
	
	public Drone masterOf(Drone slave) {
		for (Drone master : this.coordenations.keySet()) {
			if (this.coordenations.get(master).contains(slave)) {
				return master;
			}
		}
		return null;
	}
	

	@Override
	public C2Approach getC2Approach() {
		return C2Approach.COLLABORATIVE;
	}

}
