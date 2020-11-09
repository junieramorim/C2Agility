package domain;

import java.util.Iterator;

import entities.Drone;
import utils.Factory;
import repast.simphony.context.Context;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.space.graph.Network;
import repast.simphony.space.graph.RepastEdge;

public class C2Network {

	static private C2Network instance = null;
	private Network<Object> net;

	private C2Network() {
	}

	static public C2Network getInstance() {
		if (instance == null) {
			instance = new C2Network();
		}
		return instance;
	}
	
	public void setContext(Context<Object> context) {
		NetworkBuilder<Object> netBuilder = new NetworkBuilder<Object>("C2 Network", Factory.getContext(), true);
		netBuilder.buildNetwork();
		this.net = (Network<Object>) Factory.getContext().getProjection("C2 Network");
	}

	public void addEdge(Drone sender, Drone receiver) {
		net.addEdge(sender, receiver);
	}
	
	public void removeEdge(Drone sender, Drone receiver) {
		net.removeEdge(net.getEdge(sender, receiver));
	}
	
	public void removeEdges() {
		net.removeEdges();
	}

	public void removeSourceNode(Drone drone) {
		Iterator<Object> source = this.net.getAdjacent(drone).iterator();
		while (source.hasNext()) {
			RepastEdge<Object> edge = this.net.getEdge(drone, source.next());
			this.net.removeEdge(edge);
		}
	}

	public void removeDrone(Drone drone) {
		Iterator<RepastEdge<Object>> it = this.net.getEdges(drone).iterator();
		while (it.hasNext()) {
			RepastEdge<Object> edge = it.next();
			this.net.removeEdge(edge);
		}
	}
}
