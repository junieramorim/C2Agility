package entities.chart;

import repast.simphony.statecharts.*;
import repast.simphony.parameter.Parameters;
import static repast.simphony.random.RandomHelper.*;
import repast.simphony.statecharts.generator.GeneratedFor;

import static repast.simphony.essentials.RepastEssentials.*;

import entities.*;

/**
 * Guard for Transition 7, from = Ready, to = Allocating.
 */
@GeneratedFor("_vnYvAJciEeqUuepXYUrgtg")
public class SC3Guard2 implements GuardCondition<Drone> {
	@Override
	public boolean condition(Drone agent, Transition<Drone> transition, Parameters params) throws Exception {
		return (agent.isTaskAllocator() != null) && (agent.isTokenUpdated())
				&& (agent.getToken().getAvailableTasks() != null) && (agent.getToken().getAvailableTasks().size() > 0)
				&& (agent.isTaskAllocator().isAllocating());

	}
}
