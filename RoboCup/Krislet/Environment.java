//	File:			Environment.java
//	Author:		    Mutakabbir
//	Date:			30/09/2021

/* 
* Class contains information about the environment of the agent
*/
public class Environment {
    // set constants for the environment
    private static int ENV_TYPE_UNKNOWN = -1;
    private static int ENV_TYPE_BALL_UNKNOWN = 0;
    private static int ENV_TYPE_BALL_KICKABLE = 1;
    private static int ENV_TYPE_BALL_FAR = 2;

    private static String ENV_UNKNOWN = "Unknown";
    private static String ENV_BALL_UNKNOWN = "Ball_Unknown";
    private static String ENV_BALL_KICKABLE = "Ball_Kickable";
    private static String ENV_BALL_FAR = "Ball_Far";

    // environment variables
    public int environment_type;
    public String environmen_name;

    // constructor with ball information as parameter
    public Environment(ObjectInfo ball_info) {
        setEnvironment(ball_info);
    }

    // constructor with environment as parameter
    public Environment(String environment_name){
        setEnvironment(environment_name);
    }

    // getter for environment type
    public int getEnvironment_type() {
        return environment_type;
    }

    // getter for environment type
    public String getEnvironmen_name() {
        return environmen_name;
    }

    /*
    *   Environment state setter methods
    */
    private void setEnvironmentBallUnknown() {
        environment_type = ENV_TYPE_BALL_UNKNOWN;
        environmen_name = ENV_BALL_UNKNOWN;
    }

    private void setEnvironmentBallFar(){
        environment_type = ENV_TYPE_BALL_FAR;
        environmen_name = ENV_BALL_FAR;
    }

    private void setEnvironmentBallKickable(){
        environment_type = ENV_TYPE_BALL_KICKABLE;
        environmen_name = ENV_BALL_KICKABLE;
    }

    private void setEnvironmentUnknown(){
        environment_type = ENV_TYPE_UNKNOWN;
        environmen_name = ENV_UNKNOWN;
    }

    public void setEnvironment(String environment_name) {
        if (environment_name.equals(ENV_BALL_UNKNOWN)) {
            setEnvironmentBallUnknown();
        }
        else{
            if(environment_name.equals(ENV_BALL_FAR)){
                setEnvironmentBallFar();
            }
            else{
                if(environment_name.equals(ENV_BALL_KICKABLE)){
                    setEnvironmentBallKickable();
                }
                else{
                    setEnvironmentUnknown();
                }
            }
        }
    }

    public void setEnvironment(ObjectInfo ball_info) {
        if (ball_info == null) {
            setEnvironmentBallUnknown();
        } else {
            if (ball_info.m_distance > 1) {
                setEnvironmentBallFar();
            } else {
                setEnvironmentBallKickable();
            }
        }
    }

    @Override
    public String toString() {
        return environmen_name;
    }

}