package entities.chart;

import repast.simphony.statecharts.*;
import repast.simphony.parameter.Parameters;
import static repast.simphony.random.RandomHelper.*;
import repast.simphony.statecharts.generator.GeneratedFor;

import static repast.simphony.essentials.RepastEssentials.*;

import entities.*;

/**
 * Guard for Transition 137, from = Binding, to = Ready.
 */
@GeneratedFor("_vnYvAJciEeqUuepXYUrgtg")
public class SC3Guard8 implements GuardCondition<Drone> {
	@Override
	public boolean condition(Drone agent, Transition<Drone> transition, Parameters params) throws Exception {
		return (agent.isTaskAllocator() != null) && (agent.getTokenState() == agent.getTokenState().NOT_RECEIVED);

	}
}
