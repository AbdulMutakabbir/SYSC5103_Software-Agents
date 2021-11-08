//
//	File:			Brain.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//
//    Modified by:	Paul Marlow

//    Modified by:      Edgar Acosta
//    Date:             March 4, 2008

import java.lang.Math;
import java.util.ArrayList;
import java.util.regex.*;

class Brain extends Thread implements SensorInput {
	// ---------------------------------------------------------------------------
	// This constructor:
	// - stores connection to krislet
	// - starts thread for this object
	public Brain(SendCommand krislet, String team, char side, int number, String playMode) {
		m_timeOver = false;
		m_krislet = krislet;
		m_memory = new Memory();
		// m_team = team;
		m_side = side;
		// m_number = number;
		m_playMode = playMode;
		start();
	}

	public void run() {
		ObjectInfo ball = null;
		ObjectInfo goal = null;

		// first put it somewhere on my side
		if (Pattern.matches("^before_kick_off.*", m_playMode))
			m_krislet.move(-Math.random() * 52.5, 34 - Math.random() * 68.0);

		
        STRIP strip = new STRIP(null, null);
		ArrayList<STRIPOperator> plan = strip.plan();
        System.out.println(plan.toString());

		STRIPActionPerformer performer = new STRIPActionPerformer(ball, goal, plan);

		while (!m_timeOver) {
			ball = m_memory.getObject("ball");
		
			if(m_side == 'l')
				goal = m_memory.getObject("goal r");
			else
				goal = m_memory.getObject("goal l");
		
			Actions ac = performer.nextAction(ball, goal);

			ac.do_action(m_krislet);

			// sleep one step to ensure that we will not send
			// two commands in one cycle.
			try {
				Thread.sleep(2 * SoccerParams.simulator_step);
			} catch (Exception e) {
			}
		}
		m_krislet.bye();
	}

	// ===========================================================================
	// Here are suporting functions for implement logic

	// ===========================================================================
	// Implementation of SensorInput Interface

	// ---------------------------------------------------------------------------
	// This function sends see information
	public void see(VisualInfo info) {
		m_memory.store(info);
	}

	// ---------------------------------------------------------------------------
	// This function receives hear information from player
	public void hear(int time, int direction, String message) {
	}

	// ---------------------------------------------------------------------------
	// This function receives hear information from referee
	public void hear(int time, String message) {
		if (message.compareTo("time_over") == 0)
			m_timeOver = true;

	}

	// ===========================================================================
	// Private members
	private SendCommand m_krislet; // robot which is controled by this brain
	private Memory m_memory; // place where all information is stored
	private char m_side;
	volatile private boolean m_timeOver;
	private String m_playMode;

}
