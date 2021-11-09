//	File:			Action.java
//	Author:		    Mutakabbir
//	Date:			09/11/2021

/*
*   This is the abstract class defined for actions 
*   it holds the action name and description and provides the abstract method for performing the action
*/
public abstract class Actions {
    private String name;
    private String decription;

    public abstract void do_action(SendCommand krislet);

    public Actions(String name, String description) {
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
class ActionTurn extends Actions {

    private static String actionName = "Turn";
    private static String actionDecription = "Rotate/turn the player by the specifed turn parameter";
    private double actionRotation;

    public double getActionRotation() {
        return actionRotation;
    }

    public void setActionRotation(double actionRotation) {
        this.actionRotation = actionRotation;
    }

    public ActionTurn(double actionRotation) {
        super(ActionTurn.actionName, ActionTurn.actionDecription);
        this.actionRotation = actionRotation;
    }

    @Override
    public void do_action(SendCommand krislet) {

        krislet.turn(this.actionRotation);
        System.out.println("Performed Action: " + this.getName());
    }
    
    @Override
    public String toString() {
        return this.getName() + "(" + this.actionRotation + ")";
    }
}

/*
 * This class holds the action instructions for moving towards the ball
 */
class ActionDash extends Actions {

    private static String actionName = "Dash";
    private static String actionDecription = "Player runs ahead";
    private double actionPower;

    public double getActionPower() {
        return actionPower;
    }

    public void setActionPower(double actionPower) {
        this.actionPower = actionPower;
    }

    public ActionDash(double actionPower) {
        super(ActionDash.actionName, ActionDash.actionDecription);
        this.actionPower = actionPower;
    }

    @Override
    public void do_action(SendCommand krislet) {
        
        krislet.dash(this.actionPower);
        System.out.println("Performed Action: " + this.getName());
    }
  
    @Override
    public String toString() {
        return this.getName() + "(" + this.actionPower + ")";
    }

}

/*
 * This class contains information about kicking the ball
 */
class ActionKick extends Actions {

    private static String actionName = "Kick";
    private static String actionDecription = "Player tries to kick the ball";
    private double actionPower;
    private double actionDirection;

    
    public double getActionPower() {
        return actionPower;
    }

    public void setActionPower(double actionPower) {
        this.actionPower = actionPower;
    }

    public double getActionDirection() {
        return actionDirection;
    }

    public void setActionDirection(double actionDirection) {
        this.actionDirection = actionDirection;
    }

    public ActionKick(double actionPower, double actionDirection) {
        super(ActionKick.actionName, ActionKick.actionDecription);
        this.actionPower = actionPower;
        this.actionDirection = actionDirection;
    }

    @Override
    public void do_action(SendCommand krislet) {

        krislet.kick(this.actionPower, this.actionDirection);
        System.out.println("Performed Action: " + this.getName());
    }
    
    @Override
    public String toString() {
        return this.getName() + "(" + this.actionPower + "," + this.actionDirection + ")";
    }
}

/*
 * This class hold the instructions as to what needs to be done when the action
 * is not known
 */
class ActionUnknown extends Actions {

    private static String actionName = "Unkown";
    private static String actionDecription = "Action is not known";

    public ActionUnknown() {
        super(ActionUnknown.actionName, ActionUnknown.actionDecription);
    }

    @Override
    public void do_action(SendCommand krislet) {
        System.out.println("Performed Action: " + this.getName());
    }
    
    @Override
    public String toString() {
        return this.getName() + "()";
    }
}