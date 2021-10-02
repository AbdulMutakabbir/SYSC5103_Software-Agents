//	File:		StateMachine.java
//	Author:		Mutakabbir
//	Date:	    2/10/2021

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/* 
*   This class acts as a State Machine 
*/
public class StateMachine {

    public String q; // Start state
    public ArrayList<String> Q; // contains states
    public ArrayList<String> S; // contains alphabets
    public ArrayList<String> F; // contains acceptance states
    public ArrayList<ArrayList<String>> d; // contains transition function

    public State current_state; // current State

    // Delimiters for reading the data
    private static String Q_DELIMITER = ",";
    private static String S_DELIMITER = ",";
    private static String F_DELIMITER = ",";
    private static String d_DELIMITER = ";";
    private static String t_delimiter = ",";

    // constructor init's the State Machine 
    public StateMachine() {
        this.q = "";
        this.Q = new ArrayList<String>();
        this.S = new ArrayList<String>();
        this.F = new ArrayList<String>();
        this.d = new ArrayList<ArrayList<String>>();
        this.current_state = new State();
    }

    // Will return the next action that is to be performed
    public Action getNextAction(Environment env) {
        String env_name = env.getEnvironmen_name();

        Action action;

        int Q_index = Q.indexOf(this.current_state.getStateString());
        int S_index = S.indexOf(env_name);

        String[] transition = d.get(Q_index).get(S_index).split(t_delimiter);

        // set new current state
        this.current_state.setState(transition[1]);

        // get the action that needs to be done 
        action = getAction(transition[0].trim());

        return action;
    }

    // reads the data from config file
    public void setStateMachineFormCSV(String file_name) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(file_name));

        // read start State
        q = br.readLine();

        // read final states
        String F_line = br.readLine();
        F = new ArrayList<String>(Arrays.asList(F_line.split(F_DELIMITER)));

        // read states
        String Q_line = br.readLine();
        Q = new ArrayList<String>(Arrays.asList(Q_line.split(Q_DELIMITER)));

        // read alphabets
        String S_line = br.readLine();
        S = new ArrayList<String>(Arrays.asList(S_line.split(S_DELIMITER)));

        // read transitions
        String d_line = br.readLine();
        while (d_line != null) {
            ArrayList<String> transitions = new ArrayList<String>(Arrays.asList(d_line.split(d_DELIMITER)));
            d.add(transitions);

            d_line = br.readLine();
        }

        br.close();

        // System.out.println("State machine is configured...");
        // printStateMachine();
    }

    // maps the action from string to class
    private Action getAction(String action_name) {
        Action action;

        switch (action_name) {
            case "TURN":
                action = new ActionTurn();
                break;
            case "KICK":
                action = new ActionKick();
                break;
            case "DASH":
                action = new ActionDash();
                break;
            default:
                action = new ActionUnknown();
                break;
        }

        return action;
    }

    // prints the State Machine
    private void printStateMachine() {
        System.out.println("Initial State: " + q);
        System.out.println("States:        " + Q);
        System.out.println("Alphabets/Env: " + S);
        System.out.println("Final States:  " + F);
        System.out.println("Transitions: \n" + d);
    }
}


/* 
*   This class holds the state of the State Machine
*/
class State {

    boolean single_action; // State variable

    // state identifiers
    public static String PLAYER_PERFORMS_ONE_STEP_ACTION = "ONE_STEP_ACTION";
    public static String PLAYER_PERFORMS_MULTI_STEP_ACTION = "MULTI_STEP_ACTION";

    // constructor sets the default value of the state machine to "TRUE"
    public State() {
        single_action = true;
    }

    // returns the state in String format
    public String getStateString() {
        if (single_action) {
            return PLAYER_PERFORMS_ONE_STEP_ACTION;
        } else {
            return PLAYER_PERFORMS_MULTI_STEP_ACTION;
        }
    }

    // sets the state 
    public void setState(String new_state_string) {
        if (!new_state_string.equals(PLAYER_PERFORMS_ONE_STEP_ACTION)) {
            single_action = false;
        } else {
            single_action = true;
        }
    }

}
