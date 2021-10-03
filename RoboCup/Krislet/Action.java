//	File:			Action.java
//	Author:		    Mutakabbir
//	Date:			30/09/2021

/*
*   This is the abstract class defined for actions 
*   it holds the action name and description and provides the abstract method for performing the action
*/
public abstract class Action {
    public String name;
    public String decription;

    public abstract void do_action(SendCommand krislet, ObjectInfo ball_info, ObjectInfo goal_info, char side);

    public Action(String name, String description) {
        this.name = name;
        this.decription = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    @Override
    public String toString() {
        return this.name + " [" + this.decription + "]";
    }
}

/*
 * This class holds the action instructions for turning the player
 */
class ActionTurn extends Action {

    private static String action_name = "Turn";
    private static String action_decription = "Rotate/turn the player by the specifed turn parameter";
    private static double player_turn_angle = -40.0;

    public ActionTurn() {
        super(action_name, action_decription);
    }

    @Override
    public void do_action(SendCommand krislet, ObjectInfo ball_info, ObjectInfo goal_info, char side) {

        krislet.turn(player_turn_angle);
        System.out.println("Performed Action: " + this.name);
    }
}

/*
 * This class holds the action instructions for moving towards the ball
 */
class ActionDash extends Action {

    private static String action_name = "Dash";
    private static String action_decription = "Player runs ahead";
    private static double dash_multiplier = 20.0f;

    public ActionDash() {
        super(action_name, action_decription);
    }

    @Override
    public void do_action(SendCommand krislet, ObjectInfo ball_info, ObjectInfo goal_info, char side) {
        
        double dash_power;

        if (ball_info != null){
            dash_power = dash_multiplier * ball_info.m_distance;
        }
        else{
            dash_power = dash_multiplier * dash_multiplier;
        }
        krislet.dash(dash_power);
        System.out.println("Performed Action: " + this.name);
    }

}

/*
 * This class contains information about kicking the ball
 */
class ActionKick extends Action {

    private static String action_name = "Kick";
    private static String action_decription = "Player tries to kick the ball";
    private static double kick_power = 100;
    // private static double turn_angle = 30;

    public ActionKick() {
        super(action_name, action_decription);
    }

    @Override
    public void do_action(SendCommand krislet, ObjectInfo ball_info, ObjectInfo goal_info, char side) {
        double kick_direction = 0;
        if(ball_info != null && goal_info != null){
            kick_direction = (ball_info.m_direction + goal_info.m_direction) / 2;
        }

        krislet.kick(kick_power, kick_direction);
        System.out.println("Performed Action: " + this.name);
    }

}

/*
 * This class hold the instructions as to what needs to be done when the action
 * is not known
 */
class ActionUnknown extends Action {

    private static String action_name = "Unkown";
    private static String action_decription = "Action is not known";

    public ActionUnknown() {
        super(action_name, action_decription);
    }

    @Override
    public void do_action(SendCommand krislet, ObjectInfo ball_info, ObjectInfo goal_info, char side) {
        System.out.println("Performed Action: " + this.name);
    }

}