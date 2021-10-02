//	File:			Environment.java
//	Author:		    Mutakabbir
//	Date:			30/09/2021

/* 
* Class contains information about the environment of the agent
*/
public class Environment {
    // set constants for the environment
    private static String ENV_UNKNOWN = "UNKNOWN";
    private static String ENV_BALL_1_GOAL_KNOWN = "BALL_1_GOAL_KNOWN";
    private static String ENV_BALL_1_GOAL_UNKNOWN = "BALL_1_GOAL_UNKNOWN";
    private static String ENV_BALL_FAR_GOAL_KNOWN = "BALL_FAR_GOAL_KNOWN";
    private static String ENV_BALL_FAR_GOAL_UNKNOWN = "BALL_FAR_GOAL_UNKNOWN";
    private static String ENV_BALL_UNKNOWN_GOAL_KNOWN = "BALL_UNKNOWN_GOAL_KNOWN";
    private static String ENV_BALL_UNKNOWN_GOAL_UNKNOWN = "BALL_UNKNOWN_GOAL_UNKNOWN";

    // environment variables
    public String environmen_name;

    // constructor with ball information as parameter
    public Environment(ObjectInfo ball_info, ObjectInfo goal_info) {
        setEnvironment(ball_info, goal_info);
    }

    // constructor with environment as parameter
    public Environment(String environment_name) {
        setEnvironment(environment_name);
    }

    // getter for environment type
    public String getEnvironmen_name() {
        return environmen_name;
    }

    /*
     * Environment state setter methods
     */
    private void setEnvironmentBallUnknownGoalUnknown() {
        environmen_name = ENV_BALL_UNKNOWN_GOAL_UNKNOWN;
    }

    private void setEnvironmentBallUnknownGoalKnown() {
        environmen_name = ENV_BALL_UNKNOWN_GOAL_KNOWN;
    }

    private void setEnvironmentBallFarGoalUnknown() {
        environmen_name = ENV_BALL_FAR_GOAL_UNKNOWN;
    }

    private void setEnvironmentBallFarGoalKnown() {
        environmen_name = ENV_BALL_FAR_GOAL_KNOWN;
    }

    private void setEnvironmentBallOneGoalUnknown() {
        environmen_name = ENV_BALL_1_GOAL_UNKNOWN;
    }

    private void setEnvironmentBallOneGoalKnown() {
        environmen_name = ENV_BALL_1_GOAL_KNOWN;
    }

    private void setEnvironmentUnknown() {
        environmen_name = ENV_UNKNOWN;
    }

    public void setEnvironment(String environment_name) {
        if (environment_name.equals(ENV_BALL_UNKNOWN_GOAL_UNKNOWN)) {
            setEnvironmentBallUnknownGoalUnknown();
        } else {
            if (environment_name.equals(ENV_BALL_UNKNOWN_GOAL_KNOWN)) {
                setEnvironmentBallUnknownGoalKnown();
            } else {
                if (environment_name.equals(ENV_BALL_FAR_GOAL_UNKNOWN)) {
                    setEnvironmentBallFarGoalUnknown();
                } else {
                    if (environment_name.equals(ENV_BALL_FAR_GOAL_KNOWN)) {
                        setEnvironmentBallFarGoalKnown();
                    } else {
                        if (environment_name.equals(ENV_BALL_1_GOAL_UNKNOWN)) {
                            setEnvironmentBallOneGoalUnknown();
                        } else {
                            if (environment_name.equals(ENV_BALL_1_GOAL_KNOWN)) {
                                setEnvironmentBallOneGoalKnown();
                            } else {
                                setEnvironmentUnknown();
                            }
                        }
                    }
                }
            }
        }
    }

    public void setEnvironment(ObjectInfo ball_info, ObjectInfo goal_info) {
        if (ball_info == null) {
            if(goal_info == null){
                setEnvironmentBallUnknownGoalUnknown();
            }
            else{
                setEnvironmentBallUnknownGoalKnown();
            }
        } else {
            if (ball_info.m_distance > 1) {
                if(goal_info == null){
                    setEnvironmentBallFarGoalUnknown();
                }
                else{
                    setEnvironmentBallFarGoalKnown();
                }
            } else {
                if(goal_info == null){
                    setEnvironmentBallOneGoalUnknown();
                }
                else{
                    setEnvironmentBallOneGoalKnown();
                }
            }
        }
    }

    @Override
    public String toString() {
        return environmen_name;
    }

}