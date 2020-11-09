package entities.chart;

import repast.simphony.statecharts.*;
import repast.simphony.parameter.Parameters;
import static repast.simphony.random.RandomHelper.*;
import repast.simphony.statecharts.generator.GeneratedFor;

import static repast.simphony.essentials.RepastEssentials.*;

import entities.*;

/**
 * Guard for Transition 4, from = Operating, to = Maneuvering.
 */
@GeneratedFor("_NQ9TUJpBEeqqQIEemyx-2w")
public class SC1Guard2 implements GuardCondition<Drone> {
	@Override
	public boolean condition(Drone agent, Transition<Drone> transition, Parameters params) throws Exception {
		return (agent.isC2ApproachSelector() != null) && (agent.isTokenUpdated())
				&& (agent.getToken().getCh3() != null);

	}
}
