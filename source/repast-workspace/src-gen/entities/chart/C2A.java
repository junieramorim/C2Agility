
package entities.chart;

import java.util.Map;
import java.util.HashMap;

import repast.simphony.statecharts.*;
import repast.simphony.statecharts.generator.GeneratedFor;

import entities.*;

@GeneratedFor("_NQ9TUJpBEeqqQIEemyx-2w")
public class C2A extends DefaultStateChart<entities.Drone> {

	public static C2A createStateChart(entities.Drone agent, double begin) {
		C2A result = createStateChart(agent);
		StateChartScheduler.INSTANCE.scheduleBeginTime(begin, result);
		return result;
	}

	public static C2A createStateChart(entities.Drone agent) {
		C2AGenerator generator = new C2AGenerator();
		return generator.build(agent);
	}

	private C2A(entities.Drone agent) {
		super(agent);
	}

	private static class MyStateChartBuilder extends StateChartBuilder<entities.Drone> {

		public MyStateChartBuilder(entities.Drone agent, AbstractState<entities.Drone> entryState,
				String entryStateUuid) {
			super(agent, entryState, entryStateUuid);
			setPriority(0.0);
		}

		@Override
		public C2A build() {
			C2A result = new C2A(getAgent());
			setStateChartProperties(result);
			return result;
		}
	}

	private static class C2AGenerator {

		private Map<String, AbstractState<Drone>> stateMap = new HashMap<String, AbstractState<Drone>>();

		public C2A build(Drone agent) {
			SimpleStateBuilder<Drone> ssBuilder1 = new SimpleStateBuilder<Drone>("Maneuvering");
			SimpleState<Drone> s1 = ssBuilder1.build();
			stateMap.put("_PQ5KwZpBEeqqQIEemyx-2w", s1);
			MyStateChartBuilder mscb = new MyStateChartBuilder(agent, s1, "_PQ5KwZpBEeqqQIEemyx-2w");
			SimpleStateBuilder<Drone> ssBuilder2 = new SimpleStateBuilder<Drone>("Operating");
			SimpleState<Drone> s2 = ssBuilder2.build();
			stateMap.put("_OR_5kJpBEeqqQIEemyx-2w", s2);
			mscb.addRootState(s2, "_OR_5kJpBEeqqQIEemyx-2w");

			createTransitions(mscb);
			return mscb.build();

		}

		private void createTransitions(MyStateChartBuilder mscb) {
			// creates transition Transition 3
			createTransition1(mscb);
			// creates transition Transition 4
			createTransition2(mscb);

		}

		// creates transition Transition 3, from = Maneuvering, to = Operating
		private void createTransition1(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 3",
					stateMap.get("_PQ5KwZpBEeqqQIEemyx-2w"), stateMap.get("_OR_5kJpBEeqqQIEemyx-2w"));
			tb.registerGuard(new SC1Guard1());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "_RyJmkJpBEeqqQIEemyx-2w");
		}

		// creates transition Transition 4, from = Operating, to = Maneuvering
		private void createTransition2(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 4",
					stateMap.get("_OR_5kJpBEeqqQIEemyx-2w"), stateMap.get("_PQ5KwZpBEeqqQIEemyx-2w"));
			tb.registerGuard(new SC1Guard2());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "_STnU4ZpBEeqqQIEemyx-2w");
		}

	}
}
