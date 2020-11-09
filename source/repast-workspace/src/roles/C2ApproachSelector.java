package roles;

import java.util.ArrayList;
import java.util.List;

import domain.C2Approach;
import domain.Treatment;
import entities.CollaborativeToken;
import entities.ConflictedToken;
import entities.CoordinatedToken;
import entities.DeconflictedToken;
import entities.Drone;
import entities.EdgeToken;
import entities.Task;
import entities.Token;
import repast.simphony.random.RandomHelper;
import repast.simphony.util.collections.Pair;
import utils.Parameters;

public class C2ApproachSelector extends Role implements Comparable<Role>{
	
	private int manouvers = 0;
		
	public void step(Drone drone, Token token) {
		if (token == null || (Parameters.getInstance().agilityMethod().equals(Treatment.NOC2AGILITY))) {
			return; //No maneuver execution
		}
		
		// Stop Condition
		if (token.getC2Approach() != token.getNextC2Approach()) {
			if (token.getRequester() == drone) {
				if (token.handShakeStatus() != null && token.handShakeStatus()) { // Everyone checked communication links.
					Token newToken = changeToken(token, drone);
					drone.receiveToken(newToken);
					this.manouvers++;
				} else if (token.handShakeStatus() != null && !token.handShakeStatus()) { // Someone is not capable of maneuver.
					token.setNextC2Approach(token.getC2Approach());
					token.setRequester(null);
					token.getHandshake().clear();
				} 
			} 
			return; // Drone is not the requester or someone didn't respond handshake.
		}
		
		C2Approach nextC2Approach = token.getC2Approach();
		//Define C2 Approach selection based on the treatment
		if(token.getCh3() != null && token.getCh3().size() > 0) {
			if(Parameters.getInstance().agilityMethod().equals(Treatment.ONLYC2MENEUVER))
				nextC2Approach = selectC2Approach(token);
			if(Parameters.getInstance().agilityMethod().equals(Treatment.C2AGILITY))
				nextC2Approach = selectC2Approach(token.getC2Approach());
			System.out.println("==> Current C2Ap:"+token.getC2Approach().name() + " ---- Next C2Ap:"+nextC2Approach.name());
		}
		
		if (nextC2Approach != token.getC2Approach()) {
			token.setNextC2Approach(nextC2Approach);
			token.getAvailableTasks().addAll(token.getCh3());
			token.setCh3(null);
			token.setRequester(drone);
		}else {
			token.setCh3(null);
		}
		
	}
 	
	// We are using RandomHelper class based on the seeds defined during RePast execution.
	// Results: For all treatments (A1, A2 and A3) we choose the same number for each scenario, i.e., different scenarios cause different number selection 
	private C2Approach selectC2Approach(Token token) {
		//int tickCount = (int) RunEnvironment.getInstance().getCurrentSchedule().getTickCount();
		//return C2Approach.values()[(tickCount / 200) % 5];
		//(MAX - MIN + 1) + MIN));
		//int index = (int)Math.floor(Math.random() * ((C2Approach.values().length - 0 + 1) + 0));
		//return C2Approach.values()[index%C2Approach.values().length];
		int index = (int)RandomHelper.nextIntFromTo(0, C2Approach.values().length - 1);
		return C2Approach.values()[index];
	}
	
	/**
	 * @param approach
	 * @return Next C2 Approach in the C2 Approach Space diagonal. When it reaches EDGE, it goes to CONFLICTED due to there is no resources to go back on diagonal
	 */
	private C2Approach selectC2Approach(C2Approach approach) {
		if(approach.equals(C2Approach.CONFLICTED)) //CONFLICTED is the end. The connections are not redone.
			return approach;
		int i = 0;
		for(C2Approach c2a : C2Approach.values()) {
			if(c2a.equals(approach))
				break;
			i++;
		}
		return C2Approach.values()[((i+1) % 5)];
	}
	
	private Token changeToken(Token token, Drone drone) {
		Token newToken = null;
		switch(token.getNextC2Approach()) {
		case CONFLICTED:
			newToken = new ConflictedToken(token.getTeam(), token.getAvailableTasks(), token.getVersion());
			break;
		case DECONFLICTED:
			newToken = new DeconflictedToken(token.getTeam(), token.getAvailableTasks(), token.getVersion(), drone);
			break;
		case COORDINATED:
			newToken = new CoordinatedToken(token.getTeam(), token.getAvailableTasks(), drone, token.getVersion());
			break;
		case COLLABORATIVE:
			List<Drone> masters = electMasters(token, drone);
			if (masters == null) {
				newToken = token;
				newToken.setRequester(null);
				newToken.setNextMasters(null);
				newToken.setNextC2Approach(token.getC2Approach());
			} else {				
				newToken = new CollaborativeToken(token.getTeam(), token.getAvailableTasks(), masters, token.getVersion());
			}
			break;
		case EDGE:
			newToken = new EdgeToken(token.getTeam(), token.getAvailableTasks(), token.getVersion());
			break;
		}
		List<Drone> dronesWithAllocation = new ArrayList<Drone>();
		for (Pair<Task,Drone> allocation : token.getAllocations()) {
			if (!dronesWithAllocation.contains(allocation.getSecond()) || newToken.getC2Approach() == C2Approach.CONFLICTED) {
				newToken.setTaskAsAllocated(allocation.getFirst(), allocation.getSecond());
				dronesWithAllocation.add(allocation.getSecond());
			} else {
				newToken.setTaskAsAvailable(allocation.getFirst());
			}
		}
		for (Pair<Task, Drone> pair : token.getCh1()) {
			newToken.setTaskAsCh1(pair.getFirst(), pair.getSecond());			
		}
		return newToken;
	}
	
	public String information() {
		return getAbbreviation()+"\n";
	}

	@Override
	public int getOrder() {
		return 3;
	}
	
	public String getAbbreviation() {
		return "C2A";
	}

	@Override
	public int compareTo(Role o) {
		if(this.getOrder() < o.getOrder())
			return -1;
		return 1;
	}
	
	private List<Drone> electMasters(Token token, Drone drone) {
		List<Drone> masters = new ArrayList<Drone>();
		masters.add(drone);
		
		Integer teammate = 0;
		while (masters.size() < CollaborativeToken.getNMasters()) {
			Drone candidate = token.getTeam().get(teammate);
			if (candidate.ping() && candidate != drone) {
				masters.add(candidate);
			} else if (teammate >= token.getTeam().size()){
				return null;
			}
			teammate++;
		}
		return masters; 
	}

	/**
	 * @return the manouvers
	 */
	public int getManouvers() {
		return manouvers;
	}

	/**
	 * @param manouvers the manouvers to set
	 */
	public void setManouvers(int manouvers) {
		this.manouvers = manouvers;
	}

}

