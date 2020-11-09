package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import entities.Drone;
import entities.Task;
import entities.Token;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import utils.Parameters;
import utils.ResultsReport;

public class ContextController {

	private List<Drone> drones;
	private List<DynamicChange> dynamicChanges;
	private List<Task> fb; //Channel used by the system to register successfully tasks
	private int nMembers;
	private int nTasks;

	/**
	 * @return the nMembers
	 */
	public int getnMembers() {
		return nMembers;
	}

	/**
	 * @param nMembers the nMembers to set
	 */
	public void setnMembers(int nMembers) {
		this.nMembers = nMembers;
	}

	/**
	 * @return the nTasks
	 */
	public int getnTasks() {
		return nTasks;
	}

	/**
	 * @param nTasks the nTasks to set
	 */
	public void setnTasks(int nTasks) {
		this.nTasks = nTasks;
	}

	public ContextController(List<Drone> drones, List<DynamicChange> events) {
		this.drones = drones;
		this.dynamicChanges = events;
	}
	
	private List<Task> getFb(){
		if(fb == null)
			fb = new ArrayList<Task>();
		return this.fb;
	}
	
	private int fbSize() {
		List<Integer> index = new ArrayList<Integer>();
		for(Task t : getFb()) {
			if(!index.contains(t.getID()))
				index.add(t.getID());
		}
		return index.size();
	}

	@ScheduledMethod(start = 1, interval = 1)
	public void run() {
		int tickCount = (int) RunEnvironment.getInstance().getCurrentSchedule().getTickCount();
		Object dynamismResult = null;
		Token token = null;
		
		// Run dynamic changes
		for (DynamicChange dynamicChange : this.dynamicChanges) {
			try {
				dynamismResult = dynamicChange.occur(tickCount); 
			} catch (Exception e) {
			}
		}

		List<Drone> dronesWithToken = new ArrayList<Drone>();
		for (Drone d : this.drones) {
			if (d.hasTokenToSend()) {
				dronesWithToken.add(d);
			} else {
				d.step();
			}
		}
		for (Drone d : dronesWithToken) {
			if(dynamismResult != null && (dynamismResult instanceof List<?>) && (d.isTaskAllocator()!=null)) {
				List<Task> newTasks = (List<Task>)dynamismResult;
				d.isTaskAllocator().getNewTasks().addAll(newTasks);
			}
			d.step();
			token = d.getToken();
			//Simulating each member writing successfully result in the FB channel
			if(d.isExecutor()!=null && d.isExecutor().getFinishedTask()!=null) {
				getFb().add(d.isExecutor().getFinishedTask()); //In the model, it was replaced by the channel CH1 that writes <task, null> in case of successful execution
				d.isExecutor().setFinishedTask(null);
			}
		}
		//Finish the report file with general data
		if(tickCount == Parameters.getInstance().simulationTime()) {
			int totalReconfigurations = 0;
			int totalManouvers = 0;
			int time = 0;
			double reward = 0;
			for(Drone d : this.drones) {
				totalReconfigurations = totalReconfigurations + d.getReconfigurations();
				totalManouvers = totalManouvers + d.getManeuvers();
				if(d.isExecutor()!=null && d.isExecutor().getOperationTime() > time)
						time = d.isExecutor().getOperationTime();
				reward = reward + d.getReward();
			}
			
			DecimalFormat df = new DecimalFormat("###.#");
			
			ResultsReport.getInstance().registerMetric("M1", Parameters.getInstance().path()+String.valueOf(Parameters.getInstance().scenario())+"/", String.valueOf(totalReconfigurations));
			ResultsReport.getInstance().registerMetric("M2", Parameters.getInstance().path()+String.valueOf(Parameters.getInstance().scenario())+"/", String.valueOf(totalManouvers));
			ResultsReport.getInstance().registerMetric("M3", Parameters.getInstance().path()+String.valueOf(Parameters.getInstance().scenario())+"/", String.valueOf(time));
			ResultsReport.getInstance().registerMetric("M5", Parameters.getInstance().path()+String.valueOf(Parameters.getInstance().scenario())+"/", String.valueOf(df.format(((double)fbSize()*100.0)/(double)this.nTasks)));
			ResultsReport.getInstance().registerMetric("M6", Parameters.getInstance().path()+String.valueOf(Parameters.getInstance().scenario())+"/", String.valueOf(df.format(reward)));
			
			ResultsReport.getInstance().writeFile(ResultsReport.typeFiles.RUNNING.name(), true, "M1 (Total reconfigurations): "+totalReconfigurations+"\n");
			ResultsReport.getInstance().writeFile(ResultsReport.typeFiles.RUNNING.name(), true, "M2 (Maneuvers): "+totalManouvers+"\n");
			ResultsReport.getInstance().writeFile(ResultsReport.typeFiles.RUNNING.name(), true, "M3 (Timeliness): "+time+"/"+Parameters.getInstance().simulationTime()+"\n");
			ResultsReport.getInstance().writeFile(ResultsReport.typeFiles.RUNNING.name(), true, "M4 (Resilience -> Effectiveness(scenario)): "+"\n");
			ResultsReport.getInstance().writeFile(ResultsReport.typeFiles.RUNNING.name(), true, "M5 (Effectiveness %): "+df.format(((double)fbSize()*100.0)/(double)this.nTasks)+"\n");
			ResultsReport.getInstance().writeFile(ResultsReport.typeFiles.RUNNING.name(), true, "+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n");
		}
	}
}
