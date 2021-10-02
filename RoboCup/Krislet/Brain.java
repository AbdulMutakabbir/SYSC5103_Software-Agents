//
//	File:			Brain.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//
//    Modified by:	Paul Marlow

//    Modified by:      Edgar Acosta
//    Date:             March 4, 2008

//    Modified by:      Mutakabbir
//    Date:             30/09/2021

import java.io.IOException;
import java.lang.Math;
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
		ObjectInfo ball_info, goal_info;

		// init state machine
		StateMachine sm = new StateMachine();
		try {
			sm.setStateMachineFormCSV("StateAgent_Table.csv");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// first put it somewhere on my side
		if (Pattern.matches("^before_kick_off.*", m_playMode)) {
			m_krislet.move(-Math.random() * 52.5, 34 - Math.random() * 68.0);
		}

		// init Environment
		Environment env;

		// init Action
		Action action;

		while (!m_timeOver) {

			// get ball info
			ball_info = m_memory.getObject("ball");

			// get goal info
			if (m_side == 'l')
				goal_info = m_memory.getObject("goal r");
			else
				goal_info = m_memory.getObject("goal l");

			// get current environment
			env = new Environment(ball_info, goal_info);

			// String curr_state = sm.current_state.getStateString();

			// get the new action to be performed
			action = sm.getNextAction(env);

			// System.out.println("\n\n" + curr_state + "  X  " + env.environmen_name + "  -->  " + action.getName() + "  X  " + sm.current_state.getStateString());

			// perform the action
			action.do_action(m_krislet, ball_info, goal_info, m_side);

			m_memory.waitForNewInfo();

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
