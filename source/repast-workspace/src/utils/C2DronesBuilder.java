/**
 * 
 */
package utils;

import java.util.List;

import controller.ContextController;
import controller.DynamicChange;
import domain.C2Network;
import entities.CollaborativeToken;
import entities.Drone;
import entities.Task;
import entities.Token;
import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;

public class C2DronesBuilder implements ContextBuilder<Object> {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see repast.simphony.dataLoader.ContextBuilder#build(repast.simphony.context.
	 * Context)
	 */
	
	private String inputPath = "/Users/junier/git/PIBIC-C2/";
	
	@Override
	public Context<Object> build(Context<Object> context) {
		context.setId("C2Drones");
		System.out.println(" INITIALIZING ...");
		this.setRunEnvironment();
		Factory factory = createFactory(context);
		C2Network.getInstance().setContext(context); // (This needs to happen after factory creates space).
		List<Drone> drones = null;
		List<Task> tasks = null;
		if(Parameters.getInstance().changes().equals("FILE")) {
			List<String> lines = InputInformation.getInstance().readFileByLine(inputPath+Parameters.getInstance().fileName());
			List<Scenario> scenarios = InputInformation.getInstance().linesToScenarios(lines);
			drones = factory.createDrones(scenarios.get(Parameters.getInstance().scenario() - 1).getMembers());
			tasks = factory.createTasks(scenarios.get(Parameters.getInstance().scenario() - 1).getTasks());
		}else {
			drones = factory.createDrones();
			tasks = factory.createTasks();
		}
		factory.prepareFiles();
		
		//defining context changes
		List<DynamicChange> dynamicChanges = null;
		if(Parameters.getInstance().changes().equals("RANDOM"))
			dynamicChanges = factory.createDynamicChanges(drones);
		else {
			if(!Parameters.getInstance().fileName().equals("")){
				List<String> lines = InputInformation.getInstance().readFileByLine(inputPath+Parameters.getInstance().fileName());
				List<Scenario> scenarios = InputInformation.getInstance().linesToScenarios(lines);
				dynamicChanges = factory.createDynamicChanges(drones, scenarios, Parameters.getInstance().scenario() - 1);
			} else {
				dynamicChanges = factory.createDynamicChanges(drones, InputInformation.getInstance().getActions());
			}
		}
		//block end
		
		Token token = factory.createToken(drones, tasks);
		sendToken(token, drones);
		ContextController contextController = new ContextController(drones, dynamicChanges);
		contextController.setnMembers(drones.size());
		contextController.setnTasks(tasks.size());
		context.add(contextController);
		return context;
	}

	private void setRunEnvironment() {
		RunEnvironment.getInstance().endAt(Parameters.getInstance().simulationTime());
		RunEnvironment.getInstance().setScheduleTickDelay(50);
	}

	private Factory createFactory(Context<Object> context) {
		return new RandomFactory(context);
	}

	private void sendToken(Token token, List<Drone> drones) {
		switch (token.getC2Approach()) {
		case CONFLICTED:
		case DECONFLICTED:
		case COORDINATED:
			drones.get(0).receiveToken(token);
			break;
		case COLLABORATIVE:
			((CollaborativeToken) token).getMasters().forEach(master -> master.receiveToken(token));
			break;
		case EDGE:
			drones.forEach(d -> d.receiveToken(token));
			break;
		}
	}
}
