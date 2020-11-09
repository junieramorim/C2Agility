package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.ejml.data.IScalar;

import domain.C2Approach;
import domain.C2Network;
import domain.Treatment;
import entities.chart.C2A;
import entities.chart.TA;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.ui.probe.ProbedProperty;
import repast.simphony.util.collections.Pair;
import roles.C2ApproachSelector;
import roles.Executor;
import roles.Role;
import roles.TaskAllocator;
import roles.chart.EX;
import utils.Factory;
import utils.Parameters;
import utils.ResultsReport;

public class Drone {

	private Integer id;

	private List<Sensor> sensors;
	
	private int reconfigurations = 0;
	
	private int maneuvers = 0;
	
	private double reward = 0;

	private Integer fuel;

	private Integer speed = 1;

	private C2Approach currentC2Approach;
	
	private Map<Integer, Role> roles;

	private Token token;
	
	public enum TokenState {
		RECEIVED_UPDATED, RECEIVED_OUTDATED, NOT_RECEIVED
	};

	private TokenState tokenState;

	private Double timeStamp;
	
	@ProbedProperty(displayName="EX")
	EX eX = null;

	@ProbedProperty(displayName="TA")
	TA tA = null;

	@ProbedProperty(displayName="C2A")
	C2A c2A = null;
	
	
	public String getC2AState(){
		if (c2A == null) return "";
		Object result = c2A.getCurrentSimpleState();
		return result == null ? "" : result.toString();
	}
	
	public String getTAState(){
		if (tA == null) return "";
		Object result = tA.getCurrentSimpleState();
		return result == null ? "" : result.toString();
	}
	
	public String getEXState(){
		if (eX == null) return "";
		Object result = eX.getCurrentSimpleState();
		return result == null ? "" : result.toString();
	}


	public Drone(Integer id, List<Sensor> sensors, Integer fuel) {
		this.id = id;
		this.sensors = sensors;
		this.fuel = fuel;
		this.speed = 1;
		this.tokenState = TokenState.NOT_RECEIVED;
	}

	public TokenState getTokenState() {
		return this.tokenState;
	}

	public List<Sensor> getSensors() {
		return this.sensors;
	}

	public Integer getFuel() {
		return this.fuel;
	}

	public Integer getSpeed() {
		return this.speed;
	}

	public Boolean ping() {
		return getFuel() != 0;
	}

	public NdPoint position() {
		return Factory.getSpace().getLocation(this);
	}

	public Sensor bestSensorToTask(Task task) {
		Sensor chosen = this.sensors.size() > 0 ? this.sensors.get(0) : null;
		if (Parameters.getInstance().agilityMethod().equals(Treatment.NOC2AGILITY) || Parameters.getInstance().agilityMethod().equals(Treatment.ONLYC2MENEUVER)) {
			for(Sensor s: this.sensors)
				if(s.isEnable()) {
					chosen = s;
					break;
				}
		}else {
			for (Sensor s : this.sensors) {
				chosen = chosen.qualityToTask(task) >= s.qualityToTask(task) ? chosen : s;
			}
		}
//		if(task == null)
//			return chosen;
//		if (!Parameters.getInstance().agilityMethod().equals(Treatment.NOC2AGILITY) && !Parameters.getInstance().agilityMethod().equals(Treatment.ONLYC2MENEUVER)) {
//			
//		}
		return chosen;
	}
	
	public Sensor getActiveSensor() {
		for(Sensor s : getSensors())
			if(s.isEnable())
				return s;
		return null;
	}

	public Integer allocatedFuel(Token token) {
		NdPoint lastPoint = this.position();
		int totalFuel = 0;
		for (Task t : token.dronesAllocatedTasks(this)) {
			totalFuel += t.getCost()
					+ (int) Math.ceil(Factory.getSpace().getDistance(lastPoint, t.getPosition()) / this.getSpeed());
			lastPoint = t.getPosition();
		}
		Integer executionCount = (this.isExecutor() != null ? this.isExecutor().getExecutionCount() : 0);
		totalFuel = totalFuel - executionCount;
		return totalFuel;
	}

	public void drop() {
		this.fuel = 0;
		C2Network.getInstance().removeDrone(this);
	}

	public Boolean hasTokenToSend() {
		return tokenState != TokenState.NOT_RECEIVED || hasTimedOut();
	}
	
	private Map<Integer, Role> getRoles() {
		if(this.roles == null)
			this.roles = new TreeMap<Integer, Role>();
		return this.roles;
	}
	
	public Token getToken() {
		return token;
	}

	public Executor isExecutor(){
		for(Role r: getRoles().values()) {
			if(r instanceof Executor)
				return (Executor)r;
		}
		return null;
	}
	
	public TaskAllocator isTaskAllocator(){
		for(Role r: getRoles().values()) {
			if(r instanceof TaskAllocator)
				return (TaskAllocator)r;
		}
		return null;
	}
	
	public C2ApproachSelector isC2ApproachSelector(){
		for(Role r: getRoles().values()) {
			if(r instanceof C2ApproachSelector)
				return (C2ApproachSelector)r;
		}
		return null;
	}
		
	// Drone's should receive token if it's doesn't have a token yet.
	// If drone is in the same C2Approach as the received token, then accept the token if the version is updated.
	// If drone is in a different C2Approach as the received token, then accept the token if it's C2Approach is the same of what was expected.
	public void receiveToken(Token received) {
		if (this.token == null
				|| received.getC2Approach() == this.currentC2Approach && received.getVersion() >= this.token.getVersion()
				|| received.getC2Approach() != this.currentC2Approach && this.token.getNextC2Approach() == received.getC2Approach()) {
			this.tokenState = TokenState.RECEIVED_UPDATED;
			if (this.currentC2Approach != received.getC2Approach()) {
				this.currentC2Approach = received.getC2Approach();
				instantiateRoles(received);
				this.token = null;
			}
			mergeToken(received);
		} else {
			this.tokenState = TokenState.RECEIVED_OUTDATED;
		}
	}

	public void answerC2ApproachSelector() {
		if (this.currentC2Approach != token.getNextC2Approach()) {		
			this.token.addHandShake(this, true);
		}
	}
	
	public C2Approach getCurrentC2Approach() {
		return currentC2Approach;
	}

	public void setCurrentC2Approach(C2Approach currentC2Approach) {
		this.currentC2Approach = currentC2Approach;
	}

	public void step() {
		if (this.fuel == 0) {
			drop();
			return;
		}
		if (tokenState == TokenState.RECEIVED_UPDATED) {
			
			//ResultsReport.getInstance().writeFile(ResultsReport.typeFiles.PARTIAL.name(), true, "===> e: "+this.getId() + " ---- time: " + (int) RunEnvironment.getInstance().getCurrentSchedule().getTickCount() +"\n");
			
			answerC2ApproachSelector();
			
			//Steps execution in case of not being Handshake operation
			for(int r = 1; r <= 3; r++) {
				if (getRoles().get(r) != null) {
					if(isExecutor() != null) {
						this.reconfigurations = this.reconfigurations + isExecutor().getReconfigurations();
						isExecutor().setReconfigurations(0);
						this.reward = this.reward + isExecutor().getReward();
						isExecutor().setReward(0);
					}
					if(isC2ApproachSelector() != null) {
						this.maneuvers = this.maneuvers + isC2ApproachSelector().getManouvers();
						isC2ApproachSelector().setManouvers(0);
					}
					getRoles().get(r).step(this, this.token);
				}
			}
			this.token.incrementVersion();
			sendToken();
		} else {
			for(Role r : getRoles().values())
				r.step(this, null);
			if (tokenState == TokenState.RECEIVED_OUTDATED || hasTimedOut()) {
				sendToken();
			}
		}
		this.fuel = this.fuel - 1;
	}

	private void sendToken() {
		this.timeStamp = RunEnvironment.getInstance().getCurrentSchedule().getTickCount();
		
		C2Network.getInstance().removeSourceNode(this);
		tokenState = TokenState.NOT_RECEIVED;
		for (Token sendedToken : tokensToSend()) {
			Drone receiver = sendedToken.getFromTo().getSecond();
			receiver.receiveToken(sendedToken);
			C2Network.getInstance().addEdge(this, receiver);
			//remove old edge
			Drone oldSender = sendedToken.getFromTo().getFirst();
			C2Network.getInstance().removeEdge(oldSender, this);
		}
		
	}

	private Boolean hasTimedOut() {
		int tickCount = (int) RunEnvironment.getInstance().getCurrentSchedule().getTickCount();
		return this.token != null && tickCount > this.timeStamp + this.token.getTimeout();
	}

	// Switches
	// ////////////////////////////////////////////////////////////////////////////////////////////
	
	private void instantiateRoles(Token received) {
		switch (received.getC2Approach()) {
		case CONFLICTED:
			instantiateRoles((ConflictedToken) received);
			return;
		case DECONFLICTED:
			instantiateRoles((DeconflictedToken) received);
			return;
		case COORDINATED:
			instantiateRoles((CoordinatedToken) received);
			return;
		case COLLABORATIVE:
			instantiateRoles((CollaborativeToken) received);
			return;
		case EDGE:
			instantiateRoles((EdgeToken) received);
			return;
		}
	}

	private void mergeToken(Token received) {
		switch (this.currentC2Approach) {
		case CONFLICTED:
			mergeToken((ConflictedToken) received);
			return;
		case DECONFLICTED:
			mergeToken((DeconflictedToken) received);
			return;
		case COORDINATED:
			mergeToken((CoordinatedToken) received);
			return;
		case COLLABORATIVE:
			mergeToken((CollaborativeToken) received);
			return;
		case EDGE:
			mergeToken((EdgeToken) received);
			return;
		}
	}

	private List<Token> tokensToSend() {
		switch (this.currentC2Approach) {
		case CONFLICTED:
			return tokensToSend((ConflictedToken) this.token);
		case DECONFLICTED:
			return tokensToSend((DeconflictedToken) this.token);
		case COORDINATED:
			return tokensToSend((CoordinatedToken) this.token);
		case COLLABORATIVE:
			return tokensToSend((CollaborativeToken) this.token);
		case EDGE:
			return tokensToSend((EdgeToken) this.token);
		}
		return new ArrayList<Token>();
	}
	
	// Conflicted /////////////////////////////////////////////////////////////////////////////////
	
	private void instantiateRoles(ConflictedToken received) {
		if (this.isC2ApproachSelector() != null) {
			getRoles().remove(this.isC2ApproachSelector().getOrder());
			//c2A = null;
		}
		if(this.isTaskAllocator() == null) {
			addRoles(new TaskAllocator());
			//c2A = C2A.createStateChart(this, 0);
		}
		if(this.isExecutor() == null) {
			addRoles(new Executor());
			//eX = EX.createStateChart(this, 0);
		}
	}
	
	private void mergeToken(ConflictedToken token) {
		this.token = new ConflictedToken(token);
	}
	
	private List<Token> tokensToSend(ConflictedToken token) {
		List<Token> tokensToSend = new ArrayList<Token>();
		for (Drone teammate : token.getTeam()) {
			ConflictedToken slice = new ConflictedToken(token);
			slice.setFromTo(this, teammate);
			tokensToSend.add(slice);
			
			// Clear team to sliced token.
			slice.getTeam().clear();
			slice.getTeam().add(teammate);
			
			// Sliced token only has allocation information about teammate.
			slice.getAllocations().clear();
			token.dronesAllocatedTasks(teammate).forEach(t -> slice.setTaskAsAllocated(t, teammate));			
		}
		return tokensToSend;
	}

	// Deconflicted //////////////////////////////////////////////////////////////////////////////////////////

	private List<Token> tokensToSend(DeconflictedToken token) {
		List<Token> tokensToSend = new ArrayList<Token>();
		int candidateIndex = token.getTeam().indexOf(this);
		while (tokensToSend.isEmpty()) {
			candidateIndex = (candidateIndex + 1) % token.getTeam().size(); // in case of working as a ring
			Drone candidate = token.getTeam().get(candidateIndex);
			if (candidate.ping()) {
				Token tokenToSend = new DeconflictedToken(token);
				tokenToSend.setFromTo(this, candidate);
				tokensToSend.add(tokenToSend);
			} else {
				this.isTaskAllocator().refillTasksFromDrone(token, candidate); // Recover tasks from dropped drone
				token.getTeam().remove(candidate);
			}
		}
		return tokensToSend;
	}
	
	private void addRoles(Role r) {
		getRoles().put(r.getOrder(), r);
	}

	private void instantiateRoles(DeconflictedToken received) {
		if(this == received.getC2ApproachSelector() && this.isC2ApproachSelector() == null) {
			addRoles(new C2ApproachSelector());
			//c2A = C2A.createStateChart(this, 0);
		} else if (this != received.getC2ApproachSelector() && this.isC2ApproachSelector() != null) {
			getRoles().remove(this.isC2ApproachSelector().getOrder());
			//c2A = null;
		}
		if(this.isTaskAllocator() == null) {
			addRoles(new TaskAllocator());
			//tA = TA.createStateChart(this, 0);
		}
		if(this.isExecutor() == null) {
			addRoles(new Executor());
			//eX = EX.createStateChart(this, 0);
		}
	}

	private void mergeToken(DeconflictedToken received) {
		this.token = new DeconflictedToken(received);
	}

	// Coordinated ////////////////////////////////////////////////////////////////////////////////////////////

	private void mergeToken(CoordinatedToken received) {
		if ((this == received.getMaster() && this.token == null) || this != received.getMaster()) { //token received from the coordinator
			this.token = new CoordinatedToken(received);
		} else {
			received.getCh1().forEach(t -> this.token.setTaskAsCh1(t.getFirst(), t.getSecond()));
			received.getHandshake().forEach(pair -> this.token.addHandShake(pair.getFirst(), pair.getSecond()));
		}
	}
	

	private void instantiateRoles(CoordinatedToken received) {
		if (this == received.getMaster()) {
			if(this.isC2ApproachSelector() == null) {
				addRoles(new C2ApproachSelector());
				//c2A = C2A.createStateChart(this, 0);
			}
			if(this.isTaskAllocator() == null) {
				addRoles(new TaskAllocator());
				//tA = TA.createStateChart(this, 0);
			}
			if(this.isExecutor() != null) {
//				getRoles().remove(this.isExecutor().getOrder());
				//eX = null;
			}
		} else {
			if(this.isC2ApproachSelector() != null) {
				getRoles().remove(this.isC2ApproachSelector().getOrder());
				//c2A = null;
			}
			if(this.isTaskAllocator() != null) {
				getRoles().remove(this.isTaskAllocator().getOrder());
				//tA = null;
			}
			if(this.isExecutor() == null) {
				addRoles(new Executor());
				//StateChartScheduler.beginNow(eX);
				//eX = EX.createStateChart(this, 0);
			}
		}
	}
	

	private List<Token> tokensToSend(CoordinatedToken token) {
		List<Token> tokensToSend = new ArrayList<Token>();
		List<Drone> droppedDrones = new ArrayList<Drone>();
		if (this == token.getMaster()) {
			List<Drone> executors = new ArrayList<Drone>(token.getTeam());
			executors.remove(token.getMaster());
			for (Drone executor : executors) {
				if (executor.ping()) {
					CoordinatedToken slice = new CoordinatedToken(token);
					slice.getAllocations().clear();
					slice.getAvailableTasks().clear();
					slice.getCh1().clear();
					token.dronesAllocatedTasks(executor).forEach(t -> slice.setTaskAsAllocated(t, executor));
					slice.setFromTo(this, executor);
					tokensToSend.add(slice);
				} else {
					droppedDrones.add(executor);
					this.isTaskAllocator().refillTasksFromDrone(token, executor);
				}
			}
			token.setFromTo(this, this);
			tokensToSend.add(new CoordinatedToken(token));
		} else {
			Drone master = token.getMaster();
			if (master.ping()) {
				Token tokenToSend = new CoordinatedToken(token);
				tokenToSend.setFromTo(this, master);
				tokensToSend.add(tokenToSend);
			}
		}
		tokensToSend.forEach(t -> t.getTeam().removeAll(droppedDrones));
		return tokensToSend;
	}

	// Collaborative /////////////////////////////////////////////////////////////////////////////
	
	private void mergeToken(CollaborativeToken received) {
		if ((received.getMasters().contains(this) && this.token == null) || !received.getMasters().contains(this)) { 
			//master without token received the token (first token created)
			//slaves (not master) that receives a token (the token sent by a master is prepared according to slaves in its team)
			this.token = new CollaborativeToken(received);
		} else { //Master with a previous token
			Drone from = received.getFromTo().getFirst();
			Integer version = this.token.getVersion();
			List<Pair<Drone, Boolean>> handShakeStatus = this.token.getHandshake();
			if (received.getMasters().contains(from)) { //received a token from a master
				List<Pair<Task, Drone>> oldCh1 = this.token.getCh1();
				this.token = new CollaborativeToken(received);
				handShakeStatus.forEach(pair -> this.token.addHandShake(pair.getFirst(), pair.getSecond()));
				received.getHandshake().forEach(pair -> this.token.addHandShake(pair.getFirst(), pair.getSecond()));
				this.token.setVersion(version);
				for (Pair<Task, Drone> pair : oldCh1) {
					//rewrite ch1 in case of the element is in the slave set or in case of task completed
					if (received.slavesOf(this).contains(pair.getSecond()) || pair.getSecond()==null) {
						this.token.setTaskAsCh1(pair.getFirst(), pair.getSecond());
					}
				}
			} else { //received from another member not master
				received.getCh1().forEach(t -> this.token.setTaskAsCh1(t.getFirst(), t.getSecond()));
				received.getHandshake().forEach(pair -> this.token.addHandShake(pair.getFirst(), pair.getSecond()));
			}
		}
	}
	
	
	private void instantiateRoles(CollaborativeToken received) {
		if (received.getMasters().contains(this)) {
			if(this.isC2ApproachSelector() == null) {
				addRoles(new C2ApproachSelector());
				//c2A = C2A.createStateChart(this, 0);
			}
			if(this.isTaskAllocator() == null) {
				addRoles(new TaskAllocator());
				//tA = TA.createStateChart(this, 0);
			}
			if(this.isExecutor() == null) {
//				getRoles().remove(this.isExecutor().getOrder());
				addRoles(new Executor());
				//eX = EX.createStateChart(this, 0);	
			}
		} else {
			if(this.isC2ApproachSelector() != null) {
				getRoles().remove(this.isC2ApproachSelector().getOrder());
				//c2A = null;
			}
			if(this.isTaskAllocator() != null) {
				getRoles().remove(this.isTaskAllocator().getOrder());
				//tA = null;
			}
			if(this.isExecutor() == null) {
				addRoles(new Executor());
				//eX = null;
			}
		}
	}
	
	private List<Token> tokensToSend(CollaborativeToken token) {
		List<Token> tokensToSend = new ArrayList<Token>();
		List<Drone> droppedDrones = new ArrayList<Drone>();
		if (token.getMasters().contains(this)) {
			List<Drone> receivers = new ArrayList<Drone>(token.getMasters());
			receivers.addAll(token.slavesOf(this));
			for (Drone teammate : receivers) {
				if (teammate.ping()) {
					if (token.getMasters().contains(teammate)) {
						CollaborativeToken toSend = new CollaborativeToken(token);
						toSend.setFromTo(this, teammate);
						tokensToSend.add(toSend);
					} else {
						CollaborativeToken slice = new CollaborativeToken(token);
						slice.getAllocations().clear();
						slice.getAvailableTasks().clear();
						slice.getCh1().clear();
						token.dronesAllocatedTasks(teammate).forEach(t -> slice.setTaskAsAllocated(t, teammate));
						slice.setFromTo(this, teammate);
						tokensToSend.add(slice);
					}
				} else {
					this.isTaskAllocator().refillTasksFromDrone(token, teammate);
					droppedDrones.add(teammate);
					droppedDrones.addAll(token.slavesOf(teammate));
				}
			}
		} else {
			Drone master = token.masterOf(this);
			if (master.ping()) {
				Token tokenToSend = new CollaborativeToken(token);
				tokenToSend.setFromTo(this, master);
				tokensToSend.add(tokenToSend);
			} else {
				droppedDrones.add(master);
				droppedDrones.addAll(token.slavesOf(master));
			}
		}
		tokensToSend.forEach(t -> t.getTeam().removeAll(droppedDrones));
		return tokensToSend;
	}
	
	// Edge ///////////////////////////////////////////////////////////////////////////////////////
	
	private void mergeToken(EdgeToken received) {
		this.token = new EdgeToken(received);
	}
	
	private void instantiateRoles(EdgeToken received) {
		if (this.isC2ApproachSelector() == null) {
			addRoles(new C2ApproachSelector());
			//c2A = C2A.createStateChart(this, 0);
		}
		if(this.isTaskAllocator() == null) {
			addRoles(new TaskAllocator());
			//tA = TA.createStateChart(this, 0);
		}
		if(this.isExecutor() == null) {
			addRoles(new Executor());
			//eX = EX.createStateChart(this, 0);
		}
	}

	private List<Token> tokensToSend(EdgeToken token) {
		List<Token> tokensToSend = new ArrayList<Token>();
		List<Drone> droppedDrones = new ArrayList<Drone>();
		for (Drone teammate : token.getTeam()) {
			if (teammate.ping()) {
				EdgeToken toSend = new EdgeToken(token);
				toSend.setFromTo(this, teammate);
				tokensToSend.add(toSend);
			} else {
				droppedDrones.add(teammate);
				this.isTaskAllocator().refillTasksFromDrone(token, teammate);
			}
		}
		tokensToSend.forEach(t -> t.getTeam().removeAll(droppedDrones));
		return tokensToSend;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return "" + this.id;
	}

	public String information() {
		if (Parameters.getInstance().lightScreen()) return "";

		// Drone's main information.
		String information = "";
		information += "Id: " + this.id + "\n";
		information += "S: " + this.sensors + '\n';
		information += "F: " + this.fuel + "\n";

		// Roles information.
		for (Role r : getRoles().values()) {
			information += r.information();
		}
		
		if(Parameters.getInstance().infoType1())
			return information;
		
		if (this.tokenState == TokenState.RECEIVED_UPDATED) {
				information += this.token.information() + "\n";
		}
		return information;
	}
	
	public boolean isTokenUpdated() {
		if(tokenState == TokenState.RECEIVED_UPDATED)
			return true;
		else
			return false;
	}
	
	//Perform task addition in the token in case of be with the drone
	//PROTOTYPE (TEST REQUIRED)
	public void addTasks(List<Task> tasks) {
		if (this.tokenState == TokenState.RECEIVED_UPDATED) {
			this.token.getAvailableTasks().addAll(tasks);
		}
	}
	
	public Integer getId() {
		return this.id;
	}

	/**
	 * @return the configurations
	 */
	public int getReconfigurations() {
		return reconfigurations;
	}

	/**
	 * @param configurations the configurations to set
	 */
	public void setReconfigurations(int reconfigurations) {
		this.reconfigurations = reconfigurations;
	}

	/**
	 * @return the maneuvers
	 */
	public int getManeuvers() {
		return maneuvers;
	}

	/**
	 * @param maneuvers the maneuvers to set
	 */
	public void setManeuvers(int maneuvers) {
		this.maneuvers = maneuvers;
	}

	/**
	 * @return the reward
	 */
	public double getReward() {
		return reward;
	}

	/**
	 * @param reward the reward to set
	 */
	public void setReward(double reward) {
		this.reward = reward;
	}

}
