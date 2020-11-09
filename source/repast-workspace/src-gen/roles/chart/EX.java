
package roles.chart;

import java.util.Map;
import java.util.HashMap;

import repast.simphony.statecharts.*;
import repast.simphony.statecharts.generator.GeneratedFor;

import entities.*;

@GeneratedFor("_fiV1gJSaEeqUuepXYUrgtg")
public class EX extends DefaultStateChart<entities.Drone> {

	public static EX createStateChart(entities.Drone agent, double begin) {
		EX result = createStateChart(agent);
		StateChartScheduler.INSTANCE.scheduleBeginTime(begin, result);
		return result;
	}

	public static EX createStateChart(entities.Drone agent) {
		EXGenerator generator = new EXGenerator();
		return generator.build(agent);
	}

	private EX(entities.Drone agent) {
		super(agent);
	}

	private static class MyStateChartBuilder extends StateChartBuilder<entities.Drone> {

		public MyStateChartBuilder(entities.Drone agent, AbstractState<entities.Drone> entryState,
				String entryStateUuid) {
			super(agent, entryState, entryStateUuid);
			setPriority(0.0);
		}

		@Override
		public EX build() {
			EX result = new EX(getAgent());
			setStateChartProperties(result);
			return result;
		}
	}

	private static class EXGenerator {

		private Map<String, AbstractState<Drone>> stateMap = new HashMap<String, AbstractState<Drone>>();

		public EX build(Drone agent) {
			SimpleStateBuilder<Drone> ssBuilder1 = new SimpleStateBuilder<Drone>("Idle");
			SimpleState<Drone> s1 = ssBuilder1.build();
			stateMap.put("_iY3WsZSaEeqUuepXYUrgtg", s1);
			MyStateChartBuilder mscb = new MyStateChartBuilder(agent, s1, "_iY3WsZSaEeqUuepXYUrgtg");
			SimpleStateBuilder<Drone> ssBuilder2 = new SimpleStateBuilder<Drone>("Running");
			SimpleState<Drone> s2 = ssBuilder2.build();
			stateMap.put("_pSQQYZSaEeqUuepXYUrgtg", s2);
			mscb.addRootState(s2, "_pSQQYZSaEeqUuepXYUrgtg");

			SimpleStateBuilder<Drone> ssBuilder3 = new SimpleStateBuilder<Drone>("Reallocating");
			SimpleState<Drone> s3 = ssBuilder3.build();
			stateMap.put("_6_SWMJTDEeqUuepXYUrgtg", s3);
			mscb.addRootState(s3, "_6_SWMJTDEeqUuepXYUrgtg");
			SimpleStateBuilder<Drone> ssBuilder4 = new SimpleStateBuilder<Drone>("Reporting");
			SimpleState<Drone> s4 = ssBuilder4.build();
			stateMap.put("_FyWA4JYIEeqUuepXYUrgtg", s4);
			mscb.addRootState(s4, "_FyWA4JYIEeqUuepXYUrgtg");
			createTransitions(mscb);
			return mscb.build();

		}

		private void createTransitions(MyStateChartBuilder mscb) {
			// creates transition Transition 12
			createTransition1(mscb);
			// creates transition Transition 34
			createTransition2(mscb);
			// creates transition Transition 39
			createTransition3(mscb);
			// creates transition Transition 55
			createTransition4(mscb);
			// creates transition Transition 60
			createTransition5(mscb);
			// creates transition Transition 61
			createTransition6(mscb);

		}

		// creates transition Transition 12, from = Idle, to = Running
		private void createTransition1(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 12",
					stateMap.get("_iY3WsZSaEeqUuepXYUrgtg"), stateMap.get("_pSQQYZSaEeqUuepXYUrgtg"));
			tb.registerGuard(new SC2Guard1());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "_AjNOYZSbEeqUuepXYUrgtg");
		}

		// creates transition Transition 34, from = Running, to = Idle
		private void createTransition2(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 34",
					stateMap.get("_pSQQYZSaEeqUuepXYUrgtg"), stateMap.get("_iY3WsZSaEeqUuepXYUrgtg"));
			tb.registerGuard(new SC2Guard2());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "_FCbEgJTDEeqUuepXYUrgtg");
		}

		// creates transition Transition 39, from = Running, to = Reallocating
		private void createTransition3(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 39",
					stateMap.get("_pSQQYZSaEeqUuepXYUrgtg"), stateMap.get("_6_SWMJTDEeqUuepXYUrgtg"));
			tb.registerGuard(new SC2Guard3());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "_-46TIZTDEeqUuepXYUrgtg");
		}

		// creates transition Transition 55, from = Reallocating, to = Running
		private void createTransition4(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 55",
					stateMap.get("_6_SWMJTDEeqUuepXYUrgtg"), stateMap.get("_pSQQYZSaEeqUuepXYUrgtg"));
			tb.registerGuard(new SC2Guard4());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "_LVCKMJYEEeqUuepXYUrgtg");
		}

		// creates transition Transition 60, from = Running, to = Reporting
		private void createTransition5(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 60",
					stateMap.get("_pSQQYZSaEeqUuepXYUrgtg"), stateMap.get("_FyWA4JYIEeqUuepXYUrgtg"));
			tb.registerGuard(new SC2Guard5());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "_OxhiYZYIEeqUuepXYUrgtg");
		}

		// creates transition Transition 61, from = Reporting, to = Running
		private void createTransition6(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 61",
					stateMap.get("_FyWA4JYIEeqUuepXYUrgtg"), stateMap.get("_pSQQYZSaEeqUuepXYUrgtg"));
			tb.registerGuard(new SC2Guard6());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "_Q-d7EZYIEeqUuepXYUrgtg");
		}

	}
}
