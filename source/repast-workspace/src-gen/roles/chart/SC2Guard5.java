package roles.chart;

import repast.simphony.statecharts.*;
import repast.simphony.parameter.Parameters;
import static repast.simphony.random.RandomHelper.*;
import repast.simphony.statecharts.generator.GeneratedFor;

import static repast.simphony.essentials.RepastEssentials.*;

import entities.*;

/**
 * Guard for Transition 60, from = Running, to = Reporting.
 */
@GeneratedFor("_fiV1gJSaEeqUuepXYUrgtg")
public class SC2Guard5 implements GuardCondition<Drone> {
	@Override
	public boolean condition(Drone agent, Transition<Drone> transition, Parameters params) throws Exception {
		return (agent.isExecutor() != null) && (agent.isExecutor().getFinishedTask() != null);

	}
}
