package edu.buffalo.cse.jive.finiteStateMachine.util;

import edu.buffalo.cse.jive.finiteStateMachine.models.TransitionBuilder;
import edu.buffalo.cse.jive.finiteStateMachine.views.SvgGenerator;

public class AutoDrawThread extends Thread {

	private TransitionBuilder transitionBuilder;
	private SvgGenerator svgGenerator;

	public AutoDrawThread(TransitionBuilder transitionBuilder, SvgGenerator svgGenerator) {
		super();
		this.transitionBuilder = transitionBuilder;
		this.svgGenerator = svgGenerator;
	}

	@Override
	public void run() {
		while (true) {
			if (transitionBuilder.isUpdated()) {
				svgGenerator.generate(transitionBuilder.getTransitions());
				transitionBuilder.setUpdated(false);
			}
		}
	}
}
