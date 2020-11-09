
package entities.chart;

import java.util.Map;
import java.util.HashMap;

import repast.simphony.statecharts.*;
import repast.simphony.statecharts.generator.GeneratedFor;

import entities.*;

@GeneratedFor("_vnYvAJciEeqUuepXYUrgtg")
public class TA extends DefaultStateChart<entities.Drone> {

	public static TA createStateChart(entities.Drone agent, double begin) {
		TA result = createStateChart(agent);
		StateChartScheduler.INSTANCE.scheduleBeginTime(begin, result);
		return result;
	}

	public static TA createStateChart(entities.Drone agent) {
		TAGenerator generator = new TAGenerator();
		return generator.build(agent);
	}

	private TA(entities.Drone agent) {
		super(agent);
	}

	private static class MyStateChartBuilder extends StateChartBuilder<entities.Drone> {

		public MyStateChartBuilder(entities.Drone agent, AbstractState<entities.Drone> entryState,
				String entryStateUuid) {
			super(agent, entryState, entryStateUuid);
			setPriority(0.0);
		}

		@Override
		public TA build() {
			TA result = new TA(getAgent());
			setStateChartProperties(result);
			return result;
		}
	}

	private static class TAGenerator {

		private Map<String, AbstractState<Drone>> stateMap = new HashMap<String, AbstractState<Drone>>();

		public TA build(Drone agent) {
			SimpleStateBuilder<Drone> ssBuilder1 = new SimpleStateBuilder<Drone>("Waiting");
			SimpleState<Drone> s1 = ssBuilder1.build();
			stateMap.put("_0qxwYJciEeqUuepXYUrgtg", s1);
			MyStateChartBuilder mscb = new MyStateChartBuilder(agent, s1, "_0qxwYJciEeqUuepXYUrgtg");

			SimpleStateBuilder<Drone> ssBuilder2 = new SimpleStateBuilder<Drone>("Ready");
			SimpleState<Drone> s2 = ssBuilder2.build();
			stateMap.put("_7fovYZciEeqUuepXYUrgtg", s2);
			mscb.addRootState(s2, "_7fovYZciEeqUuepXYUrgtg");
			SimpleStateBuilder<Drone> ssBuilder3 = new SimpleStateBuilder<Drone>("Allocating");
			SimpleState<Drone> s3 = ssBuilder3.build();
			stateMap.put("_mPooUJfZEeq49tuNB7DUMA", s3);
			mscb.addRootState(s3, "_mPooUJfZEeq49tuNB7DUMA");
			SimpleStateBuilder<Drone> ssBuilder4 = new SimpleStateBuilder<Drone>("Updating");
			SimpleState<Drone> s4 = ssBuilder4.build();
			stateMap.put("_wvrfoZfZEeq49tuNB7DUMA", s4);
			mscb.addRootState(s4, "_wvrfoZfZEeq49tuNB7DUMA");
			SimpleStateBuilder<Drone> ssBuilder5 = new SimpleStateBuilder<Drone>("Notifying");
			SimpleState<Drone> s5 = ssBuilder5.build();
			stateMap.put("_fmXYYJhUEeq49tuNB7DUMA", s5);
			mscb.addRootState(s5, "_fmXYYJhUEeq49tuNB7DUMA");
			SimpleStateBuilder<Drone> ssBuilder6 = new SimpleStateBuilder<Drone>("Binding");
			SimpleState<Drone> s6 = ssBuilder6.build();
			stateMap.put("_3Re1IJicEeq49tuNB7DUMA", s6);
			mscb.addRootState(s6, "_3Re1IJicEeq49tuNB7DUMA");
			createTransitions(mscb);
			return mscb.build();

		}

		private void createTransitions(MyStateChartBuilder mscb) {
			// creates transition Transition 3
			createTransition1(mscb);
			// creates transition Transition 7
			createTransition2(mscb);
			// creates transition Transition 9
			createTransition3(mscb);
			// creates transition Transition 31
			createTransition4(mscb);
			// creates transition Transition 40
			createTransition5(mscb);
			// creates transition Transition 84
			createTransition6(mscb);
			// creates transition Transition 133
			createTransition7(mscb);
			// creates transition Transition 137
			createTransition8(mscb);
			// creates transition Transition 171
			createTransition9(mscb);

		}

		// creates transition Transition 3, from = Waiting, to = Ready
		private void createTransition1(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 3",
					stateMap.get("_0qxwYJciEeqUuepXYUrgtg"), stateMap.get("_7fovYZciEeqUuepXYUrgtg"));
			tb.registerGuard(new SC3Guard1());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "__UkZkZciEeqUuepXYUrgtg");
		}

		// creates transition Transition 7, from = Ready, to = Allocating
		private void createTransition2(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 7",
					stateMap.get("_7fovYZciEeqUuepXYUrgtg"), stateMap.get("_mPooUJfZEeq49tuNB7DUMA"));
			tb.registerGuard(new SC3Guard2());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "_ucZ1cJfZEeq49tuNB7DUMA");
		}

		// creates transition Transition 9, from = Ready, to = Updating
		private void createTransition3(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 9",
					stateMap.get("_7fovYZciEeqUuepXYUrgtg"), stateMap.get("_wvrfoZfZEeq49tuNB7DUMA"));
			tb.registerGuard(new SC3Guard3());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "_2mPqYJfZEeq49tuNB7DUMA");
		}

		// creates transition Transition 31, from = Updating, to = Ready
		private void createTransition4(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 31",
					stateMap.get("_wvrfoZfZEeq49tuNB7DUMA"), stateMap.get("_7fovYZciEeqUuepXYUrgtg"));
			tb.registerGuard(new SC3Guard4());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "_YRIFoZhTEeq49tuNB7DUMA");
		}

		// creates transition Transition 40, from = Allocating, to = Notifying
		private void createTransition5(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 40",
					stateMap.get("_mPooUJfZEeq49tuNB7DUMA"), stateMap.get("_fmXYYJhUEeq49tuNB7DUMA"));
			tb.registerGuard(new SC3Guard5());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "_w2-kkZhUEeq49tuNB7DUMA");
		}

		// creates transition Transition 84, from = Notifying, to = Binding
		private void createTransition6(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 84",
					stateMap.get("_fmXYYJhUEeq49tuNB7DUMA"), stateMap.get("_3Re1IJicEeq49tuNB7DUMA"));
			tb.registerGuard(new SC3Guard6());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "_65T3cZicEeq49tuNB7DUMA");
		}

		// creates transition Transition 133, from = Binding, to = Notifying
		private void createTransition7(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 133",
					stateMap.get("_3Re1IJicEeq49tuNB7DUMA"), stateMap.get("_fmXYYJhUEeq49tuNB7DUMA"));
			tb.registerGuard(new SC3Guard7());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "_lTSf4JkNEeq49tuNB7DUMA");
		}

		// creates transition Transition 137, from = Binding, to = Ready
		private void createTransition8(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 137",
					stateMap.get("_3Re1IJicEeq49tuNB7DUMA"), stateMap.get("_7fovYZciEeqUuepXYUrgtg"));
			tb.registerGuard(new SC3Guard8());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "_CVbAEJkTEeq49tuNB7DUMA");
		}

		// creates transition Transition 171, from = Allocating, to = Waiting
		private void createTransition9(MyStateChartBuilder mscb) {
			TransitionBuilder<Drone> tb = new TransitionBuilder<Drone>("Transition 171",
					stateMap.get("_mPooUJfZEeq49tuNB7DUMA"), stateMap.get("_0qxwYJciEeqUuepXYUrgtg"));
			tb.registerGuard(new SC3Guard9());
			tb.addTrigger(new AlwaysTrigger(1.0));
			tb.setPriority(0.0);
			mscb.addRegularTransition(tb.build(), "__ztLcJl5EeqQ1p0AkO9stQ");
		}

	}
}
